package com.doteyplay.luna.common.protocol.codec;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

import com.doteyplay.luna.common.message.EncoderMessage;

/**
 * 获得编码和解码器的工厂类
 *
 */
public class DefaultProtocolCodecFactory extends DemuxingProtocolCodecFactory {
	public DefaultProtocolCodecFactory(){
		super.addMessageDecoder(DefaultMessageDecoder.class);
		super.addMessageEncoder(EncoderMessage.class,DefaultMessageEncoder.class);
	}
}
