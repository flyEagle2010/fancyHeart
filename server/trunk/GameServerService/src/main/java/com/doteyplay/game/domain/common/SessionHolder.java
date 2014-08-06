package com.doteyplay.game.domain.common;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import com.doteyplay.core.server.task.TaskCallbackHandler;
import com.doteyplay.game.MessageCommands;
import com.doteyplay.game.message.system.ACKMessage;
import com.doteyplay.game.task.TaskManager;
import com.doteyplay.game.util.DateTimeUtil;
import com.doteyplay.net.message.AbstractMessage;

/**
 * session holder
 * 
 * @author
 * 
 */
public class SessionHolder
{
	private static Logger logger = Logger.getLogger(SessionHolder.class);

	private long userId;

	private long curRoleId;

	private IoSession session;

	private long sessionId;
	
	private long heartBeatTime = 0;

	/**
	 * 发送ACK时的时间戳
	 */
	private long ackSentTimeStamp = System.currentTimeMillis();

	/**
	 * 计划任务回调句柄
	 */
	private TaskCallbackHandler callbackHandler;
	/**
	 * 认证状态
	 */
	private boolean hasAuth;

	public void update()
	{
		try
		{
			// 20秒处理
			final long now = System.currentTimeMillis();
			if (now - heartBeatTime >= 20 * DateTimeUtil.SECOND)
			{
				if(session != null && session.isConnected())
				{
					heartBeatTime = now;

					// 发送心跳消息
					ACKMessage message = new ACKMessage();
					sendMsg(message);

					this.ackSentTimeStamp = System.currentTimeMillis();
				}
				else
				{
					this.callbackHandler.cancel();
					return;
				}
			}
		} catch (Exception e)
		{
			logger.error(userId + "更新出错:" + e.getMessage());
		}
	}

	public void sendMsg(AbstractMessage msg)
	{
		if (msg == null)
		{
			String e = "用户发送消息错误-消息为空:id=" + userId;
			logger.error(e, new NullPointerException(e));
			return;
		}
		if (session == null)
		{
			logger.error("session == null :" + this.toString());
			return;
		} else if (!session.isConnected())
		{
			logger.error("session disconnected :" + this.toString());
			return;
		}
		try
		{
			IoBuffer buf = msg.encodeIoBuffer();
			if (buf != null)
			{
				if(MessageCommands.values()[buf.getShort(4)] != MessageCommands.ACK_MESSAGE)
					logger.error("SEND_MESSAGE:"+MessageCommands.values()[buf.getShort(4)]);
				session.write(buf);
			}
		} catch (Exception e)
		{
			logger.error("发送消息时出错:" + msg, e);
		}
	}

	public static void sendMsg(AbstractMessage msg, IoSession session)
	{
		if (msg == null)
		{
			String e = "用户发送消息错误-消息为空";
			logger.error(e, new NullPointerException(e));
			return;
		}
		if (session == null)
		{
			logger.error("session == null ", new RuntimeException(
					"session == null"));
			return;
		} else if (!session.isConnected())
		{
			logger.error("session disconnected ");
			return;
		}
		try
		{
			IoBuffer buf = msg.encodeIoBuffer();
			if (buf != null)
			{
				if(MessageCommands.values()[buf.getShort(4)] != MessageCommands.ACK_MESSAGE)
					logger.error("SEND_MESSAGE:"+MessageCommands.values()[buf.getShort(4)]);
				session.write(buf);
			}
		} catch (Exception e)
		{
			logger.error("发送消息时出错:" + msg, e);
		}
	}

	public long getUserId()
	{
		return userId;
	}

	public void setUserId(long userId)
	{
		this.userId = userId;
	}

	public IoSession getSession()
	{
		return session;
	}

	public void setSession(IoSession session)
	{
		this.session = session;
	}

	public long getCurRoleId()
	{
		return curRoleId;
	}

	public void setCurRoleId(long curRoleId)
	{
		this.curRoleId = curRoleId;
	}

	public boolean isHasAuth()
	{
		return hasAuth;
	}

	public void setHasAuth(boolean hasAuth)
	{
		this.hasAuth = hasAuth;
	}

	public TaskCallbackHandler getCallbackHandler()
	{
		return callbackHandler;
	}

	public void setCallbackHandler(TaskCallbackHandler callbackHandler)
	{
		this.callbackHandler = callbackHandler;
	}

	public long getAckSentTimeStamp()
	{
		return ackSentTimeStamp;
	}

	public long getSessionId()
	{
		return sessionId;
	}

	public void setSessionId(long sessionId)
	{
		this.sessionId = sessionId;
	}

	@Override
	public String toString()
	{
		return "SessionHolder [userId=" + userId + ", curRoleId=" + curRoleId
				+ ", session=" + session + ", sessionId=" + sessionId
				+ ", heartBeatTime=" + heartBeatTime + ", ackSentTimeStamp="
				+ ackSentTimeStamp + ", callbackHandler=" + callbackHandler
				+ ", hasAuth=" + hasAuth + "]";
	}
}