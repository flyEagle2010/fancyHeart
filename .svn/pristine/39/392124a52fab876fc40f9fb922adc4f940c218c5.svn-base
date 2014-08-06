package com.doteplay.editor.common;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.doteplay.editor.EditorConfig;
import com.doteplay.editor.data.text.TextData;
import com.doteplay.editor.util.Util;
import com.doteplay.editor.util.xml.simpleXML.SimpleAttributes;
import com.doteplay.editor.util.xml.simpleXML.StrUtils;
import com.doteyplay.game.config.GameConfig;

public class DataManager
{
	/**
	 * 数据组Map
	 */
	public static Hashtable<String, DataGroup> dataGroups = new Hashtable<String, DataGroup>();
	/**
	 * 数据组list
	 */
	public static Vector<DataGroup> dataGroupList = new Vector<DataGroup>();
	/**
	 * 错误数据替换列表
	 */
	private static Hashtable<String, BaseData> replaceDataMap = new Hashtable<String, BaseData>();

	public DataManager()
	{
	}

	/**
	 * 根据配置文件读取数据并创建数据组
	 * 
	 * @param attributes
	 *            配置文件
	 */
	public static void loadDataGroupConfig(SimpleAttributes attributes)
	{
		String tmpGroupClass = attributes.getValue("class");
		String tmpGroupPrefix = attributes.getValue("prefix");
		if (!StrUtils.empty(tmpGroupClass) && !StrUtils.empty(tmpGroupPrefix))
		{
			DataGroup dc = null;
			int dataGroupType = StrUtils.parseint(
					attributes.getValue("grouptype"), 0);
			if (dataGroupType == 0)
			{
				dc = new DataGroup(attributes);
			} else
			{
				dc = (DataGroup) Util.loadClassByName("com.doteplay.editor.data."
						+ tmpGroupClass + "." + tmpGroupPrefix + "DataGroup");
				dc.init(attributes);
			}
			if (dc != null)
				DataManager.addDataGroup(dc);
		}
	}

	/**
	 * 获取数据组名称列表
	 * 
	 * @return 数据组名称列表
	 */
	public static String[] getGroupNames()
	{
		String ss[] = new String[dataGroupList.size()];
		for (int i = 0; i < ss.length; i++)
		{
			ss[i] = dataGroupList.get(i).groupName;
		}
		return ss;
	}

	/**
	 * 获取数据组id列表
	 * 
	 * @return 数据组id列表
	 */
	public static String[] getGroupIds()
	{
		String ss[] = new String[dataGroupList.size()];
		for (int i = 0; i < ss.length; i++)
		{
			ss[i] = dataGroupList.get(i).groupId;
		}
		return ss;
	}

	/**
	 * 初始化所有数据组
	 * 
	 * @return
	 */
	public static boolean init()
	{
		DataGroup dc;
		for (int i = 0; i < dataGroupList.size(); i++)
		{
			dc = (DataGroup) dataGroupList.get(i);

			// check the directory for list and load data;
			dc.initBaseDataByIndexFile();
		}
		return true;
	}

	/**
	 * 添加数据组
	 * 
	 * @param dg
	 *            数据组
	 */
	public static void addDataGroup(DataGroup dg)
	{
		if (!dataGroups.containsKey(dg.groupId))
		{
			dataGroups.put(dg.groupId, dg);
			dataGroupList.addElement(dg);
		}
	}

	/**
	 * 根据数据组id获取数据组
	 * 
	 * @param gid
	 *            数据组id
	 * @return 数据组
	 */
	public static DataGroup getDataGroup(String gid)
	{
		return (DataGroup) dataGroups.get(gid);
	}

	/**
	 * 添加数据到指定id数据组
	 * 
	 * @param gid
	 *            数据组id
	 * @param bd
	 *            数据
	 */
	public static void addData(String gid, BaseData bd)
	{
		DataGroup c = (DataGroup) dataGroups.get(gid);
		if (c == null)
			return;
		c.addData(bd);
	}

	/**
	 * 获取指定id数据组数据列表
	 * 
	 * @param gid
	 *            数据组id
	 * @return 数据组数据列表
	 */
	public static Vector<BaseData> getDataList(String gid)
	{
		DataGroup c = (DataGroup) dataGroups.get(gid);
		if (c == null)
			return null;
		return c.getDataList();
	}

	/**
	 * 获取指定数据组中的数据(有错误替换提示)
	 * 
	 * @param gid
	 *            数据组id
	 * @param id
	 *            数据id
	 * @return 数据
	 */
	public static synchronized BaseData getData(String gid, String id)
	{
		DataGroup c = (DataGroup) dataGroups.get(gid);
		if (c == null)
			return null;
		BaseData bd = (BaseData) c.getData(id);

		String a;
		try
		{
			a = bd.getId() + "_" + bd.getName();
		} catch (Exception e)
		{
			System.out.println("getData id error : [" + gid + "-" + id + "]");
			e.printStackTrace();
		}

		if (bd == null)
		{
			bd = replaceDataMap.get(gid + "_" + id);
			if (bd == null)
			{
				String t = "无法装载数据:类型[" + gid + "]-编号[" + id + "]";
				String s = "类型[" + gid + "]-编号[" + id + "]缺失";
				System.out.println(s);
				JOptionPane.showMessageDialog(null, t, s,
						JOptionPane.ERROR_MESSAGE);

				Vector<BaseData> dataList = getDataList(gid);
				// bd=(BaseData)dataList.get(0);
				bd = (BaseData) JOptionPane.showInputDialog(null, "请先择替换物体",
						"信息对话框", JOptionPane.INFORMATION_MESSAGE, null,
						dataList.toArray(), null);
				if (bd != null)
				{
					replaceDataMap.put(gid + "_" + id, bd);
				}
			}
		}

		if (bd != null && bd.isDelete())
		{
			String s = "[" + gid + " " + bd.id + "_" + bd.nameId + "] 为删除状态";
			// JOptionPane.showMessageDialog(null, s);
			// try {
			// throw new RuntimeException(s);
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
		}

		return bd;
	}

	/**
	 * 根据id获取指定数据组中的数据(不弹错误提示)
	 * 
	 * @param gid
	 *            数据组id
	 * @param id
	 *            数据id
	 * @return 数据
	 */
	public static synchronized BaseData getBaseData(String gid, String id)
	{
		DataGroup c = (DataGroup) dataGroups.get(gid);
		if (c == null)
			return null;
		BaseData bd = (BaseData) c.getData(id);
		return bd;
	}

	/**
	 * 根据名称获取指定数据组中的数据(弹出错误提示)
	 * 
	 * @param gid
	 * @param id
	 * @return
	 */
	public static synchronized BaseData getDataByName(String gid, int nameId)
	{
		DataGroup c = (DataGroup) dataGroups.get(gid);
		if (c == null)
			return null;
		BaseData bd = (BaseData) c.getDataByName(nameId);
		if (bd == null)
			JOptionPane.showMessageDialog(null, "无法装载数据:类型[" + gid + "]-名称["
					+ getTextById(nameId) + "]", "信息对话框", JOptionPane.ERROR_MESSAGE);
		return bd;
	}
	
	public static String getTextById(int nameId)
	{
		DataGroup c = (DataGroup) dataGroups.get("text");
		TextData data = (TextData) c.getData(String.valueOf(nameId));
		if(data == null)
			return null;
		return data.gameData.name;
	}

//	/**
//	 * 根据名称获取指定数据组中的数据
//	 * 
//	 * @param gid
//	 *            数据组id
//	 * @param nm
//	 *            数据名称
//	 * @return 数据
//	 */
//	public static synchronized BaseData getDataByDataName(String gid, String nm)
//	{
//		DataGroup c = (DataGroup) dataGroups.get(gid);
//		if (c == null)
//			return null;
//		BaseData bd = (BaseData) c.getDataByName(nm);
//		return bd;
//	}

	/**
	 * 根据名称列表获取指定数据组中的数据
	 * 
	 * @param <T>
	 * @param gid
	 *            数据组id
	 * @param nm
	 *            数据名称
	 * @return 数据列表
	 */
	public static synchronized <T extends BaseData> List<T> getDatasByName(
			String gid, int[] nm)
	{
		DataGroup c = (DataGroup) dataGroups.get(gid);
		if (c == null)
			return null;

		List<T> bdList = new ArrayList<T>();
		for (int s : nm)
		{
			T bd = (T) c.getDataByName(s);
			if (bd == null)
			{
				continue;
			}
			// JOptionPane.showMessageDialog(null,
			// "无法装载数据:类型["+gid+"]-名称["+nm+"]", "信息对话框",
			// JOptionPane.ERROR_MESSAGE);
			bdList.add(bd);
		}
		return bdList;
	}

	/**
	 * 新建数据
	 * 
	 * @param gid
	 *            数据组id
	 * @return 数据
	 */
	public static synchronized BaseData newData(String gid)
	{
		DataGroup dc = (DataGroup) dataGroups.get(gid);
		if (dc == null)
			return null;
		String gclass = "com.doteplay.editor.data." + dc.groupId + "."
				+ dc.groupClassId + "Data";
		BaseData bd = (BaseData) Util.loadClassByName(gclass);
		// bd.id=String.valueOf(++gdata.maxId);

		return bd;
	}

	/**
	 * 获取新数据id
	 * 
	 * @param gid
	 *            数据组id
	 * @return 数据id
	 */
	public static synchronized String getNewDataId(String gid)
	{
		DataGroup gdata = (DataGroup) dataGroups.get(gid);
		if (gdata == null)
			return null;
		// return String.valueOf(gdata.maxId+1);
		return String.valueOf(gdata.getMaxId() + 1);
	}

	/**
	 * 释放所有持有的锁
	 */
	public static void releaseAllGroupDataLock()
	{

		if (!EditorConfig.useDataBase)
		{
			return;
		}

		for (DataGroup dg : dataGroupList)
		{
			dg.releaseAllDataLock();
		}
	}
}
