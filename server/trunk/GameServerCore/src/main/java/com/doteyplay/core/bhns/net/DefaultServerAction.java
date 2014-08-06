package com.doteyplay.core.bhns.net;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.doteyplay.core.bhns.AbstractSimpleService;
import com.doteyplay.core.bhns.BOServiceManager;
import com.doteyplay.core.bhns.ISimpleService;
import com.doteyplay.core.bhns.ServiceInfo;
import com.doteyplay.core.bhns.rmi.ProxyBean;
import com.doteyplay.core.bhns.rmi.ProxyConst;
import com.doteyplay.core.bhns.source.IEndpointSource;
import com.doteyplay.core.monitor.Monitor;
import com.doteyplay.luna.common.action.BaseAction;
import com.doteyplay.luna.common.message.DecoderMessage;
import com.doteyplay.luna.common.message.EncoderMessage;

/**
 * 代理服务服务器网络调用入口
 * 
 */
public class DefaultServerAction extends BaseAction
{
	private static final Logger logger = Logger.getLogger(DefaultServerAction.class
			.getName());
	
	private static DefaultServerAction instance = new DefaultServerAction();

	public static DefaultServerAction getInstance()
	{
		return instance;
	}

	@Override
	public void doAction(long arg0)
	{
	}

	@Override
	public void doAction(IoSession arg0, DecoderMessage arg1)
	{
		long tmpserviceId = arg1.getRoleId();
		long synKey = arg1.getSynKey();
		ProxyBean bean = new ProxyBean(); 
		bean.decode(arg1.getBuffer());
		
		int tmpPortalId = bean.getPortalId();
		byte tmpInternalFlag = bean.getInternalFlag();
		byte tmpSrcEndpointId = bean.getEndpointId();
		String tmpMethodSignature = bean.getMethodSignature();
		
		Monitor.mark(tmpMethodSignature, tmpPortalId);
		Monitor.recordGetCallIoSize(arg1.getMessageLength());
		
		try
		{
			if (tmpserviceId == 0)
			{
				ServiceInfo tmpServiceInfo = BOServiceManager.getServiceInfo(tmpPortalId);
				if (tmpServiceInfo != null)
				{
					byte tmpEndpointId = tmpServiceInfo.source().endpointIdOfserviceId(tmpserviceId);
					//直接对portal调用
					if (tmpInternalFlag == ProxyConst.FLAG_SERVICE_PORTAL_COMMAND)
					{
						if(bean.getParameter()!=null && bean.getParameter().length==2)
						{
							Integer argPortalId=(Integer)bean.getParameter()[0];
							String argCommand=(String)bean.getParameter()[1];
							String rspData=tmpServiceInfo.postPortalCommand(argPortalId.intValue(), argCommand, tmpEndpointId);
							
							
							if(tmpServiceInfo.source().isSyncPortalCommand(argPortalId.intValue(), argCommand))
							{
								EncoderMessage encoderMessage = new EncoderMessage((short) 0, false);
								encoderMessage.setSynKey(synKey);
								encoderMessage.putObject(rspData);
								Monitor.recordSendReturnIoSize(encoderMessage.getBuffRealLength());
								Monitor.recordSimpleInfo();
								arg0.write(encoderMessage);
								return;
							}
							else
							{
								Monitor.recordSendReturnIoSize(0);
								Monitor.recordSimpleInfo();
								return;
							}
						}							
					}
					else
					{
						ISimpleService tmpBO = tmpServiceInfo.getPortalService(tmpPortalId, tmpEndpointId);
						if (tmpBO != null && !tmpBO.isProxy())
						{
							AbstractSimpleService<?> tmpBOObj = (AbstractSimpleService<?>) tmpBO;
							Object obj = tmpBOObj.invokeMethod(tmpMethodSignature, bean.getParameter());
							
							if(tmpServiceInfo.option().isSyncMethod(tmpMethodSignature))
							{
								EncoderMessage encoderMessage = new EncoderMessage((short) 0, false);
								encoderMessage.setSynKey(synKey);
								encoderMessage.putObject(obj);

								Monitor.recordSendReturnIoSize(encoderMessage.getBuffRealLength());
								Monitor.recordSimpleInfo();
								
								arg0.write(encoderMessage);
								return;
							}
							else
							{
								Monitor.recordSendReturnIoSize(0);
								Monitor.recordSimpleInfo();
								return;
							}
						}
					}
				}
			}
			else
			{
				if (tmpInternalFlag == ProxyConst.FLAG_SERVICE_INVOKE)
				{
					// 服务API调用
					ServiceInfo tmpServiceInfo = BOServiceManager.getServiceInfo(tmpPortalId);
					ISimpleService tmpBO = tmpServiceInfo.findService(tmpPortalId, tmpserviceId);
					if (tmpBO != null && !tmpBO.isProxy())
					{
						Object obj;
						if (tmpBO.isValid())
							obj = tmpServiceInfo.option().invokeMethod(tmpBO, tmpMethodSignature, bean.getParameter());
						else
							obj = tmpServiceInfo.option().invokeDummy(tmpMethodSignature, bean.getParameter());
						
						if(logger.isDebugEnabled())
							logger.debug("serviceId:"+tmpserviceId+"--portalId:"+tmpPortalId+"--method"+tmpMethodSignature);
						if(tmpServiceInfo.option().isSyncMethod(tmpMethodSignature))
						{
							EncoderMessage encoderMessage = new EncoderMessage((short) 0, false);
							encoderMessage.setSynKey(synKey);
							encoderMessage.putObject(obj);
							
							Monitor.recordSendReturnIoSize(encoderMessage.getBuffRealLength());
							Monitor.recordSimpleInfo();
							arg0.write(encoderMessage);
							return;
						}
						else
						{
							Monitor.recordSendReturnIoSize(0);
							Monitor.recordSimpleInfo();
							return;
						}
					}
				}
				else if (tmpInternalFlag == ProxyConst.FLAG_SERVICE_IS_ACTIVE)
				{
					ServiceInfo tmpServiceInfo = BOServiceManager.getServiceInfo(tmpPortalId);
					if (tmpServiceInfo != null)
					{
						IEndpointSource tmpEndpoint = tmpServiceInfo.source().getEndpoint(tmpSrcEndpointId);
						if (tmpEndpoint != null && tmpEndpoint.isLocalImplemention()
								&& tmpEndpoint.isActive(tmpserviceId))
						{
							EncoderMessage encoderMessage = new EncoderMessage((short) 0, false);
							encoderMessage.setSynKey(synKey);
							encoderMessage.putObject(true);
							Monitor.recordSendReturnIoSize(encoderMessage.getBuffRealLength());
							arg0.write(encoderMessage);
							return;
						}
					}
					EncoderMessage encoderMessage = new EncoderMessage((short) 0, false);
					encoderMessage.setSynKey(synKey);
					encoderMessage.putObject(false);
					Monitor.recordSendReturnIoSize(encoderMessage.getBuffRealLength());
					
					arg0.write(encoderMessage);
			
					return;
				}
				else if (tmpInternalFlag == ProxyConst.FLAG_SERVICE_ACTIVE_NOTIFY)
				{
					boolean isActive = false;
					ServiceInfo tmpServiceInfo = BOServiceManager.getServiceInfo(tmpPortalId);
					if (tmpServiceInfo != null)
					{
						boolean isOrderActive = false;
						if(bean.getParameter()!=null && bean.getParameter().length==1)
							isOrderActive = (Boolean)bean.getParameter()[0];
						
						IEndpointSource tmpEndpoint = tmpServiceInfo.source().getEndpoint(tmpSrcEndpointId);
						if (tmpEndpoint != null && tmpEndpoint.isLocalImplemention())
						{
							if (tmpEndpoint.getPortalId() != tmpPortalId)
							{
								// 子服务
								ISimpleService tmpService = tmpEndpoint.findService(tmpServiceInfo.getPortalId(),
										tmpserviceId);
								if (tmpService != null && tmpService.isValid())
									isActive = true;
							}
							else if (tmpEndpoint.activeService(tmpserviceId, isOrderActive) != null)
							{
								isActive = true;
								if(logger.isDebugEnabled())
									logger.debug("激活service："+tmpserviceId+"--portalId:"+tmpServiceInfo.getPortalId()+"--endpoint:"+tmpSrcEndpointId);
							}
						}
					}
					//激活改为异步请求
					Monitor.recordSendReturnIoSize(0);
					Monitor.recordSimpleInfo();
					return;
				}
				else if (tmpInternalFlag == ProxyConst.FLAG_SERVICE_DESTROY)
				{
					ServiceInfo tmpServiceInfo = BOServiceManager.getServiceInfo(tmpPortalId);
					if (tmpServiceInfo != null)
					{
						byte tmpEndpointId = tmpServiceInfo.source().endpointIdOfserviceId(tmpserviceId);
						if (!tmpServiceInfo.isProxy(tmpEndpointId))
						{
							tmpServiceInfo.destroyService(tmpserviceId);
						}
					}
					//销毁为异步
					Monitor.recordSendReturnIoSize(0);
					Monitor.recordSimpleInfo();
					return;
				}
				else if (tmpInternalFlag == ProxyConst.FLAG_EVENT_TRIGGER)
				{
					ServiceInfo tmpServiceInfo = BOServiceManager.getServiceInfo(tmpPortalId);
					if (tmpServiceInfo != null)
					{
//						byte tmpEndpointId = tmpServiceInfo.source().endpointIdOfserviceId(tmpserviceId);
//						if (!tmpServiceInfo.isProxy(tmpEndpointId))
//						{
							tmpServiceInfo.triggerEvent((int)tmpserviceId, tmpMethodSignature, bean.getParameter());
//						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
//		EncoderMessage encoderMessage = new EncoderMessage((short) 0, false);
//		encoderMessage.putObject(null);
//		Monitor.recordSendReturnIoSize(encoderMessage.getBuffRealLength());
//		arg0.write(encoderMessage);
	}

}
