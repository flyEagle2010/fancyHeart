package com.doteyplay.net.message;


public class MessageActionHelper
{

	private final IMessageAction<? extends AbstractMessage, ?> action;
	/**
	 * 该Action是否关闭(true为关闭不可用)
	 */
	private boolean closed = false;

	/**
	 * 
	 */
	public MessageActionHelper(
			IMessageAction<? extends AbstractMessage, ?> action)
	{
		this.action = action;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.doteyplay.net.message.IMessageActionHelper#getAction()
	 */
	public IMessageAction<?, ?> getAction()
	{
		return action;
	}

	public void close()
	{
		closed = true;
	}

	public void open()
	{
		closed = false;
	}

	public boolean isClosed()
	{
		return closed;
	}
}
