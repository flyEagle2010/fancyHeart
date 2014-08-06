/**
 * @package com.doteplay.editor.data.common
 * @file TimeData.java
 */
package com.doteplay.editor.data.common;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import com.doteyplay.game.editor.ResourceDataConstants;
import com.doteplay.editor.common.ISavebleData;


/**
 */
public class TimeData implements ISavebleData
{
	/**
	 * 限时类型
	 */
	public byte timeType = ResourceDataConstants.TIME_TYPE_RELATIVELY;
	/**
	 * 限时标识
	 */
	public long timeValue = -1;

	@Override
	public void init(DataInputStream in) throws Exception
	{
		timeType = in.readByte();
		timeValue = in.readLong();
	}

	@Override
	public void save(DataOutputStream out) throws Exception
	{
		out.writeByte(timeType);
		out.writeLong(timeValue);
	}

	@Override
	public void saveClient(DataOutputStream out) throws Exception
	{
		save(out);
	}

	@Override
	public void saveServer(DataOutputStream out) throws Exception
	{
		save(out);
	}

}
