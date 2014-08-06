package com.doteyplay.net.protocol;

import org.apache.mina.core.buffer.IoBuffer;

import com.doteyplay.core.bhns.ISimpleService;

/**
 * 服务的分发使用
 */
public interface IServicePipeline
{

	public abstract void dispatchAction(ISimpleService service, IoBuffer buffer,long sessionId);
}
