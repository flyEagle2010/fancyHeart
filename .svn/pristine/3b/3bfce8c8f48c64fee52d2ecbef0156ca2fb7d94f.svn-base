package com.doteyplay.luna.util;

import java.lang.management.ManagementFactory;
import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * synkey = ip4AddrCode| pid | threadid
 */
public class SynKeyUtil
{
	private static long synkeyPrefix;

	static
	{
		try
		{
			long ip4AddrCode = Inet4Address.getLocalHost().getHostAddress()
					.hashCode();
			long pid = Short.parseShort(ManagementFactory.getRuntimeMXBean()
					.getName().split("@")[0]);
			synkeyPrefix = ip4AddrCode << 32 | pid << 16;
		} catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
	}

	public static long getSynkey()
	{
		return (Long) (synkeyPrefix | Thread.currentThread().getId());
	}

//	public static void main(String[] args)
//	{
//		System.out.println(getSynkey());
//	}
}
