package com.doteyplay.core.util.servertask;

/**
 * 
 */
public interface IServerTaskDBManager
{
	public void saveServerTaskInfo(ServerTaskInfo taskInfo);
	
	public ServerTaskInfo getServerTaskInfo(int id);
	
	public void updateTaskInfo(ServerTaskInfo info);
}
