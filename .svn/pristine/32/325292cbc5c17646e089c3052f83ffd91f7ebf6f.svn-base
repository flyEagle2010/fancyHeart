package com.doteyplay.net.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;


public class DefaultProtocolCodecFactory implements ProtocolCodecFactory {

	private static final DefaultMessageDecoder messageDecoder=new DefaultMessageDecoder();
	private static final DefaultMessageEncoder messageEncoder=new DefaultMessageEncoder();

	public DefaultProtocolCodecFactory()
	{
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		return messageDecoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		return null;
//		return messageEncoder;
	}
}// Class DefaultProtocolCodecFactory
