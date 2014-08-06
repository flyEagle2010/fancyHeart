package com.doteyplay.net.message;

import com.doteyplay.exception.MessageProcessException;

/**
 * 
 */
public interface IMessageAction<T extends AbstractMessage, P>
{
	void processMessage(T message, P p) throws MessageProcessException;
}
