package com.doteplay.editor.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import jxl.Cell;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.DefaultDocument;

import com.doteyplay.game.editor.ResourceDataConstants;
import com.doteyplay.game.gamedata.data.IGameData;
import com.doteplay.editor.EditorConfig;
import com.doteplay.editor.XEditor;
import com.doteplay.editor.data.text.TextData;
import com.doteplay.editor.database.BaseConnectionPool;
import com.doteplay.editor.datahelper.IRuntimeDataHelper;
import com.doteplay.editor.datahelper.MetaData;
import com.doteplay.editor.datahelper.ParamList;
import com.doteplay.editor.util.Util;
import com.doteplay.editor.util.xml.XmlUtil;

/**
 * 编辑器中所有数据类的抽象父类<br>
 * 主要实现了数据存储、读取、版本管理等功能
 */
public abstract class BaseData<T extends IGameData> extends
		AbstractEditableData implements IXmlElement, ILinkData
{

	/**
	 * 文件名
	 */
	public String fileName;
	/**
	 * 数据类型
	 */
	public String type = "base";
	/**
	 * 客户端资源加载标志
	 */
	public int preload = ResourceDataConstants.PRELOAD_TYPE_DOWNLOAD;
	/**
	 * 客户端资源加载顺序(暂时无用)
	 */
	public int preloadOrder = 0;
	/**
	 * 开关标志
	 */
	public int openFlag = ResourceDataConstants.SWITCH_TYPE_OPEN;
	/**
	 * 初始化信息
	 */
	public String initInfo = "";

	/**
	 * 编辑数据，编辑界面专用数据，目前采用二进制格式写入
	 */
	public byte[] editorData;
	/**
	 * 临时客户端数据
	 */
	public byte[] clientData;
	/**
	 * 临时服务器端数据
	 */
	public byte[] serverData;
	/**
	 * 相关数据组引用
	 */
	public DataGroup dataGroup;
	/**
	 * 被打开引用次数
	 */
	public int refNum = 0;
	/**
	 * 本数据关联对象缓存
	 */
	public Hashtable<String, BaseData> objectDatas = new Hashtable<String, BaseData>();
	/**
	 * 生成方式 0:用户定义 1:系统生成
	 */
	public int geneType = 0;

	public int packDataId = -1;
	public String checkOutText;
	public String checkDataResult = "";

	public T gameData;

	public BaseData(T data)
	{
		this.gameData = data;
	}

	public boolean loadData()
	{
		try
		{
			DataInputStream in = new DataInputStream(new ByteArrayInputStream(
					editorData));

			in.readInt();
			in.readInt();

			gameData.load(in);
			in.close();
			return true;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public void releaseData()
	{

	}

	public boolean saveData()
	{
		try
		{
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(bout);

			out.writeInt(this.getIntId());
			out.writeInt(this.nameId);
			gameData.save(out);

			out.flush();
			out.close();

			editorData = null;
			editorData = bout.toByteArray();
			return true;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public boolean checkData()
	{
		return true;
	}

	// /**
	// * 设置客户端版本数据
	// *
	// * @param versionType 版本类型(EditorConfig.VERSION_TYPE_xxx)
	// * @param vdata
	// */
	// public final void setClientVersionData(int versionType, byte[] vdata) {
	// clientData = null;
	// clientData = vdata;
	// }

	// /**
	// * 获取客户端数据
	// * @param versionType 版本类型(EditorConfig.VERSION_TYPE_xxx)
	// * @return
	// */
	// public final byte[] getClientVersionData(int versionType) {
	// return clientData;
	// }

	// /**
	// * 获取当前客户端数据
	// * @return
	// */
	// public final byte[] getNowClientVersionData() {
	// byte[] bytes = getClientVersionData(EditorConfig.VERSION_TYPE);
	// if (bytes == null) {
	// bytes = clientData;
	// }
	// return bytes;
	// }

	/**
	 * 获取当前数据组类型
	 * 
	 * @return 据组类型
	 */
	public final int getResourceType()
	{
		return this.dataGroup.resourceType;
	}

	//
	// /**
	// * 获取当前数据组资源类型
	// * @return 据组类型
	// */
	// public final int getClientResourceType() {
	// return this.dataGroup.getClientResourceType();
	// }

	/**
	 * 获取唯一key
	 * 
	 * @return
	 */
	public final String getKey()
	{
		return type + "_" + id;
	}

	public final String getTABString()
	{
		return this.id + "\t" + this.nameId + "\t" + this.dataType;
	}

	// /**
	// * 是否支持指定版本数据 子类重载
	// *
	// * @param versionType
	// * @return
	// */
	// public boolean isSupportVersion(int versionType)
	// {
	// return false;
	// }

	/**
	 * 从缺省版本更新指定版本数据
	 * 
	 * @param versionType
	 * @param updateType
	 */
	public boolean updateVersionDataFromDefault(int versionType, int updateType)
	{
		return updateVersionData(EditorConfig.VERSION_DEFAULT_TYPE,
				versionType, updateType, false);
	}

	/**
	 * 从指定版本更新(或转换)数据到其他版本
	 * 
	 * @param fromVersionType
	 * @param toVersionType
	 * @param updateType
	 * @param isChange
	 *            是否转换
	 * @return
	 */
	public boolean updateVersionData(int fromVersionType, int toVersionType,
			int updateType, boolean isChange)
	{
		return false;
	}

	/**
	 * 获取editorData的MD5串
	 * 
	 * @return MD5串
	 */
	public final String getMD5()
	{
		return Util.MD5(editorData);
	}

	/**
	 * 从缺省版本更新全部版本数据
	 */
	public void updateAllVersionDataFromDefault()
	{
		updateVersionDataFromDefault(0, 0);
	}

	/**
	 * 复制数据对象
	 */
	public BaseData duplicate()
	{
		return null;
	}

	/**
	 * 获取id
	 * 
	 * @return
	 */
	public final String getId()
	{
		return id;
	}

	/**
	 * 数据对象是否符合条件
	 * 
	 * @param param
	 * @return
	 */
	public boolean isMatch(Hashtable<String, Object> param)
	{
		return false;
	}

	/**
	 * 数据是否符合条件
	 */
	public boolean isMatch(ParamList params)
	{
		return false;
	}

	/**
	 * 数据对象是否符合条件
	 * 
	 * @param param
	 * @return
	 */
	public boolean isMatch(Object param)
	{
		return false;
	}

	/**
	 * 转换为metadata
	 */
	public void exportMetaData(MetaData metadata)
	{
		if (metadata != null)
		{
			metadata.setStrData("id", this.id);
			metadata.setStrData("dataid", "0");
			metadata.setStrData("name", String.valueOf(this.nameId));
			metadata.setStrData("desc", this.description);
		}
	}

	/**
	 * 根据id获取相关对象（在对象内部建立的原因是能创建一个内部缓存）
	 * 
	 * @param groupType
	 *            数据组类型
	 * @param id
	 *            数据id
	 * @return
	 */
	public final BaseData getObjectData(String groupType, String id)
	{
		if (groupType == null || id == null)
			return null;

		if (type.equals(groupType) && this.id.equals(id))
			return this;

		String tmpKey = groupType + id;
		BaseData bd = objectDatas.get(tmpKey);
		if (bd == null)
		{
			bd = DataManager.getData(groupType, id);
			if (bd != null)
			{
				objectDatas.put(tmpKey, bd);
				bd.open();
				if (!bd.id.equals(id))
				{
					this.markModified();
				}
			}
		}
		return bd;
	}

	public final BaseData getObjectData(String groupType, int id)
	{
		return getObjectData(groupType, "" + id);
	}

	/**
	 * 获取名称获取相关对象（在对象内部建立的原因是能创建一个内部缓存）
	 * 
	 * @param groupType
	 *            数据组类型
	 * @param name
	 *            数据名称
	 * @return
	 */
	public final BaseData getObjectDataByName(String groupType, int nameId)
	{
		BaseData bd = DataManager.getDataByName(groupType, nameId);
		if (bd == null)
			return null;

		String tmpKey = groupType + bd.id;
		if (!objectDatas.containsKey(tmpKey))
		{
			objectDatas.put(tmpKey, bd);
			bd.open();
		}
		return bd;
	}

	/**
	 * 数据组文件后缀
	 * 
	 * @return 数据组文件后缀
	 */
	public final String getFileSuffix()
	{
		return dataGroup.fileSuffix;
	}

	/**
	 * 初始化
	 */
	public boolean init(DataGroup group, String id)
	{
		dataGroup = group;
		isNew = false;
		this.id = id;
		fileName = dataGroup.groupPath + id + "." + dataGroup.fileSuffix;
		return true;
	}

	/**
	 * 释放内存
	 */
	public final void release()
	{
		releaseData();

		int size = objectDatas.size();
		BaseData[] datas = new BaseData[size];
		objectDatas.values().toArray(datas);
		for (int i = 0; i < size; i++)
		{
			datas[i].close();
		}

		objectDatas.clear();
		initInfo = "";

	}

	/**
	 * 设置id
	 * 
	 * @param mid
	 */
	public final void setId(String mid)
	{
		id = mid;
		if (dataGroup == null)
		{
			fileName = "";
			return;
		}
		fileName = dataGroup.groupPath + id + "." + dataGroup.fileSuffix;
	}

	/**
	 * 拷贝EditorData到目标数据
	 * 
	 * @param bd
	 */
	public final void copyEditorDataTo(BaseData bd)
	{
		int size;
		if (editorData != null)
		{
			size = editorData.length;
			bd.editorData = new byte[size];
			System.arraycopy(editorData, 0, bd.editorData, 0, size);
		}
	}

	/**
	 * 复制数据到目标数据
	 * 
	 * @param bd
	 */
	public final void copyDataTo(BaseData bd)
	{
		bd.id = id;
		bd.dataGroup = dataGroup;
		bd.nameId = nameId;
		bd.type = type;
		bd.dataType = dataType;
		bd.openFlag = openFlag;
		bd.preload = preload;
		bd.preloadOrder = preloadOrder;

		int size;
		if (this.editorData != null)
		{
			size = editorData.length;
			bd.editorData = new byte[size];
			System.arraycopy(editorData, 0, bd.editorData, 0, size);
		}
		if (this.clientData != null)
		{
			size = clientData.length;
			bd.clientData = new byte[size];
			System.arraycopy(clientData, 0, bd.clientData, 0, size);
		}
		if (this.serverData != null)
		{
			size = serverData.length;
			bd.serverData = new byte[size];
			System.arraycopy(serverData, 0, bd.serverData, 0, size);
		}
	}

	/**
	 * 保存数据到本地文件
	 * 
	 * @return
	 */
	public final boolean save()
	{
		if (geneType == 1)
			return false;
		if (EditorConfig.VERSION_TYPE != EditorConfig.VERSION_DEFAULT_TYPE
				&& !versionDataEditable)
			return false;
		if (!checkData())
		{
			JOptionPane.showMessageDialog(XEditor.xEditor.getJFrame(),
					this.checkDataResult);
			return false;
		}

		if (isNew)
		{
			if (id == null)
				id = DataManager.getNewDataId(type);
			fileName = dataGroup.groupPath + id + "." + dataGroup.fileSuffix;
		}

		if (!saveData())
			return false;
		if (!saveClientData())
			return false;
		if (!saveServerData())
			return false;
		// save to file!
		if (!Util.saveFile(fileName + "x", editorData))
			return false;
		// // String fn=dataGroup.groupPath+id+"."+dataGroup.fileSuffix;
		// if (clientData != null)
		// {
		// if (!Util.saveFile(fileName, clientData))
		// return false;
		// }
		// if (serverData != null)
		// {
		// if (!Util.saveFile(fileName + "s", serverData))
		// return false;
		// }

		if (isNew)
		{
			dataGroup.addData(this);
			if (dataGroup.groupPanel != null)
				dataGroup.groupPanel.addData(this);
			refNum++;
			isNew = false;
		}
		modified = false;

		if (isInDB == 1)
		{
			version = db_version + 1;
		} else
		{
			version = 0;
		}

		System.out.println(this.getPrintStr("save:"));

		// dataGroup.save();
		return true;
	}

	/**
	 * 数据另存
	 * 
	 * @param tid
	 * @param name
	 * @return
	 */
	public BaseData saveAs(String tid, int name)
	{

		if (tid == null || !Util.isInteger(tid))
		{
			return null;
		}

		BaseData newBaseData = null;

		try
		{
			if (dataGroup.isDataExist(tid))
			{
				newBaseData = dataGroup.getData(tid);
			} else
			{
				newBaseData = dataGroup.newData();
			}

			this.copyDataTo(newBaseData);
			newBaseData.loadData();
			newBaseData.setId(tid);

			newBaseData.nameId = name;
			newBaseData.openFlag = openFlag;
			newBaseData.preload = preload;
			newBaseData.preloadOrder = preloadOrder;

			newBaseData.save();
			newBaseData.dataGroup.saveIndexFile();
			newBaseData.release();
			newBaseData.refNum = 0;

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return newBaseData;

	}

	/**
	 * 版本提升
	 */
	public final void versionUp()
	{
		if (isInDB == 1)
		{
			version = db_version + 1;
		} else
		{
			version = 0;
		}
	}

	/**
	 * 数据是否打开
	 * 
	 * @return
	 */
	public final boolean isOpen()
	{
		return refNum > 0;
	}

	/**
	 * 数据是否删除
	 * 
	 * @return
	 */
	public final boolean isDelete()
	{
		return (openFlag == ResourceDataConstants.SWITCH_TYPE_DELETE);
	}

	/**
	 * 打开数据，从本地文件读取数据到内存中，一般和close()方法成对使用
	 * 
	 * @return
	 */
	public final synchronized boolean open()
	{
		if (isNew)
			return true;

		if (refNum == 0)
		{
			// fileName=dataGroup.groupPath+id+".xml";
			if (geneType == 0)
			{
				editorData = Util.loadFile(fileName + "x");
				if (editorData == null)
				{
					return false;
					// System.out.println("["+type+"]:"+id+"-"+name+":load
					// ok!");
				}
			}
			if (geneType == 0)
				loadData();
		}
		refNum++;
		// if(refNum>0){
		// System.out.println("["+type+"]:"+id+"-"+name+":refnum : "+refNum);
		// }
		// System.out.println("["+type+"]:"+id+"-"+name+":refnum : "+refNum);
		return true;
	}

	public final synchronized boolean openForText()
	{
		if (isNew)
			return true;

		editorData = Util.loadFile(fileName + "x");
		if (editorData == null)
		{
			return false;
		}
		loadData();
		return true;
	}

	/**
	 * 关闭数据，释放内存中的数据，一般和open()方法成对使用
	 */
	public final synchronized void close()
	{
		// if(editable)
		// return;
		clientData = null;

		if (isNew)
		{
			editorData = null;
			release();
			// releaseData();
			return;
		}
		refNum--;
		// System.out.println("["+type+"]:"+id+"-"+name+":refnum : "+refNum);
		if (refNum <= 0)
		{
			editorData = null;
			modified = false;
			release();
			refNum = 0;
			// releaseData();
			// System.out.println("["+type+"]:"+id+"-"+name+":release ok!");
		} else
		{
			// if(modified)
			// {
			// loadData();
			// modified=false;
			// }
		}
	}

	/**
	 * 检查组数据有效性
	 */
	public void checkRuntimeData(IRuntimeDataHelper datahelper)
	{
		datahelper.debugInfoOutput("check " + this.dataGroup.groupName + "."
				+ this.id, 1);
	}

	/**
	 * 保存所有客户端数据
	 * 
	 * @return
	 */
	public boolean saveAllClientData()
	{
		saveClientData();
		return true;
	}

	/**
	 * 保存某个版本客户端数据
	 * 
	 * @return
	 */
	public boolean saveClientData(int versionType)
	{
		clientData = null;
		return true;
	}

	/**
	 * 保存240X320版本客户端数据
	 * 
	 * @return
	 */
	protected boolean saveClientData()
	{
		clientData = null;
		return true;
	}

	/**
	 * 保存服务器端数据
	 * 
	 * @return
	 */
	protected boolean saveServerData()
	{
		serverData = editorData;
		return true;
	}

	/**
	 * 获取用于数据列表显示名称
	 * 
	 * @return
	 */
	public String getFullName()
	{
		StringBuffer tmpBuffer = new StringBuffer();
		if (EditorConfig.BASEDATA_INFORMATION_FULL)
		{
			tmpBuffer.append("<").append(refNum).append(">");
			if (isInDB == 1)
			{
				if (version > db_version)
				{
					tmpBuffer.append("[*]");
				} else if (version < db_version)
				{
					tmpBuffer.append("[！]");
				}
				tmpBuffer.append("[DB]");
			}
			if (geneType == 1)
				tmpBuffer.append("[GN]");

			// if (getClientResourceType() != -1) {
			// tmpBuffer.append("["
			// + ResourceDataConstants.PRELOAD_TYPE_STR[preload] + " "
			// + preloadOrder + "]");
			// }

			// tmpBuffer.append("[" +
			// ResourceDataConstants.SWITCH_TYPE_STR[openFlag] + "]");

			tmpBuffer.append(getId()).append(":");
			if (this.type.equals("text"))
				tmpBuffer.append(((TextData) this).gameData.name);
			else
				tmpBuffer.append(DataManager.getTextById(nameId));

			tmpBuffer.append("(" + version + " " + lastEditorDateTime + " "
					+ lastEditUserName + ")");
		} else
		{
			if (isInDB == 1)
			{
				if (version > db_version)
				{
					tmpBuffer.append("[*]");
				} else if (version < db_version)
				{
					tmpBuffer.append("[！]");
				}
				tmpBuffer.append("[DB] ");
			}
			if (geneType == 1)
				tmpBuffer.append("[GN] ");

			tmpBuffer.append(getId()).append(" : ");

			if (this.type.equals("text"))
				tmpBuffer.append(((TextData) this).gameData.name);
			else
				tmpBuffer.append(DataManager.getTextById(nameId));
		}
		return tmpBuffer.toString();
	}

	@Override
	public String toString()
	{
		StringBuffer tmpBuffer = new StringBuffer();
		if (EditorConfig.BASEDATA_INFORMATION_FULL)
		{
			tmpBuffer.append("<").append(refNum).append(">");
			if (isInDB == 1)
			{
				if (version > db_version)
				{
					tmpBuffer.append("[*]");
				} else if (version < db_version)
				{
					tmpBuffer.append("[！]");
				}
				tmpBuffer.append("[DB]");
			}
			if (geneType == 1)
				tmpBuffer.append("[GN]");

			// if (getClientResourceType() != -1) {
			// tmpBuffer.append("["
			// + ResourceDataConstants.PRELOAD_TYPE_STR[preload] + " "
			// + preloadOrder + "]");
			// }
			//
			// tmpBuffer.append("[" +
			// ResourceDataConstants.SWITCH_TYPE_STR[openFlag] + "]");

			tmpBuffer.append(getId()).append(":");
			if (this.type.equals("text"))
				tmpBuffer.append(((TextData) this).gameData.name);
			else
				tmpBuffer.append(DataManager.getTextById(nameId));

			tmpBuffer.append("(" + version + " " + lastEditorDateTime + " "
					+ lastEditUserName + ")");
		} else
		{
			if (isInDB == 1)
			{
				if (version > db_version)
				{
					tmpBuffer.append("[*]");
				} else if (version < db_version)
				{
					tmpBuffer.append("[！]");
				}
				tmpBuffer.append("[DB] ");
			}
			if (geneType == 1)
				tmpBuffer.append("[GN] ");

			tmpBuffer.append(getId()).append(" : ");

			if (this.type.equals("text"))
				tmpBuffer.append(((TextData) this).gameData.name);
			else
				tmpBuffer.append(DataManager.getTextById(nameId));
		}

		// if (isInDB == 1) {
		// if (version != db_version)
		// tmpBuffer.append("[*]");
		// tmpBuffer.append("[DB]");
		// }
		// if (geneType == 1)
		// tmpBuffer.append("[GN]");
		//
		// if (getClientResourceType() != -1) {
		// tmpBuffer.append("["
		// + ResourceDataConstants.PRELOAD_TYPE_STR[preload] + " "
		// + preloadOrder + "]");
		// }
		//
		// tmpBuffer.append("[" +
		// ResourceDataConstants.SWITCH_TYPE_STR[openFlag] + "]");
		//
		// tmpBuffer.append(getId()).append(":").append(name);

		return tmpBuffer.toString();
	}

	/**
	 * 本地数据对比数据库数据
	 * 
	 * @param conn
	 * @return
	 */
	public String checkSum(Connection conn)
	{
		// 系统生成的忽略
		if (this.geneType == 1)
		{
			return "true";
		}

		int db_openFlag = 0, db_preload = 0, db_preloadOrder = 0, db_gd_version = 0;
		Date db_gd_dateTime = null;
		String db_name = null;

		try
		{
			String sql;
			PreparedStatement pstat;
			ResultSet rs;

			sql = "select name,openFlag,preload,preloadOrder,gd_dateTime,gd_version from "
					+ EditorConfig.RES_DATA_TABLE_EDITOR
					+ " where resourceId=? and resourceType=?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, id);
			pstat.setInt(2, this.getResourceType());

			rs = pstat.executeQuery();
			if (rs.next())
			{
				// 数据库数据是否和本地一致
				db_name = rs.getString("name");
				db_openFlag = rs.getByte("openFlag");
				db_preload = rs.getByte("preload");
				db_preloadOrder = rs.getInt("preloadOrder");
				db_gd_dateTime = rs.getTimestamp("gd_dateTime");
				db_gd_version = rs.getInt("gd_version");

			}
			rs.close();
			pstat.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		// 新建数据
		if (db_name == null)
		{
			System.out.println("db_editorData: null");
			return "新增";
		}

		try
		{

			if (this.version != db_gd_version)
			{
				return "版本号有改动:[" + version + "] > [" + db_gd_version + "]";
			}

			// 此处用时间判断是没有道理的因为编辑的时间和发布库的时间原本就不是用一个时间所以根本没有可比性
			// if (this.lastEditorDateTime.after(db_gd_dateTime)) {
			// return "时间变动:[" + this.lastEditorDateTime + "] > [" +
			// db_gd_dateTime + "]";
			// }

			// this.open();
			//
			// int loc_editorDataLength = editorData.length;
			// int db_editorDataLength = db_editorData.length;
			//
			// String loc_editorDataMD5 = Util.MD5(editorData);
			// String db_editorDataMD5 = Util.MD5(db_editorData);
			//
			// this.close();
			//
			// db_editorData = null;
			//
			// if (loc_editorDataLength != db_editorDataLength) {
			// return "大小变动:[" + loc_editorDataLength + "] <> [" +
			// db_editorDataLength + "]";
			// }
			//
			// if (!loc_editorDataMD5.equals(db_editorDataMD5)) {
			// db_editorData = null;
			// return "内容变动";
			// }

			String loc_name = DataManager.getTextById(this.nameId);
			if (!db_name.equals(loc_name))
			{
				return "名称变动:[" + loc_name + "] <> [" + db_name + "]";
			}

			if (db_openFlag != this.openFlag || db_preload != this.preload
					|| db_preloadOrder != this.preloadOrder)
			{
				return "标志变动(openFlag,preload,preloadOrder):[" + this.openFlag
						+ "," + this.preload + "," + this.preloadOrder
						+ "] <> [" + db_openFlag + "," + db_preload + ","
						+ db_preloadOrder + "]";
			}

		} catch (Exception e)
		{
			e.printStackTrace();
			return "异常数据比对";
		}

		// db_editorData = null;

		return "true";
	}

	// TODO:========数据库=========================================
	/**
	 * 从数据库装载数据，并保存到本地文件
	 * 
	 * @return
	 */
	public final boolean db_load(Connection conn)
	{

		if (conn == null)
			return false;
		boolean ok = false;

		try
		{
			if (db_loadCommonData(conn))
			{
				if (editorData != null && editorData.length > 0)
				{
					ok = true;
				}
			}
			if (ok)
				isInDB = 1;
			else
				isInDB = 0;
			if (editorData != null)
			{
				Util.saveFile(fileName + "x", editorData);
				editorData = null;
			}
			if (clientData != null)
			{
				Util.saveFile(fileName, clientData);
				clientData = null;
			}
			if (serverData != null)
			{
				Util.saveFile(fileName + "s", serverData);
				serverData = null;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			ok = false;
		}

		// try {
		// conn.close();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		return ok;
	}

	/**
	 * 从数据库删除对象数据
	 * 
	 * @return
	 */
	public final boolean db_delete()
	{
		if (dataTable == null)
			return false;
		Connection conn = BaseConnectionPool.getConnection();
		if (conn == null)
			return false;
		boolean ok = false;
		try
		{

			// TODO:暂时屏蔽
			// if(db_deleteCommonData(conn)){
			// ok=true;
			// }

			// writeLog("删除");
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
	 * 从数据库中删除对象数据
	 * 
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public final boolean db_deleteCommonData(Connection conn) throws Exception
	{

		// try{
		// conn.setAutoCommit(false); //设置不会自动提交
		// stmt = conn.createStatement();
		// stmt.executeQuery("SELECT * FROM temp_info");
		// stmt.executeUpdate("UPDATE temp_info SET ip='***.***.***.***'");
		// conn.commit(); //提交事务
		// System.out.println("Ok!");
		// conn.close();
		// }catch(SQLException e){
		// try{
		// conn.rollback(); // 操作不成功，回滚事务
		// }catch(SQLException r){
		// System.out.println(r.getMessage());
		// }
		// System.out.println(e.getMessage());
		// }

		String sql;
		PreparedStatement pstat;
		boolean ok = false;

		try
		{

			conn.setAutoCommit(false); // 设置不会自动提交

			sql = "delete from " + EditorConfig.RES_DATA_TABLE_EDITOR
					+ " where resourceId=? and resourceType=?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, id);
			pstat.setInt(2, this.getResourceType());
			pstat.executeUpdate();
			conn.commit(); // 提交事务
			pstat.close();

			ok = true;
		} catch (SQLException e)
		{
			try
			{
				conn.rollback(); // 操作不成功，回滚事务
			} catch (SQLException r)
			{
				System.out.println(r.getMessage());
			}
			System.out.println(e.getMessage());
			ok = false;
		}

		return ok;
	}

	/**
	 * 从数据库记录中读取对象数据
	 * 
	 * @param rs
	 * @throws Exception
	 */
	public final void db_readCommonDataByResultSet(ResultSet rs)
			throws Exception
	{

		PKId = rs.getInt("PKId");
		nameId = rs.getInt("name");
		dataType = rs.getInt("type");
		description = rs.getString("description");

		preload = rs.getInt("preload");
		preloadOrder = rs.getInt("preloadOrder");
		openFlag = rs.getInt("openFlag");

		lastEditUserId = rs.getInt("gd_editorUserId");
		lastEditUserName = rs.getString("gd_editorUserName");
		lastEditorVersion = rs.getString("gd_editorVersion");
		lastEditorDateTime = rs.getTimestamp("gd_dateTime");

		db_version = rs.getInt("gd_version");
		editorData = rs.getBytes("gd_editorData");
		serverData = rs.getBytes("gd_serverData");
		clientData = rs.getBytes("gd_clientData");
		checkOutText = rs.getString("gd_description");

		// System.out.println("db_readDataByResultSet:"+name+","+checkOutText);
	}

	public final void db_readHistoryCommonDataByResultSet(ResultSet rs)
			throws Exception
	{

		nameId = rs.getInt("name");
		dataType = rs.getInt("type");
		description = rs.getString("description");

		preload = rs.getInt("preload");
		preloadOrder = rs.getInt("preloadOrder");
		openFlag = rs.getInt("openFlag");

		lastEditUserId = rs.getInt("gd_editorUserId");
		lastEditUserName = rs.getString("gd_editorUserName");
		lastEditorVersion = rs.getString("gd_editorVersion");
		lastEditorDateTime = rs.getTimestamp("gd_dateTime");

		db_version = rs.getInt("gd_version");
		editorData = rs.getBytes("gd_editorData");
		serverData = rs.getBytes("gd_serverData");
		clientData = rs.getBytes("gd_clientData");
		checkOutText = rs.getString("gd_description");

		// System.out.println("db_readDataByResultSet:"+name+","+checkOutText);
	}

	/**
	 * 装载从数据库中装载数据
	 * 
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected final boolean db_loadCommonData(Connection conn) throws Exception
	{

		String sql;
		PreparedStatement pstat;
		ResultSet rs;
		boolean ok = false;
		// sql = "select * from " + dataTable + " where PKId=?";

		String tableName = EditorConfig.RES_DATA_TABLE_EDITOR;
		sql = "select * from " + tableName
				+ " where resourceId=? and resourceType=?";

		pstat = conn.prepareStatement(sql);
		pstat.setString(1, id);
		pstat.setInt(2, this.getResourceType());

		rs = pstat.executeQuery();
		if (rs.next())
		{

			db_readCommonDataByResultSet(rs);

			version = db_version;

			ok = true;

		}
		rs.close();
		pstat.close();
		return ok;
	}

	protected final int db_publishCommonData1(Connection conn) throws Exception
	{

		String sql;
		PreparedStatement pstat;
		int result;

		if (isInDB == 1)
		{
			sql = "update " + EditorConfig.RES_DATA_TABLE_EDITOR + " set "
					+ "name=?," + "type=?," + "description=?," + "openFlag=?,"
					+ "preload=?," + "preloadOrder=?," + "geneType=?,"
					+ "gd_editorData=?," + "gd_serverData=?,"
					+ "gd_clientResType=?," + "gd_clientData=?,"
					+ "gd_client176Data=?," + "gd_client128Data=?,"
					+ "gd_linkData=?," + "gd_editorUserId=?,"
					+ "gd_editorUserName=?," + "gd_editorVersion=?,"
					+ "gd_version=?," + "gd_description=?,"
					+ "gd_dateTime=CURRENT_TIMESTAMP"
					+ " where resourceId=? and resourceType=?";

			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, nameId);
			pstat.setInt(2, dataType);
			pstat.setString(3, description);
			pstat.setInt(4, openFlag);
			pstat.setInt(5, preload);
			pstat.setInt(6, preloadOrder);
			pstat.setInt(7, geneType);
			pstat.setBytes(8, editorData);
			pstat.setBytes(9, serverData);
			// pstat.setInt(10, getClientResourceType());
			pstat.setInt(10, 0);
			pstat.setBytes(11, clientData);
			pstat.setBytes(12, getLinkData());
			pstat.setInt(13, lastEditUserId);
			pstat.setString(14, lastEditUserName);
			pstat.setString(15, lastEditorVersion);
			pstat.setInt(16, version);
			pstat.setString(17, checkOutText);
			pstat.setString(18, id);
			pstat.setInt(19, getResourceType());

			result = pstat.executeUpdate();
			pstat.close();
		} else
		{
			sql = "insert into "
					+ EditorConfig.RES_DATA_TABLE_EDITOR
					+ " ("
					+ "resourceId,"
					+ "resourceType,"
					+ "name,"
					+ "type,"
					+ "description,"
					+ "openFlag,"
					+ "preload,"
					+ "preloadOrder,"
					+ "geneType,"
					+ "gd_editorData,"
					+ "gd_serverData,"
					+ "gd_clientResType,"
					+ "gd_clientData,"
					+ "gd_linkData,"
					+ "gd_editorUserId,"
					+ "gd_editorUserName,"
					+ "gd_editorVersion,"
					+ "gd_version,"
					+ "gd_description,"
					+ "gd_dateTime,"
					+ "PKId"
					+ ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,?)";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, id);
			pstat.setInt(2, this.getResourceType());
			pstat.setInt(3, nameId);
			pstat.setInt(4, dataType);
			pstat.setString(5, description);
			pstat.setInt(6, openFlag);
			pstat.setInt(7, preload);
			pstat.setInt(8, preloadOrder);
			pstat.setInt(9, geneType);
			pstat.setBytes(10, editorData);
			pstat.setBytes(11, serverData);
			// pstat.setInt(12, getClientResourceType());
			pstat.setInt(12, 0);
			pstat.setBytes(13, clientData);
			pstat.setBytes(14, getLinkData());
			pstat.setInt(15, lastEditUserId);
			pstat.setString(16, lastEditUserName);
			pstat.setString(17, lastEditorVersion);
			pstat.setInt(18, version);
			pstat.setString(19, checkOutText);
			pstat.setInt(20, PKId);

			result = pstat.executeUpdate();
			pstat.close();

			db_version = 0;
		}

		if (result > 0)
		{
			isInDB = 1;
		}

		// System.out.println(sql);

		// System.out.println("db_saveCommonData:"+this.getName()+"
		// "+version+","+db_version);

		return result;
	}

	/**
	 * 保存数据，多属性数据或需要更新其他数据表
	 * 
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected final int db_saveCommonData(Connection conn) throws Exception
	{

		String sql;
		PreparedStatement pstat;
		int result = 0;
		int ver = 0;

		conn.setAutoCommit(false); // 设置不会自动提交

		if (isInDB == 1)
		{
			if (EditorConfig.isPublisher)
			{// 发布不用更改数据版本
				ver = version;
			} else
			{
				ver = db_version + 1;
			}

			try
			{

				sql = "update " + EditorConfig.RES_DATA_TABLE_EDITOR + " set "
						+ "name=?," + "type=?," + "description=?,"
						+ "openFlag=?," + "preload=?," + "preloadOrder=?,"
						+ "geneType=?," + "gd_editorData=?,"
						+ "gd_serverData=?," + "gd_clientResType=?,"
						+ "gd_clientData=?," + "gd_linkData=?,"
						+ "gd_editorUserId=?," + "gd_editorUserName=?,"
						+ "gd_editorVersion=?," + "gd_version=?,"
						+ "gd_description=?," + "gd_dateTime=CURRENT_TIMESTAMP"
						+ " where resourceId=? and resourceType=?";

				pstat = conn.prepareStatement(sql);
				pstat.setInt(1, nameId);
				pstat.setInt(2, dataType);
				pstat.setString(3, description);
				pstat.setInt(4, openFlag);
				pstat.setInt(5, preload);
				pstat.setInt(6, preloadOrder);
				pstat.setInt(7, geneType);
				pstat.setBytes(8, editorData);
				pstat.setBytes(9, serverData);
				// pstat.setInt(10, this.getClientResourceType());
				pstat.setInt(10, 0);
				pstat.setBytes(11, clientData);
				pstat.setBytes(12, this.getLinkData());
				pstat.setInt(13, XEditor.user.id);
				pstat.setString(14, XEditor.user.name);
				pstat.setString(15, EditorConfig.VERSION_BUILD);
				pstat.setInt(16, ver);
				pstat.setString(17, checkOutText);
				pstat.setString(18, id);
				pstat.setInt(19, this.getResourceType());
				result = pstat.executeUpdate();

				conn.commit(); // 提交事务

				pstat.close();

			} catch (SQLException e)
			{
				e.printStackTrace();
				try
				{
					conn.rollback(); // 操作不成功，回滚事务
				} catch (SQLException r)
				{
					System.out.println(r.getMessage());
				}
				System.out.println(e.getMessage());
			}

		} else
		{
			if (EditorConfig.isPublisher)
			{// 发布不用更改数据版本
				ver = version;
			} else
			{
				ver = 1;
			}

			try
			{

				sql = "insert into "
						+ EditorConfig.RES_DATA_TABLE_EDITOR
						+ " ("
						+ "resourceId,"
						+ "resourceType,"
						+ "name,"
						+ "type,"
						+ "description,"
						+ "openFlag,"
						+ "preload,"
						+ "preloadOrder,"
						+ "geneType,"
						+ "gd_editorData,"
						+ "gd_serverData,"
						+ "gd_clientResType,"
						+ "gd_clientData,"
						+ "gd_linkData,"
						+ "gd_editorUserId,"
						+ "gd_editorUserName,"
						+ "gd_editorVersion,"
						+ "gd_version,"
						+ "gd_description,"
						+ "gd_dateTime"
						+ ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP)";

				pstat = conn.prepareStatement(sql);
				pstat.setString(1, id);
				pstat.setInt(2, this.getResourceType());
				pstat.setInt(3, nameId);
				pstat.setInt(4, dataType);
				pstat.setString(5, description);
				pstat.setInt(6, openFlag);
				pstat.setInt(7, preload);
				pstat.setInt(8, preloadOrder);
				pstat.setInt(9, geneType);
				pstat.setBytes(10, editorData);
				pstat.setBytes(11, serverData);
				// pstat.setInt(12, this.getClientResourceType());
				pstat.setInt(12, 0);
				pstat.setBytes(13, clientData);
				pstat.setBytes(14, this.getLinkData());
				pstat.setInt(15, XEditor.user.id);
				pstat.setString(16, XEditor.user.name);
				pstat.setString(17, EditorConfig.VERSION_BUILD);
				pstat.setInt(18, ver);
				pstat.setString(19, checkOutText);
				result = pstat.executeUpdate();

				conn.commit(); // 提交事务

				pstat.close();

				db_version = 0;

			} catch (SQLException e)
			{
				try
				{
					conn.rollback(); // 操作不成功，回滚事务
				} catch (SQLException r)
				{
					r.printStackTrace();
				}
				System.out.println(e.getMessage());
			}

		}

		if (result > 0)
		{
			isInDB = 1;
		}

		// System.out.println(sql);

		// System.out.println("db_saveCommonData:"+this.getName()+"
		// "+version+","+db_version);

		return result;
	}

	/**
	 * 获取用于打印的数据字符串
	 * 
	 * @param s
	 * @return
	 */
	private final String getPrintStr(String s)
	{
		// int editorDataSize = Util.getByteArrayLenth(editorData);
		// int clinetDataSize = Util.getByteArrayLenth(clientData);
		// int serverDataSize = Util.getByteArrayLenth(serverData);
		// int client176DataSize = Util.getByteArrayLenth(client176Data);
		// int client128DataSize = Util.getByteArrayLenth(client128Data);
		// StringBuffer sb = new StringBuffer();
		// sb.append(s);
		// sb.append(this.id+"_"+this.name);
		// sb.append(" editor:"+editorDataSize);
		// sb.append(" server:"+serverDataSize);
		// sb.append(" client:"+clinetDataSize);
		// sb.append(" client176:"+client176DataSize);
		// sb.append(" client128:"+client128DataSize);

		int editorDataSize = Util.getByteArrayLenth(editorData);
		int clinetDataSize = Util.getByteArrayLenth(clientData);
		int serverDataSize = Util.getByteArrayLenth(serverData);
		StringBuffer sb = new StringBuffer();
		sb.append(s);
		sb.append(this.id + "\t" + this.nameId);
		// sb.append(" editor:"+editorDataSize);
		// sb.append(" server:"+serverDataSize);
		// sb.append(" client:"+clinetDataSize);
		// sb.append(" client176:"+client176DataSize);
		return sb.toString();
	}

	/**
	 * 保存数据到数据库
	 * 
	 * @return
	 */
	public final boolean db_save()
	{

		// 系统生成数据不许提交数据库
		if (geneType == 1)
		{
			return false;
		}

		if (EditorConfig.isPublisher)
		{
			// 不需要检查版本

		} else
		{

			if (isDBVersionConflict())
			{
				strError = "当前版本低于数据库中的版本，请先更新数据！";
				return false;
			}

			if (checkDataBuildVersion() == false)
			{
				strError = "当前编辑器版本较低，无法提交该数据，请先更新编辑器！";
				return false;
			}
		}

		int result = -1;

		Connection conn = BaseConnectionPool.getConnection();
		if (conn == null)
			return false;

		try
		{
			open();

			try
			{

				if (geneType != 1)
				{
					saveClientData();
					saveServerData();
				}

				result = db_saveCommonData(conn);

				if (result > 0)
				{
					String action = "";
					if (isInDB == 1)
					{
						action = "修改";
					} else
					{
						action = "添加";
					}

					if (!EditorConfig.isPublisher)
					{
						db_version++;
						version = db_version;
					} else
					{
						db_version = version;
					}

					dataGroup.onDataSaved(this);

					System.out.println(this.getPrintStr("db_save:"));

				}
				// =====================================================

			} catch (Exception e)
			{
				e.printStackTrace();
			}

			close();
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
		return (result > 0) ? true : false;
	}

	/**
	 * 获取int的id
	 * 
	 * @return
	 */
	public int getIntId()
	{
		if (id == null)
			return -1;

		return Integer.parseInt(id);
	}

	/**
	 * 获取名称
	 * 
	 * @return
	 */
	public int getName()
	{
		return nameId;
	}

	/**
	 * 获取数据名称
	 * 
	 * @return
	 */
	public String getBaseName()
	{
		if (id == null)
			return "未命名";
		if (this.type.equals("text"))
			return id + ":" + ((TextData) this).gameData.name;
		else
			return id + ":" + DataManager.getTextById(nameId);
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 */
	public void setName(Integer name)
	{
		this.nameId = name;
	}

	/**
	 * 获取数据子类型名称数组 用于过滤数据
	 * 
	 * @return
	 */
	public String[] getDataTypeNames()
	{
		return null;
	}

	// TODO: ========CVS==================================================

	/**
	 * 获取CSV文件格式数据，需重载实现
	 * 
	 * @return
	 */
	public String getCSVData()
	{
		return id + "," + nameId;
	}

	/**
	 * 获取CSV文件格式数据，需重载实现
	 * 
	 * @param ss
	 * @return
	 */
	public boolean setCSVData(String[] ss)
	{
		id = ss[0];
		nameId = Integer.parseInt(ss[1]);
		return true;
	}

	/**
	 * 给数据对象赋值，begin从数组中第几个位置开始
	 */
	public int setExcelData(Cell[] cells, int begin)
	{
		int i = begin;
		if (cells != null)
		{
			id = cells[i++].getContents();
			nameId = Integer.parseInt(cells[i++].getContents());
			System.err.println(nameId + "======================>" + id);
		}
		return i;
	}

	/**
	 * 获取Excel数据，需重载实现
	 */
	public String[] getExcelData()
	{
		String[] ss = new String[]
		{ id, DataManager.getTextById(nameId) };
		return ss;
	}

	/**
	 * 设置Excel数据，需重载实现
	 */
	public void setExcelData(String[] ss)
	{
		id = ss[0];
		nameId = Integer.parseInt(ss[1]);
	}

	/**
	 * 获得Excel格式，需重载实现
	 */
	public WritableSheet getWritableSheet(WritableSheet sheet,
			WritableCellFormat wcf)
	{
		try
		{
			sheet.addCell(new Label(0, 0, "数据编号", wcf));
			sheet.addCell(new Label(1, 0, "名称", wcf));
			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 30);
		} catch (RowsExceededException e)
		{
			e.printStackTrace();
		} catch (WriteException e)
		{
			e.printStackTrace();
		}
		return sheet;
	}

	// TODO:========XML======================================================

	/**
	 * 获取属性Map，需重载实现
	 * 
	 * @return
	 */
	@Override
	public Map<String, Object> getAttributes()
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("name", nameId);
		// map.put("description", description);
		// map.put("dataType", dataType);
		// map.put("preload", preload);
		// map.put("openFlag", openFlag);
		// map.put("lastEditUserId", lastEditUserId);
		// map.put("lastEditUserName", lastEditUserName);
		// map.put("lastEditorDateTime", lastEditorDateTime);
		// map.put("lastEditorVersion", lastEditorVersion);
		// map.put("version", version);
		return map;
	}

	/**
	 * 设置属性Map，需重载实现
	 * 
	 * @return
	 */
	@Override
	public void setAttributes(Map<String, Object> map)
	{
		id = (String) map.get("id");
		nameId = (Integer) map.get("name");
		// description = (String) map.get("description");
		// dataType = Integer.parseInt((String) map.get("dataType"));
		// preload = Integer.parseInt((String) map.get("preload"));
		// openFlag = Integer.parseInt((String) map.get("openFlag"));
		// lastEditUserId = Integer.parseInt((String)
		// map.get("lastEditUserId"));
		// lastEditUserName = (String) map.get("lastEditUserName");
		// lastEditorDateTime = Timestamp.valueOf((String)
		// map.get("lastEditorDateTime"));
		// lastEditorVersion = (String) map.get("lastEditorVersion");
		// version = Integer.parseInt((String) map.get("version"));
	}

	@Override
	public Element getElement()
	{
		Element element = XmlUtil.CreateElement(this);
		XmlUtil.createCDATAElement(element, "description", description);
		return element;
	}

	@Override
	public void setElement(Element e)
	{
		XmlUtil.PutAttributes(this, e);
		description = XmlUtil.getStringValueByCDATAElement(e
				.element("description"));
	}

	/**
	 * 读取Xml文件，需重载实现
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean importXmlFile(String fileName)
	{
		try
		{
			Document doc = XmlUtil.ReadXml(fileName);
			if (doc == null)
			{
				return false;
			}
			Element root = doc.getRootElement();
			this.open();
			this.setElement(root);
			this.save();
			this.close();
			this.dataGroup.saveIndexFile();
			return true;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 导出Xml文件，需重载实现
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean exportXmlFile(String fileName)
	{
		Document doc = new DefaultDocument();
		doc.add(this.getElement());
		// System.out.println("writeXml:\r\n" + fileName + "\r\n" +
		// doc.asXML());
		return XmlUtil.WriteXml(doc, fileName);
	}

	// TODO:=========LOCK============================================

	/**
	 * 当前用户持的数据锁
	 */
	private DataLock dataLock = new DataLock();

	/**
	 * 获取当前用户持有的锁
	 * 
	 * @return
	 */
	public DataLock getDataLock()
	{
		return dataLock;
	}

	/**
	 * 是否被别人锁定
	 * 
	 * @return
	 */
	public boolean isLockedByOther()
	{
		if (dataLock == null)
		{
			return false;
		}
		if (dataLock.hasLock() && dataLock.isLockedByOther())
		{
			return true;
		}
		return false;
	}

	/**
	 * 是否被自己锁定
	 * 
	 * @return
	 */
	public boolean isLockedByMe()
	{
		if (dataLock == null)
		{
			return false;
		}
		if (dataLock.hasLock() && dataLock.isLockedByMe())
		{
			return true;
		}
		return false;
	}

	/**
	 * 获得锁定用户名
	 * 
	 * @return
	 */
	public String getLockUserName()
	{
		if (dataLock == null)
		{
			return "";
		}
		return dataLock.lockUser;
	}

	public boolean hasLock()
	{
		if (dataLock == null)
		{
			return false;
		}
		return dataLock.hasLock();
	}

	/**
	 * 获取锁
	 */
	public void getLock()
	{

		dataLock = readLockFromDB();

		// 如果没锁直接上锁
		if (dataLock == null || !dataLock.hasLock())
		{
			dataLock = new DataLock(true, XEditor.user.id, XEditor.user.name);
			writeLockToDB(dataLock);
			return;
		}

		// 目前的锁是别人上的不改变当前锁
		if (dataLock.hasLock() && dataLock.isLockedByOther())
		{
			JOptionPane.showMessageDialog(XEditor.xEditor.getJFrame(),
					"注意！当前数据正在被[" + dataLock.lockUser + "]编辑并锁定");
			return;
		}
	}

	/**
	 * 释放锁
	 */
	public void releaseLock()
	{
		if (dataLock == null)
		{
			return;
		}

		// 如果有锁，并且锁是自己上的释放他
		if (dataLock.hasLock() && dataLock.isLockedByMe())
		{
			// System.out.println("release Lock"+dataLock);
			clearLock();
			return;
		}
	}

	/**
	 * 清除锁
	 */
	public void clearLock()
	{
		if (deleteLockFromDB())
		{
			dataLock = new DataLock(false, -1, null);
		}
	}

	// TODO==========检查数据版本号=============
	public boolean chechVersion()
	{
		String infoOfDB = readInfoFromDB();
		if (infoOfDB == null || infoOfDB.isEmpty()
				|| infoOfDB.indexOf("_") <= 0)
		{
			return true;
		}

		String[] tempArray = infoOfDB.split("_");
		int editorUserId = Integer.parseInt(tempArray[0]);
		int versionOfDB = Integer.parseInt(tempArray[1]);
		if (editorUserId == XEditor.user.id)
		{// 自己编辑应该不会错
			return true;
		}

		if (versionOfDB > db_version)
		{
			return false;
		}

		if (db_version > version)
		{
			return false;
		}
		return true;
	}

	/**
	 * 从数据库中获取版本号 编辑用户Id_版本号
	 * 
	 * @return:editerUserId_version(28_16)
	 */
	public String readInfoFromDB()
	{
		String info = null;
		Connection conn = BaseConnectionPool.getConnection();
		if (conn == null)
		{
			return info;
		}
		String tableName = EditorConfig.RES_DATA_TABLE_EDITOR;

		try
		{
			String sql = "";
			PreparedStatement pstat = null;
			ResultSet res = null;

			sql = "SELECT gd_editorUserId,gd_version FROM " + tableName
					+ " WHERE resourceId=? AND resourceType=?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, id);
			pstat.setInt(2, this.getResourceType());

			res = pstat.executeQuery();
			if (res.next())
			{
				info = String.valueOf(res.getInt("gd_editorUserId"));
				info += "_";
				info += String.valueOf(res.getInt("gd_version"));
			}
			res.close();
			pstat.close();

		} catch (Exception e)
		{
			e.printStackTrace();
			return info;
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

		return info;
	}

	private boolean deleteLockFromDB()
	{
		Connection conn = BaseConnectionPool.getConnection();
		if (conn == null)
		{
			return false;
		}

		try
		{
			String sql;
			PreparedStatement pstat;

			sql = "delete from " + EditorConfig.RES_DATA_TABLE_LOCK
					+ " where resourceId=? and resourceType=?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, id);
			pstat.setInt(2, this.getResourceType());

			pstat.executeUpdate();
			pstat.close();
			// conn.close();

			return true;

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
		return false;

	}

	/**
	 * 设置数据锁
	 * 
	 * @param lock
	 */
	private void writeLockToDB(DataLock lock)
	{

		Connection conn = BaseConnectionPool.getConnection();
		if (conn == null)
		{
			return;
		}

		try
		{
			String sql;
			PreparedStatement pstat;

			sql = "insert into "
					+ EditorConfig.RES_DATA_TABLE_LOCK
					+ " (resourceId,resourceType,lockUserId,lockUserName) values (?,?,?,?)";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, id);
			pstat.setInt(2, this.getResourceType());
			pstat.setInt(3, lock.lockUserId);
			pstat.setString(4, lock.lockUser);

			pstat.executeUpdate();
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
	 * 获得数据锁
	 * 
	 * @return
	 */
	private DataLock readLockFromDB()
	{

		DataLock lock = null;

		Connection conn = BaseConnectionPool.getConnection();
		if (conn == null)
		{
			return null;
		}

		String sql;
		PreparedStatement pstat;
		ResultSet rs;
		sql = "select lockUserId,lockUserName from "
				+ EditorConfig.RES_DATA_TABLE_LOCK
				+ " where resourceId=? and resourceType=?";

		System.out.println(sql);

		try
		{
			pstat = conn.prepareStatement(sql);

			pstat.setString(1, id);
			pstat.setInt(2, this.getResourceType());

			rs = pstat.executeQuery();
			if (rs.next())
			{
				lock = new DataLock(true, rs.getInt("lockUserId"),
						rs.getString("lockUserName"));
			}
			rs.close();
			pstat.close();

		} catch (Exception e1)
		{
			e1.printStackTrace();
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
		return lock;
	}

	// TODO:===============资源关联============================
	/**
	 * 获取资源关联接口 如果数据有资源关联需要继承此接口，没有资源关联返回null
	 */
	@Override
	public Map<String, LinkData> getLinkDataMap()
	{
		return null;
	}

	/**
	 * 关联数据
	 * 
	 * @return
	 */
	public byte[] getLinkData()
	{

		Map<String, LinkData> linkDataMap = this.getLinkDataMap();
		if (linkDataMap == null)
		{
			return null;
		}

		// // 如果数据本身就是资源要加入资源列表中
		// if (this.getClientResourceType() != -1) {
		// LinkDataUtil.associateDataResource(linkDataMap, this
		// .getClientResourceType(), this.getIntId());
		// }

		int allSize = 0;
		List<LinkData>[] linkLevel = new List[LinkData.LEVEL_COUNTS];
		for (LinkData linkData : linkDataMap.values())
		{
			if (linkLevel[linkData.level] == null)
			{
				linkLevel[linkData.level] = new ArrayList<LinkData>();
			}
			linkLevel[linkData.level].add(linkData);
		}

		System.out.println(this.getName() + " allSize=" + allSize);

		byte[] linkData = null;

		try
		{
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(bout);

			out.writeInt(allSize);

			int levels = linkLevel.length;
			out.writeByte(levels);
			for (int i = 0; i < levels; i++)
			{
				List<LinkData> linkLevelList = linkLevel[i];
				if (linkLevelList == null)
				{
					out.writeShort(0);
				} else
				{
					// 按照资源类型排序
					LinkDataUtil.sortLinkDataList(linkLevelList);

					out.writeShort(linkLevelList.size());
					for (LinkData res : linkLevelList)
					{
						out.writeByte(res.type);
						out.writeShort(res.id);
					}
				}

			}

			out.flush();
			out.close();
			linkData = bout.toByteArray();

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return linkData;
	}

	// ==============================数据版本============================================

	protected String strError;

	public String getErrorStr()
	{
		return id + " " + nameId + ":" + strError;

	}

	/**
	 * 从数据库获取获取数据版本
	 * 
	 * @return
	 */
	public String getLastEditorVersionFromDB()
	{
		String ver = "";

		Connection conn = BaseConnectionPool.getConnection();
		if (conn == null)
		{
			return null;
		}

		String sql;
		PreparedStatement pstat;
		ResultSet rs;
		// sql = "select gd_editorVersion from " + dataTable + " where PKId=?";

		sql = "select gd_editorVersion from "
				+ EditorConfig.RES_DATA_TABLE_EDITOR
				+ " where resourceId=? and resourceType=?";

		try
		{
			pstat = conn.prepareStatement(sql);

			pstat.setString(1, id);
			pstat.setInt(2, this.getResourceType());
			rs = pstat.executeQuery();
			if (rs.next())
			{
				ver = rs.getString("gd_editorVersion");
			}
			rs.close();
			pstat.close();

		} catch (Exception e1)
		{
			e1.printStackTrace();
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
		return ver;
	}

	/**
	 * 检查数据版本
	 * 
	 * @return
	 */
	public boolean checkDataBuildVersion()
	{

		String lastEditorVersion = getLastEditorVersionFromDB();

		// 数据库中没有相关数据，可以提交
		if (lastEditorVersion == null)
		{
			return true;
		}

		// 当期编辑器版本>=数据编辑版本
		int rel = EditorConfig.VERSION_BUILD.compareTo(lastEditorVersion);
		// String ver = EditorConfig.VERSION_BUILD.substring(10, 16);
		// String time = EditorConfig.VERSION_BUILD.substring(24);
		//
		// System.out.println(ver+","+time);
		//
		// System.out.println("checkDataBuildVersion db:" + lastEditorVersion +
		// " edit:" + EditorConfig.VERSION_BUILD
		// + " " + lastEditorVersion.compareTo(EditorConfig.VERSION_BUILD) + " "
		// + rel);

		if (rel >= 0)
		{
			return true;
		}

		return false;
	}

	/**
	 * 获取数据库最后编辑时间
	 * 
	 * @return
	 */
	public Timestamp getLastEditorDateTimeFromDB()
	{

		Timestamp lastEditorDateTime = null;
		Connection conn = BaseConnectionPool.getConnection();
		if (conn == null)
		{
			return null;
		}

		String sql;
		PreparedStatement pstat;
		ResultSet rs;
		// sql = "select gd_dateTime from " + dataTable + " where PKId=?";
		sql = "select gd_dateTime from " + EditorConfig.RES_DATA_TABLE_EDITOR
				+ " where resourceId=? and resourceType=?";

		try
		{
			pstat = conn.prepareStatement(sql);

			pstat.setString(1, id);
			pstat.setInt(2, this.getResourceType());

			rs = pstat.executeQuery();
			if (rs.next())
			{
				lastEditorDateTime = rs.getTimestamp("gd_dateTime");
				// this.db_version=db_ver;
				// System.out.println("lastEditorDateTime="+lastEditorDateTime);
			}
			rs.close();
			pstat.close();

		} catch (Exception e1)
		{
			e1.printStackTrace();
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
		return lastEditorDateTime;

	}

	/**
	 * 是否版本冲突
	 * 
	 * @return
	 */
	public boolean isDBVersionConflict()
	{

		Timestamp nowDBEditorTime = getLastEditorDateTimeFromDB();

		// 数据库中没有相关数据
		if (nowDBEditorTime == null)
		{
			return false;
		}

		// 当期编辑日期 < 数据库中的编辑日期
		if (this.lastEditorDateTime.before(nowDBEditorTime))
		{
			return true;
		}

		return false;
	}

	// TODO 索引文件=================================================
	/**
	 * 从索引文件中初始化，要和getIndexString对应 如果特殊需要可重载
	 * 
	 * @param group
	 * @param ss
	 * @return
	 */
	public int initByIndexFile(DataGroup<?> group, String[] ss)
	{

		dataGroup = group;
		isNew = false;
		int num = 0;

		try
		{

			id = ss[num++];
			nameId = Integer.parseInt(ss[num++]);
			version = Integer.parseInt(ss[num++]);

			/*
			 * lastEditUserId=Integer.parseInt(ss[5]); lastEditUserName=ss[6];
			 * lastEditorDateTime=ss[7];
			 */
			fileName = dataGroup.groupPath + id + "." + dataGroup.fileSuffix;
			if (ss.length > num)
				dataType = Integer.parseInt(ss[num++]);
			if (ss.length > num)
				preload = Integer.parseInt(ss[num++]);
			if (ss.length > num)
				preloadOrder = Integer.parseInt(ss[num++]);
			if (ss.length > num)
				openFlag = Integer.parseInt(ss[num++]);

			if (ss.length > num)
				lastEditUserId = Integer.parseInt(ss[num++]);
			if (ss.length > num)
				lastEditUserName = ss[num++];
			if (ss.length > num)
			{
				try
				{
					lastEditorDateTime = Timestamp.valueOf(ss[num++]);
				} catch (Exception e)
				{
					lastEditorDateTime = new Timestamp(
							System.currentTimeMillis());
				}
			}
			if (ss.length > num)
				lastEditorVersion = ss[num++];

		} catch (Exception e)
		{
			System.out.println(group + "," + ss[0]);
			e.printStackTrace();
		}

		// if(ss.length>num)
		// packDataId=Integer.parseInt(ss[num++]);
		return num;
	}

	/**
	 * 获取索引描述存储使用，要和initByIndexFile对应 如果特殊需要可重载
	 */
	public String getIndexString()
	{

		StringBuffer s = new StringBuffer(id + "\t");

		// if(this.type.equals("text"))
		// s.append(((TextData)this).textData.text + "\t");
		// else
		// s.append(DataManager.getTextById(nameId) + "\t");
		//
		s.append(nameId + "\t");
		s.append(version + "\t").append(dataType + "\t").append(preload + "\t")
				.append(preloadOrder + "\t").append(openFlag);

		// 判断为新建数据
		if (lastEditorDateTime == null)
		{

			if (XEditor.user == null)
			{
				s.append("\t" + -1 + "\t").append("离线用户" + "\t")
						.append(new Timestamp(new Date().getTime()) + "\t")
						.append(EditorConfig.VERSION_BUILD).toString();
			} else
			{
				s.append("\t" + XEditor.user.id + "\t")
						.append(XEditor.user.name + "\t")
						.append(new Timestamp(new Date().getTime()) + "\t")
						.append(EditorConfig.VERSION_BUILD).toString();
			}

		} else
		{
			s.append("\t" + lastEditUserId + "\t")
					.append(lastEditUserName + "\t")
					.append(lastEditorDateTime + "\t")
					.append(lastEditorVersion).toString();
		}

		return s.toString();
	}

	// TODO:===============数据引用查询============================
	/**
	 * 对象引用列表
	 */
	private List<String> objectCiteList = new Vector<String>();

	/**
	 * 向对象引用列表中添加引用对象key
	 * 
	 * @param key
	 */
	public void addList(String key)
	{
		if (objectCiteList != null)
		{
			for (int i = 0; i < objectCiteList.size(); i++)
			{
				if (key.equals(objectCiteList.get(i)))
				{
					return;
				}
			}
			objectCiteList.add(key);
		}
	}

	/**
	 * 清除对象引用列表中的数据
	 */
	public void clearList()
	{
		objectCiteList = new Vector<String>();
	}

	/**
	 * 获得对象引用列表
	 * 
	 * @return
	 */
	public List<String> getReferenceDatas()
	{
		// System.out.println(dataGroup.groupName+"是："+getName());
		// for (int i = 0; i < objectCiteList.size(); i++) {
		// System.out.println("\t对象列表====>>>> "+objectCiteList.get(i));
		// }
		return objectCiteList;
	}

	public void initData()
	{
		loadData();
	}

	public void saveAllData()
	{
		saveData();
		saveClientData();
		saveServerData();
	}

}
