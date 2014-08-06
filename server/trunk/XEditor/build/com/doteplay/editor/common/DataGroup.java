package com.doteplay.editor.common;

import java.awt.Component;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.DefaultDocument;
import org.dom4j.tree.DefaultElement;

import com.doteyplay.game.editor.ResourceDataConstants;
import com.doteplay.editor.EditorConfig;
import com.doteplay.editor.database.BaseConnectionPool;
import com.doteplay.editor.datahelper.IRuntimeDataHelper;
import com.doteplay.editor.datahelper.MetaData;
import com.doteplay.editor.datahelper.MetaDataScheme;
import com.doteplay.editor.datahelper.ParamList;
import com.doteplay.editor.file.exporter.ExcelExporterManager;
import com.doteplay.editor.util.Util;
import com.doteplay.editor.util.xml.XmlUtil;
import com.doteplay.editor.util.xml.simpleXML.SimpleAttributes;
import com.doteplay.editor.util.xml.simpleXML.StrUtils;

public class DataGroup<T extends BaseData> implements IXmlDocument
{

	/**
	 * 查询条件 : 修改标记
	 */
	public boolean modifiedKey;
	/**
	 * 查询条件 : 名称关键字
	 */
	public String nameKey = null;
	/**
	 * 查询条件 : 数据类型标记
	 */
	public int dataTypeKey = -1;
	/**
	 * 查询条件 : 非系统生成数据标记
	 */
	public boolean noGenDataKey;
	/**
	 * 查询条件 : 本地数据标记
	 */
	public boolean localDataKey;
	/**
	 * 查询条件 : 自定义数据
	 */
	public boolean customDataKey;
	/**
	 * 查询条件 : 引用的数据
	 */
	public boolean refDataKey;
	/**
	 * 查询条件 :　装载方式
	 */
	public int loadType = -1;
	/**
	 * 数据组类型
	 */
	public int resourceType;
	/**
	 * 数据组Id
	 */
	public String groupId;
	/**
	 * 数据组名称
	 */
	public String groupName;
	/**
	 * 数据类ID
	 */
	public String groupClassId;
	/**
	 * 数据列表类型 0:通用面板 1:自定义面板
	 */
	public int listType = 0;
	/**
	 * 数据列表面板
	 */
	public GroupPanel groupPanel;
	/**
	 * 数据组存储路径
	 */
	public String groupPath;
	/**
	 * 数据文件后缀
	 */
	public String fileSuffix;
	/**
	 * 数据对象MAP
	 */
	protected Hashtable<String, T> datas = new Hashtable<String, T>();
	/**
	 * 数据对象列表
	 */
	protected Vector<T> dataList = new Vector<T>();
	/**
	 * 当前最大数据对象编号
	 */
	public int maxId = 0;

	/**
	 * 元数据模板
	 */
	private MetaDataScheme refScheme;

	/**
	 * 数据过滤器
	 */
	private IDataFilter<T> dataFilter;
	/**
	 * XML导出绝对时间以后的数据
	 */
	public long whatTime = -1L;

	/**
	 * 是否需要导出语言包
	 */
	public boolean needLanguage = false;

	public DataGroup()
	{
	}

	public DataGroup(SimpleAttributes attributes)
	{
		init(attributes);
	}

//	protected int getClientResourceType()
//	{
//		return ResourceDataConstants
//				.getClientDataTypeByResourceType(this.resourceType);
//	}

	/**
	 * 数据过滤器，用于不同数据组之间的访问实现
	 * 
	 * @param params
	 * @return
	 */
	public List<MetaData> filterData(ParamList params)
	{
		Vector<MetaData> v = new Vector<MetaData>();
		int size = dataList.size();
		T baseData;
		MetaData mdata;
		for (int i = 0; i < size; i++)
		{
			baseData = dataList.get(i);
			baseData.open();
			try
			{
				if (baseData.isMatch(params))
				{
					mdata = new MetaData(this.refScheme);
					baseData.exportMetaData(mdata);
					v.addElement(mdata);
					mdata = null;
				}
			} finally
			{
				baseData.close();
			}
		}
		return v;
	}

	public MetaData createMetaData()
	{
		return new MetaData(this.refScheme);
	}

	/**
	 * 通过配置文件初始化
	 * 
	 * @param groupinfo
	 */
	public void init(SimpleAttributes groupinfo)
	{
		resourceType = Integer.parseInt(groupinfo.getValue("id"));
		groupId = groupinfo.getValue("class");
		groupName = groupinfo.getValue("name");
		groupClassId = groupinfo.getValue("prefix");
		listType = StrUtils.parseint(groupinfo.getValue("listtype"), 0);
		fileSuffix = groupinfo.getValue("suf");
		groupPath = EditorConfig.dataPath + groupId + File.separatorChar;
//		needLanguage = groupinfo.getValue("needLanguage").equals("1") ? true
//				: false;

		File f = new File(groupPath);
		if (!f.exists())
		{
			try
			{
				f.mkdir();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		// 载入元数据模板
		String tmpDataSchemeFile = groupinfo.getValue("datascheme");
		refScheme = new MetaDataScheme();
		refScheme.setDataGroup(groupName);
		if (tmpDataSchemeFile == null || tmpDataSchemeFile.length() <= 0)
		{
			refScheme.regField("id", "数据ID", MetaDataScheme.DATA_TYPE_STRING);
			refScheme.regField("dataid", "关联数据块ID",
					MetaDataScheme.DATA_TYPE_STRING);
			refScheme.regField("name", "名称", MetaDataScheme.DATA_TYPE_STRING);
			refScheme.regField("desc", "描述", MetaDataScheme.DATA_TYPE_STRING);
		} else
		{
			refScheme.loadDataSchemeFile(EditorConfig.confPath
					+ tmpDataSchemeFile);
		}

		// 登记权限组
		PermissionManager.regPermissionPoint(groupId);
	}

	/**
	 * 移除所有系统生成数据
	 */
	public void removeAllGeneData()
	{
		int size = dataList.size();
		T bd;
		Enumeration<T> enu = datas.elements();// dataList.elements();
		int num = 0;
		while (enu.hasMoreElements())
		// for(int i=0;i<size;i++)
		{
			// bd=dataList.get(i);
			bd = enu.nextElement();
			if (bd.geneType == 0)
				continue;
			dataList.removeElement(bd);
			datas.remove(bd.id);
			num++;
		}
		// System.out.println("removeAllGeneData:"+size+","+num);
	}

	private void clear()
	{
		datas.clear();
		dataList.clear();
	}

	/**
	 * 获取数据数量
	 * 
	 * @return
	 */
	public int getDataCount()
	{
		return dataList.size();
	}

	/**
	 * 根据索引获取数据
	 * 
	 * @param idx
	 * @return
	 */
	public T getDataByIndex(int idx)
	{
		if (idx >= 0 && idx < dataList.size())
			return (T) this.dataList.get(idx);
		else
			return null;
	}

	/**
	 * 根据数据id获取数据
	 * 
	 * @param idx
	 * @return
	 */
	public T getData(String id)
	{
		return (id != null) ? (T) this.datas.get(id) : null;
	}

	/**
	 * 数据id是否已经存在
	 * 
	 * @param id
	 * @return
	 */
	public boolean isDataExist(String id)
	{
		return (id != null) ? datas.containsKey(id) : false;
	}

	/**
	 * 获取数据列表
	 * 
	 * @return
	 */
	public Vector<T> getDataList()
	{
		return this.dataList;
	}

	/**
	 * 根据名称获取数据
	 * 
	 * @param nameId
	 * @return
	 */
	public T getDataByName(int nameId)
	{
		// Vector<T> v=new Vector<T>();
		int size = dataList.size();
		T bd;
		for (int i = 0; i < size; i++)
		{
			bd = dataList.get(i);
			if (bd.nameId == nameId)
				return bd;
			// v.addElement(bd);
		}
		return null;
	}

	/**
	 * 根据数据类型获取数据列表
	 * 
	 * @param type
	 * @return
	 */
	public Vector<T> getDataListByType(int type)
	{
		if (type == -1)
			return dataList;
		Vector<T> v = new Vector<T>();
		int size = dataList.size();
		T bd;
		for (int i = 0; i < size; i++)
		{
			bd = dataList.get(i);
			if (bd.dataType == type)
				v.addElement(bd);
		}
		return v;
	}

	/**
	 * 新建数据
	 * 
	 * @return
	 */
	public T newData()
	{
		// if(type==1)
		// return null;
		String gclass = "com.doteplay.editor.data." + groupId + "." + groupClassId
				+ "Data";
		T bd = (T) Util.loadClassByName(gclass);
		// bd.id=String.valueOf(++gdata.maxId);

		return bd;
	}

	/**
	 * 添加数据
	 * 
	 * @param ss
	 * @return
	 */
	public T addData(String[] ss)
	{
		String gclass = "com.doteplay.editor.data." + groupId + "." + groupClassId
				+ "Data";
		T bd = (T) Util.loadClassByName(gclass);
		if (bd != null)
		{
			bd.initByIndexFile(this, ss);
			addData(bd);
		}
		return bd;
	}

	/**
	 * 设置数据
	 * 
	 * @param bd
	 */
	public void setData(T bd)
	{
		datas.put(bd.id, bd);

		int index = -1;
		for (int i = 0; i < dataList.size(); i++)
		{
			T bd1 = dataList.get(i);
			if (bd.id.equals(bd1.id))
			{
				index = i;
				break;
			}
		}

		if (index != -1)
		{
			dataList.set(index, bd);
		}
	}

	/**
	 * 加入数据对象
	 * 
	 * @param bd
	 */
	public void addData(T bd)
	{
		if (datas.containsKey(bd.id))
			return;
		int j;
		datas.put(bd.id, bd);

		int a = Integer.parseInt(bd.id);
		int b;
		boolean added = false;
		T bd1;
		// 按照编号顺序加入列表
		for (j = dataList.size() - 1; j >= 0; j--)
		{
			bd1 = dataList.get(j);
			b = Integer.parseInt(bd1.id);
			if (a >= b)
			{
				//防止链表增加
				if (j < dataList.size() - 1)
					dataList.insertElementAt(bd, j + 1);
				else
					dataList.addElement(bd);
				added = true;
				break;
			}
		}
		if (!added)
		{
			dataList.addElement(bd);
		}
		if (a > maxId)
			maxId = a;
	}

	/**
	 * 删除数据对象
	 * 
	 * @param bd
	 */
	public void removeData(T bd)
	{
		if (bd.geneType == 1)
			return;
		datas.remove(bd.id);
		dataList.remove(bd);

		// if(type==1)
		// return;

		// 删除客户端文件
		String fileName = groupPath + bd.id + "." + fileSuffix;
		File file = new File(fileName);
		if (file.exists())
			file.delete();
		// 删除客户端176文件
		fileName = groupPath + bd.id + "." + fileSuffix + "1";
		file = new File(fileName);
		if (file.exists())
			file.delete();
		// 删除客户端1000*600文件
		fileName = groupPath + bd.id + "." + fileSuffix + "2";
		file = new File(fileName);
		if (file.exists())
			file.delete();
		// 删除编辑器文件
		fileName = groupPath + bd.id + "." + fileSuffix + "x";
		file = new File(fileName);
		if (file.exists())
			file.delete();
		// 删除服务器端文件
		fileName = groupPath + bd.id + "." + fileSuffix + "s";
		file = new File(fileName);
		if (file.exists())
			file.delete();
		// if(groupPanel!=null)
		// groupPanel.removeData(bd);

		// save();
	}

	/**
	 * 获取数据组最大id
	 * 
	 * @return
	 */
	public synchronized int getMaxId()
	{
		int b;
		T bd1;
		maxId = 0;
		for (int j = 0; j < dataList.size(); j++)
		{
			bd1 = dataList.get(j);
			b = Integer.parseInt(bd1.id);
			if (b > maxId)
				maxId = b;
		}
		return maxId;
	}

	/**
	 * 获取数据组数据库中最大id
	 * 
	 * @return
	 */
	public synchronized String getMaxDataId()
	{
		int b;
		T bd1;
		maxId = 0;
		for (int j = 0; j < dataList.size(); j++)
		{
			bd1 = dataList.get(j);
			b = Integer.parseInt(bd1.id);
			if (b > maxId)
				maxId = b;
		}

		return String.valueOf(maxId + 1);
	}

	/**
	 * 保存本地数据索引文件
	 * 
	 * @return
	 */
	public boolean saveIndexFile()
	{
		// if(type==1)
		// return true;
		try
		{
			String path = groupPath + "index.txt";
			int i, num = 0;
			int size = dataList.size();
			StringBuffer s = new StringBuffer();
			T bd;
			for (i = 0; i < size; i++)
			{
				bd = (T) dataList.get(i);
				if (bd.geneType == 1)
					continue;
				num++;
			}
			s.append(num + "\r\n");
			for (i = 0; i < size; i++)
			{
				bd = (T) dataList.get(i);
				if (bd.geneType == 1)
					continue;
				s.append(bd.getIndexString() + "\r\n");
			}
			Util.saveFile(path, s.toString().getBytes());

			if (groupPanel != null)
				groupPanel.updateView();
			return true;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 根据数据索引文件初始化数据组
	 * 
	 * @return
	 */
	public boolean initBaseDataByIndexFile()
	{

		String gclass = "com.doteplay.editor.data." + groupId + "." + groupClassId
				+ "Data";
		T bd, bd1;

		// 读取索引文件
		String fn_index = groupPath + "index.txt";
		byte[] buf = Util.loadFile(fn_index);
		if (buf == null)
			return false;
		String s_index = new String(buf);
		int i = 0, j;
		String temp;
		String[] sss;

		Class tmpGroupClass = null;
		try
		{
			tmpGroupClass = Class.forName(gclass);
		} catch (Exception e)
		{
			e.printStackTrace();
			tmpGroupClass = null;
		}
		String[] ss = s_index.split("\r\n");
		// 1
		int size = Integer.parseInt(ss[i++]);
		// 1 沙漠 2008-04-30 12:00:00
		for (j = 0; j < size; j++)
		{
			temp = ss[i++];
			sss = temp.split("\t");
			try
			{
				bd = (T) tmpGroupClass.newInstance();
			} catch (Exception e)
			{
				e.printStackTrace();
				bd = null;
			}
			if (bd == null)
				continue;
			bd.initByIndexFile(this, sss);
			addData(bd);
			bd.isInDB = 0;
			bd.db_version = 0;
		}
		tmpGroupClass = null;
		return true;
	}

	/**
	 * 根据数据库内容刷新更新数据组
	 * 
	 * @param thread
	 */
	public void update(Thread thread)
	{
		if (EditorConfig.useDataBase)
		{

			String gclass = "com.doteplay.editor.data." + groupId + "."
					+ groupClassId + "Data";
			T bd;

			// 清除历史状态
			for (int j = 0; j < dataList.size(); j++)
			{
				bd = dataList.get(j);
				bd.isInDB = 0;
				bd.db_version = 0;
			}
			// 从数据库更新索引文件
			// System.out.println(gclass);
			bd = (T) Util.loadClassByName(gclass);
			bd.dataGroup = this;

			db_loadList(gclass);

			saveIndexFile();

		}
	}

	/**
	 * 数据更新事件响应
	 * 
	 * @param bd
	 */
	public void onDataSaved(T bd)
	{
		if (groupPanel != null)
			groupPanel.updateView();
	}

	/**
	 * 查找符合条件数据对象 数据对象处于open状态
	 * 
	 * @param param
	 * @return
	 */
	public Vector<T> findData(Hashtable<String, Object> param)
	{
		Vector<T> v = new Vector<T>();
		int size = dataList.size();
		T baseData;
		for (int i = 0; i < size; i++)
		{
			baseData = dataList.get(i);
			baseData.open();
			if (baseData.isMatch(param))
			{
				v.addElement(baseData);
			} else
				baseData.close();
		}
		return v;
	}

	/**
	 * 特殊数据刷新，需要重载实现
	 * 
	 * @param component
	 * @param selectedObjs
	 * @return
	 */
	public boolean specialUpdate(Component component, Object[] selectedObjs)
	{
		JOptionPane.showConfirmDialog(component, "没有特殊更新!");
		return false;
	}

	/**
	 * 释放该组所有数据的锁
	 */
	public void releaseAllDataLock()
	{
		if (!EditorConfig.useDataBase)
		{
			return;
		}
		for (T d : datas.values())
		{
			// System.out.println(d.dataId+" "+d.getDataLock());
			d.releaseLock();
		}
	}

	/**
	 * 检查组数据有效性
	 */
	public void checkData(IRuntimeDataHelper datahelper)
	{
		int size = dataList.size();
		T baseData;
		for (int i = 0; i < size; i++)
		{
			baseData = dataList.get(i);
			baseData.open();
			try
			{
				baseData.checkRuntimeData(datahelper);
			} finally
			{
				baseData.close();
			}
		}

	}

	/**
	 * 获取数据过滤器，需要重载实现
	 * 
	 * @return
	 */
	public IDataFilter<T> getDataFilter()
	{
		return this.dataFilter;
	}

	/**
	 * 设置数据过滤器，需要重载实现
	 * 
	 * @param dataFilter
	 */
	public void setDataFilter(IDataFilter<T> dataFilter)
	{
		this.dataFilter = dataFilter;
	}

	// TODO:===数据库========================================

	/**
	 * 根据数据库装载数据
	 * 
	 * @param gclass
	 */
	public void db_loadList(String gclass)
	{

		Connection conn = BaseConnectionPool.getConnection();
		if (conn == null)
			return;

		System.out.println("===" + groupName + " " + "===");

		String sql;
		PreparedStatement pstat;
		ResultSet rs;

		String tableName = EditorConfig.RES_DATA_TABLE_EDITOR;
		try
		{

			sql = "select resourceId,PKId,name,gd_version,type,gd_editorUserId,gd_editorUserName,gd_editorVersion,gd_dateTime,preload,preloadOrder,openFlag from "
					+ tableName + " where resourceType=? order by resourceId";

			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, this.resourceType);

			rs = pstat.executeQuery();
			while (rs.next())
			{
				String id = String.valueOf(rs.getInt("resourceId"));
				// String id = String.valueOf(rs.getInt("PKId"));
				T bd1 = datas.get(id);
				if (bd1 == null)
				{
					bd1 = (T) Util.loadClassByName(gclass);
					bd1.init(this, id);
					if (!EditorConfig.isPublisher)
					{
						if (bd1.db_load(conn))
						{
							this.addData(bd1);
						}
					} else
					{
						this.addData(bd1);
					}
				} else
				{
					// bd1.dataId=""+rs.getInt("dataId");
					// System.out.println(ss[2]);
					// if(bd1.dataId!=null && !bd1.dataId.equals("-1")){
					// bd1.db_version=rs.getInt("gd_version");
					// }

					bd1.PKId = rs.getInt("PKId");
					bd1.db_version = rs.getInt("gd_version");
					bd1.isInDB = 1;

					bd1.lastEditorVersion = rs.getString("gd_editorVersion");

					// 如果不是发布数据，从数据库读取数据
					if (!EditorConfig.isPublisher)
					{
						bd1.nameId = rs.getInt("name");

						bd1.preload = rs.getInt("preload");
						bd1.preloadOrder = rs.getInt("preloadOrder");
						bd1.openFlag = rs.getInt("openFlag");

						// 每条记录都添加了用户和时间显示
						bd1.lastEditUserId = rs.getInt("gd_editorUserId");
						bd1.lastEditUserName = rs
								.getString("gd_editorUserName");
						bd1.lastEditorDateTime = rs.getTimestamp("gd_dateTime");
					}

				}
				// System.out.println("\t" + id + "," +bd1.PKId+","+ bd1.name +
				// ",version:" + bd1.version + ",db_version:"
				// + bd1.db_version + " " + bd1.lastEditUserName + " " +
				// bd1.lastEditorDateTime + " "
				// + bd1.lastEditorDateTime);

			}
			rs.close();
			pstat.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				conn.close();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 从数据库中删除系统生成数据
	 * 
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected boolean db_deleteGeneData()
	{
		Connection conn = BaseConnectionPool.getConnection();
		if (conn == null)
			return false;

		String sql;
		PreparedStatement pstat;
		boolean ok = false;
		try
		{
			// sql = "delete from " + groupTable + " where geneType=1";

			sql = "delete from " + EditorConfig.RES_DATA_TABLE_EDITOR
					+ " where geneType=1 and resourceType=?";

			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, this.resourceType);

			int result = pstat.executeUpdate();
			if (result == 1)
			{
				ok = true;
			}
			pstat.close();
		} catch (Exception e)
		{
			e.printStackTrace();
			ok = false;
		} finally
		{
			try
			{
				conn.close();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return ok;
	}

	/**
	 * 将所有数据的通用信息存入数据库中需要子类重载实现
	 */
	public void db_saveAllCommonData()
	{
	}

	// TODO: ======================XML============================

	@Override
	public Document getDocument()
	{
		Document doc = new DefaultDocument();
		doc.add(this.getElement());
		return doc;
	}

	public void setDocument(Document doc)
	{
		Element root = doc.getRootElement();
	}

	@Override
	public String getXmlFileName()
	{
		return groupPath + groupId + ".xml";
	}

	@Override
	public boolean importXml()
	{
		Document doc = XmlUtil.ReadXml(this.getXmlFileName());
		if (doc == null)
		{
			return false;
		}
		Element root = doc.getRootElement();
		List<Element> list = root.selectNodes(groupClassId + "Data");
		for (Element e : list)
		{

			try
			{
				Attribute attribute = e.attribute("id");
				T data = datas.get(attribute.getValue());
				data.open();
				data.setElement(e);
				data.save();
				data.close();
			} catch (Exception e1)
			{
				System.err.println(e.attribute("id") + ":" + e1.getMessage());
				e1.printStackTrace();
			}
		}
		this.saveIndexFile();
		return true;
	}

	@Override
	public boolean exportXml()
	{
		Document doc = this.getDocument();
		String fileName = this.getXmlFileName();
		// System.out.println("writeXml:\r\n" + fileName + "\r\n" +
		// doc.asXML());
		return XmlUtil.WriteXml(doc, fileName);
	}

	/**
	 * 获取属性Map
	 * 
	 * @return
	 */
	public Map<String, Object> getAttributeMap()
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resourceType", resourceType);
		map.put("groupId", groupId);
		map.put("groupName", groupName);
		map.put("groupClassId", groupClassId);
		map.put("listType", listType);
		map.put("fileSuffix", fileSuffix);
		return map;
	}

	public Element getElement()
	{
		Element element = null;
		try
		{
			element = new DefaultElement(groupId.toUpperCase());
			Map<String, Object> map = this.getAttributeMap();
			XmlUtil.ElementAddAttributes(element, map);
			Object[] bds = groupPanel.getSelectObjects();
			if (bds == null || bds.length < 1)
			{// 说明没有选择任何数据直接导出全部
				for (T data : dataList)
				{
					if (data.geneType != 1
							&& data.lastEditorDateTime.getTime() > whatTime)
					{
						data.open();
						element.add(data.getElement());
						data.close();
					}
				}

			} else
			{// 导出选择项
				for (Object obj : bds)
				{
					T data = (T) obj;
					if (data.geneType != 1
							&& data.lastEditorDateTime.getTime() > whatTime)
					{
						data.open();
						element.add(data.getElement());
						data.close();
					}
				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return element;
	}

	// TODO:====CVS============================
	/**
	 * 是否支持CSV导入/导出，需要重载实现
	 * 
	 * @return
	 */
	public boolean isSupportCSV()
	{
		return false;
	}

	/**
	 * 获取CSV数据
	 * 
	 * @return
	 */
	public String getCSVTitle()
	{
		return "编号,名称";
	}

	// TODO:====================excel===============================
	public String getExcelFileName()
	{
		return groupPath + groupId + ".xls";
	}

	public boolean exportExcel()
	{
		ExcelExporterManager exporter = new ExcelExporterManager(new File(
				this.getExcelFileName()));
		exporter.start(groupName);
		exporter.exporter(this);
		return true;
	}
}
