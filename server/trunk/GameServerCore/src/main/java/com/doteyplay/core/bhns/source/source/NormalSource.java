package com.doteyplay.core.bhns.source.source;

import org.apache.log4j.Logger;

import com.doteyplay.core.bhns.AbstractSimpleService;
import com.doteyplay.core.bhns.ISimpleService;
import com.doteyplay.core.bhns.source.IEndpointSource;
import com.doteyplay.core.bhns.source.IServiceSource;
import com.doteyplay.core.bhns.source.options.NormalServiceOption;
import com.doteyplay.core.bhns.source.source.cluster.EndpointSource;
import com.doteyplay.core.bhns.source.source.cluster.RemoteEndpointSource;

public class NormalSource implements IServiceSource
{
	private static final Logger logger = Logger.getLogger(NormalSource.class);

	private NormalServiceOption serviceOption;
	private IEndpointSource endpoint;
	private byte[] endpointIdList;

	public NormalSource(NormalServiceOption serviceOption)
	{
		this.serviceOption = serviceOption;
		this.endpoint = null;
		endpointIdList = new byte[]{0};
	}

	public void bindSource(String portalImplClassName, String serviceImplClassName, String configfile, String dataBlocks)
	{
		if (portalImplClassName != null && portalImplClassName.length() > 0 && serviceImplClassName != null
				&& serviceImplClassName.length() > 0)
		{
			this.endpoint = new EndpointSource(serviceOption, (byte) 0, portalImplClassName, serviceImplClassName,
					configfile, dataBlocks);

		}
		else
		{
			logger.error("register endpoint error:portalImplClassName=" + portalImplClassName
					+ ",serviceImplClassName=" + serviceImplClassName + ",configfile=" + configfile);
		}

	}

	@Override
	public void initialize()
	{
		if (this.endpoint == null)
		{
			this.endpoint = new RemoteEndpointSource(serviceOption, (byte) 0);
		}
		this.endpoint.initialize();
	}

	@Override
	public boolean isLocalImplemention(byte endpointId)
	{
		return true;
	}

	@Override
	public IEndpointSource getEndpoint(byte endpointId)
	{
		return this.endpoint;
	}

	@Override
	public int getVersion(byte endpointId)
	{
		return endpoint.getVersion();
	}

	@Override
	public String getConfigFile(byte endpointId)
	{
		return endpoint.getConfigFile();
	}

	@Override
	public String getDataBlocks(byte endpointId)
	{
		return endpoint.getDataBlocks();
	}

	@Override
	public boolean isAvailable()
	{
		return endpoint.isAvailable();
	}

	@Override
	public AbstractSimpleService<?> createServiceImpInstance(byte endpointId)
	{
		return endpoint.createServiceImpInstance();
	}

	@Override
	public void destroyService(long svrid)
	{
		endpoint.destroyService(svrid);
	}
	
	@Override
	public String postPortalCommand(int portalId,String requestCommand,byte endpointId)
	{
		return endpoint.postPortalCommand(portalId,requestCommand);
	}
	
	@Override
	public boolean isSyncPortalCommand(int portalId, String requestCommand)
	{
		return endpoint.isSyncPortalCommand(portalId, requestCommand);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ISimpleService> T findService(int portalId, long svrid)
	{
		return (T)endpoint.findService(portalId, svrid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ISimpleService> T getPortalService(int portalId, byte endpointId)
	{
		return (T)endpoint.getPortalService(portalId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends ISimpleService> T getLocalPortalService(int portalId)
	{
		if(endpoint.isLocalImplemention())
			return (T)endpoint.getPortalService(portalId);
		else
			return null;
	}

	@Override
	public void triggerEvent(int eventid, String methodSignature, Object[] args)
	{
		endpoint.triggerEvent(eventid, methodSignature, args);
	}

	@Override
	public boolean isActive(long svrid, byte endpointId)
	{
		return endpoint.isActive(svrid);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends ISimpleService> T activeService(long svrid, byte endpointId, boolean isOrderActive)
	{
		return (T)endpoint.activeService(svrid, isOrderActive);
	}

	@Override
	public byte[] endpointIdList()
	{
		return endpointIdList;
	}
	
	@Override
	public byte endpointIdOfserviceId(long svrid)
	{
		return 0;
	}

	@Override
	public IEndpointSource getLocalEndPoint()
	{
		if(endpoint.isLocalImplemention())
			return endpoint;
		return null;
	}
	
	@Override
	public int getEndPointSize()
	{
		return 1;
	}
}
