package com.doteplay.editor.tools.help;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.doteplay.editor.database.BaseConnectionPool;

/**
 * @author wyc 关于数据库操作的方法类
 */
public class HelpImportDBManager {

	public HelpImportDBManager() {
		
	}

	/**
	 * 插入数据
	 * 
	 * @return
	 * @throws SQLException
	 */
	public boolean insertXmlIntoDB(HelpData helpData) {
		boolean flag = false;
		HelpData cd = selectXmlById(helpData.getPKId());
		Statement state;
		if (cd != null) {
			flag = udpateXmlOfDb(helpData);
		}
		else {
			
			Connection conn = BaseConnectionPool.getConnection();
			
			try {
				conn.setAutoCommit(false);
				state = conn.createStatement();
				if (state.executeUpdate(helpData.toString()) > 0) {// 执行成功
					conn.commit();
					flag = true;
				}
				state.close();
			} catch (SQLException e) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} finally {
				try {
					conn.close();// 关闭连接
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	/**
	 * 删除
	 */
	public boolean deleteXmlFromDB(HelpData helpData) {
		boolean flag = false;
		return flag;
	}

	/**
	 * 修改
	 */
	public boolean udpateXmlOfDb(HelpData cellData) {
		boolean flag = false;
		String sql = "update t_game_help set name='" + cellData.getName() + "' , description='"
				+ cellData.getDescription() + "' , parentId=" + cellData.getParentId() + ",type=" + cellData.getType()
				+ "  where PKid=" + cellData.getPKId();
		Statement state;
		Connection conn = BaseConnectionPool.getConnection();
		
		try {
			conn.setAutoCommit(false);
			state = conn.createStatement();
			if (state.executeUpdate(sql) > 0) {// 执行成功
				conn.commit();
				flag = true;
			}
			state.close();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();// 关闭连接
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	/**
	 * 删除全部
	 */
	public boolean deleteAllXmlFromDB(String sql) {
		boolean flag = false;
		return flag;
	}

	/**
	 * 查询全部
	 */
	public List<HelpData> selectXmlFromDB() {
		List<HelpData> list = new ArrayList<HelpData>();
		return list;
	}

	/**
	 * 根据id查找对象
	 */
	public HelpData selectXmlById(int id) {
		HelpData helpData = null;
		String sql = "select * from t_game_help where PKId=" + id;
		Statement state;
		ResultSet res;
		Connection conn = BaseConnectionPool.getConnection();
		try {
			state = conn.createStatement();
			res = state.executeQuery(sql);
			if (res.next()) {
				helpData = new HelpData();
				helpData.setDescription(res.getString("description"));
				helpData.setName(res.getString("name"));
				helpData.setParentId(res.getInt("parentId"));
				helpData.setType(res.getInt("type"));
				helpData.setPKId(id);
			}
			res.close();
			state.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();// 关闭连接
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return helpData;
	}

}
