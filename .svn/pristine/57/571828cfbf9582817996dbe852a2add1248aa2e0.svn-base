/**
 * 
 */
package com.doteplay.editor.common;

import com.doteplay.editor.XEditor;

public class DataLock
{

	public boolean lock = false;
	public String lockUser = null;
	public int lockUserId = -1;

	public DataLock()
	{

	}

	public DataLock(boolean lock, int lockUserId, String lockUser)
	{
		this.lock = lock;
		this.lockUserId = lockUserId;
		this.lockUser = lockUser;
	}

	/**
	 * 是否有锁
	 * 
	 * @return
	 */
	public boolean hasLock()
	{
		return lock;
	}

	/**
	 * 自己上的锁
	 * 
	 * @return
	 */
	public boolean isLockedByMe()
	{
		if (lockUserId != -1 && lockUserId == XEditor.user.id)
		{
			return true;
		} else
		{
			return false;
		}
	}

	/**
	 * 别人上的锁
	 * 
	 * @return
	 */
	public boolean isLockedByOther()
	{
		return (!isLockedByMe());
	}

	@Override
	public String toString()
	{
		return "hasLock:" + hasLock() + " LockByMe=" + isLockedByMe() + " "
				+ lockUserId + " " + lockUser;
	}
}
