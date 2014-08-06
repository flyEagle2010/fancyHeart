package com.doteplay.editor.common;

import java.util.HashSet;
import java.util.Iterator;

import com.doteplay.editor.user.UserData;

public class PermissionManager
{

	public final static int MAX_GROUP_COUNT = 16;

	private class PermissionGroup
	{

		private int groupId;
		private String name;
		private String desc;
		private HashSet<String> groupPermissionPoints;

		public PermissionGroup(int grpid, String name, String desc)
		{
			this.groupId = grpid;
			this.name = name;
			this.desc = desc;
			groupPermissionPoints = new HashSet<String>();
		}

		public int getGroupId()
		{
			return groupId;
		}

		public void setGroupId(int groupId)
		{
			this.groupId = groupId;
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public String getDesc()
		{
			return desc;
		}

		public void setDesc(String desc)
		{
			this.desc = desc;
		}

		public void regPermissionPoint(String pointname)
		{
			if (pointname != null && pointname.length() > 0
					&& !groupPermissionPoints.contains(pointname))
			{
				groupPermissionPoints.add(pointname);
			}
		}

		public boolean isPermission(String pointname)
		{
			if (pointname != null && pointname.length() > 0)
				return groupPermissionPoints.contains(pointname);
			else
				return false;
		}
	}

	private HashSet<String> permissionPoints;

	private PermissionGroup[] groups;

	public PermissionManager()
	{
		permissionPoints = new HashSet<String>();
		groups = new PermissionGroup[MAX_GROUP_COUNT];
	}

	private void reg_permission_point(String pointname)
	{
		if (pointname != null && pointname.length() > 0
				&& !permissionPoints.contains(pointname))
		{
			permissionPoints.add(pointname);
		}
	}

	private void reg_permission_group(int groupid, String groupname,
			String desc, String permissions)
	{
		if (groupid >= 0 && groupid < MAX_GROUP_COUNT)
		{
			if (groups[groupid] == null)
			{
				groups[groupid] = new PermissionGroup(groupid, groupname, desc);
			}
			if (permissions != null && permissions.length() > 0)
			{
				if ("*".equals(permissions))
				{
					Iterator<String> tmpIR = permissionPoints.iterator();
					while (tmpIR != null && tmpIR.hasNext())
					{
						groups[groupid].regPermissionPoint(tmpIR.next());
					}
				} else
				{
					String[] tmpPoints = permissions.split(",");
					if (tmpPoints != null && tmpPoints.length > 0)
					{
						for (int i = 0; i < tmpPoints.length; i++)
							groups[groupid].regPermissionPoint(tmpPoints[i]);
					}
					tmpPoints = null;
				}
			}
		}
	}

	private boolean is_permission(int groupid, String pointname)
	{
		if (groupid >= 0 && groupid < MAX_GROUP_COUNT
				&& groups[groupid] != null)
			return groups[groupid].isPermission(pointname);
		else
			return false;
	}

	// *****************************************************************************
	private static PermissionManager instance;
	private static UserData user; // 当前用户
	private static boolean isUseDatabase;

	private synchronized static PermissionManager getInstance()
	{
		if (instance == null)
			instance = new PermissionManager();
		return instance;
	}

	public static void regPermissionPoint(String pointname)
	{
		PermissionManager tmpManager = getInstance();
		if (tmpManager != null)
			tmpManager.reg_permission_point(pointname);
	}

	public static void regPermissionGroup(int groupid, String groupname,
			String desc, String permissions)
	{
		PermissionManager tmpManager = getInstance();
		if (tmpManager != null)
			tmpManager.reg_permission_group(groupid, groupname, desc,
					permissions);
	}

	public static boolean isPermission(int groupid, String pointname)
	{
		PermissionManager tmpManager = getInstance();
		if (tmpManager != null)
			return tmpManager.is_permission(groupid, pointname);
		else
			return false;
	}

	public static void bindUser(UserData auser, boolean isusedatabase)
	{
		user = auser;
		isUseDatabase = isusedatabase;
	}

	public static boolean isPermission(String pointname)
	{
		if (user != null)
		{
			PermissionManager tmpManager = getInstance();
			if (tmpManager != null)
				return tmpManager.is_permission(user.groupId, pointname);
			else
				return false;
		} else
			return true;
	}
}
