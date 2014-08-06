package com.doteyplay.luna.common;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * 指令格式：验证码(short)+报文长度(short)+指令号(short)+报文内容
 * 
 * 服务器相关的常量
 */
public class LunaConstants
{

	/**
	 * 验证码的长度
	 */
	public static final byte VALIDATOR_LENGTH = 2;

	/**
	 * 报文长度,不包含校验码长度
	 */
	public static final byte MESSAGE_LENGTH = 4;

	/**
	 * 角色编号的长度4
	 */
	public static final byte ROLEID_LENGTH = 8;
	
	/**
	 * 指令长度
	 */
	public static final byte COMMAND_LENGTH = 2;
	
	/**
	 * 用于同步阻塞的唯一key
	 */
	public static final byte SYNKEY_LENGTH = 8;
	/**
	 * 报文头的最小长度,验证码(short)+报文长度(int)+角色编号(int)+指令号(short)+同步返回标示(long)
	 */
	public static final byte HEADER_LENGTH = VALIDATOR_LENGTH + MESSAGE_LENGTH
			+ ROLEID_LENGTH + COMMAND_LENGTH+SYNKEY_LENGTH;

	/**
	 * 发送给服务器端的验证码
	 */
	public static final short SERVER_VALIDATOR_CODE = 3456;

	/**
	 * 发给客户端的验证码
	 */
	public static final short CLIENT_VALIDATOR_CODE = 8765;

	/**
	 * 最大的报文长度
	 */
	public static final int MAX_MESSAGE_LENGTH = Integer.MAX_VALUE;
	/**
	 * 缺省Buffer的大小
	 */
	public static final int DEFAULT_MESSAGE_LENGTH = 2048;
	/**
	 * 最小的报文长度为只有会话编号和角色编号和指令编号
	 */
	public static final short MIN_MESSAGE_LENGTH = 6;

	/**
	 * idel空闲时间
	 */
	public static final byte IDLE_TIME = 60;
	/**
	 * 解码
	 */
	public static final CharsetDecoder DECODER = Charset.forName("UTF-8")
			.newDecoder();
	/**
	 * 编码
	 */
	public static final CharsetEncoder ENCODER = Charset.forName("UTF-8")
			.newEncoder();

	
	public static final byte FALSE = 0;
	public static final byte TRUE = 1;
}
