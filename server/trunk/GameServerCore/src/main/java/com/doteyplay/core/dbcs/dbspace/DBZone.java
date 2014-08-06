package com.doteyplay.core.dbcs.dbspace;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMap;
import com.ibatis.sqlmap.engine.mapping.result.ResultMap;
import com.ibatis.sqlmap.engine.mapping.statement.MappedStatement;
import com.ibatis.sqlmap.engine.mapping.statement.StatementType;
import com.doteyplay.core.dbcs.config.AbstractTagFileReader;
import com.doteyplay.core.dbcs.config.PackageItemInfo;
import com.doteyplay.core.dbcs.config.SqlMapXmlReader;
import com.doteyplay.core.util.xml.IParamterSupport;
import com.doteyplay.core.util.xml.ISimpleParamters;
import com.doteyplay.core.util.xml.XmlFileSupport;

/**
 * 一个数据库连接 持有Mapper信息
 * 
 * @author 
 * 
 */
public class DBZone implements IParamterSupport
{
	private static final Logger logger = Logger.getLogger(DBZone.class);

	private int zoneId;
	private String dataSourceName;
	private String dataSourceConfig;
	private String sqlMapTemplate;
	private List<String> mapperPackageList = new ArrayList<String>();
	private List<PackageItemInfo> mapperItems = new ArrayList<PackageItemInfo>();
	private SqlMapClient sqlMapClient;

	public void loadConfig(String sqlMapConfigFile)
	{
		XmlFileSupport.parseXmlFromResource(sqlMapConfigFile, this);
	}

	public void loadSqlMap() throws Exception
	{
		JAXPConfigurator.configure(Resources.getResourceAsReader(this.getDataSourceConfig()), false);

		AbstractTagFileReader xmlReader = new SqlMapXmlReader(this);
		String tmpXmlContent = xmlReader.getContent();
		ByteArrayInputStream tmpIn = new ByteArrayInputStream(tmpXmlContent.getBytes());
		// 配置数据源
		sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(tmpIn);
	}

	@Override
	public void putParamter(ISimpleParamters paramter)
	{
		if ("MAPPER".equals(paramter.getDataName()))
		{
			mapperPackageList.add(paramter.getValue("PACKAGE"));
		} else if ("DBZONE".equals(paramter.getDataName()))
		{
			zoneId = Integer.parseInt(paramter.getValue("ID"));
			dataSourceName = paramter.getValue("DATASOURCE");
			dataSourceConfig = paramter.getValue("DBCONFIG");
			sqlMapTemplate = paramter.getValue("SQLMAP");
		}
	}

	@Override
	public void onComplete()
	{
	}

	/**
	 * @return the dbId
	 */
	public int getZoneId()
	{
		return zoneId;
	}

	/**
	 * @return the dbSourceName
	 */
	public String getDataSourceName()
	{
		return dataSourceName;
	}

	/**
	 * @return the dbSourcePath
	 */
	public String getDataSourceConfig()
	{
		return dataSourceConfig;
	}

	/**
	 * @return the templatePath
	 */
	public String getSqlMapTemplate()
	{
		return sqlMapTemplate;
	}

	public void regMapperName(String pcakgename, String itemname)
	{
		this.mapperItems.add(new PackageItemInfo(pcakgename, itemname));
	}

	public int mapperCount()
	{
		return mapperItems.size();
	}

	public Collection<PackageItemInfo> getMapperItems()
	{
		return mapperItems;
	}

	public Collection<String> getMapperPackages()
	{
		return mapperPackageList;
	}

	public MappedStatement getMappedStatement(String statementid)
	{
		return ((SqlMapClientImpl) sqlMapClient).getMappedStatement(statementid);
	}

	public static Class<?> getMappedStatementParamClass(MappedStatement statement)
	{
		Class<?> pclass = null;
		ParameterMap tmpParameterMap = statement.getParameterMap();
		if (tmpParameterMap != null)
			pclass = tmpParameterMap.getParameterClass();
		if (pclass == null)
			pclass = statement.getParameterClass();
		return pclass;
	}

	public static Class<?> getMappedStatementResultClass(MappedStatement statement)
	{
		Class<?> pclass = null;
		ResultMap tmpResultMap = statement.getResultMap();
		if (tmpResultMap != null)
			pclass = tmpResultMap.getResultClass();
		return pclass;
	}

	public static boolean isSelectStatement(MappedStatement statement)
	{
		return (statement.getStatementType() == StatementType.SELECT);
	}

	public static boolean isUpdateStatement(MappedStatement statement)
	{
		return (statement.getStatementType() == StatementType.UPDATE);
	}

	public static boolean isDeleteStatement(MappedStatement statement)
	{
		return (statement.getStatementType() == StatementType.DELETE);
	}

	public static boolean isInsertStatement(MappedStatement statement)
	{
		return (statement.getStatementType() == StatementType.INSERT);
	}

	public static boolean isProcedureStatement(MappedStatement statement)
	{
		return (statement.getStatementType() == StatementType.PROCEDURE);
	}

	public static boolean isUnknownStatement(MappedStatement statement)
	{
		return (statement.getStatementType() == StatementType.UNKNOWN);
	}

	public Object queryForObject(String statementId, Object param) throws SQLException
	{
		return sqlMapClient.queryForObject(statementId, param);
	}

	public Object queryForObject(String statementId) throws SQLException
	{
		return sqlMapClient.queryForObject(statementId);
	}
	
	public Object queryForList(String statementId, Object param) throws SQLException
	{
		return sqlMapClient.queryForList(statementId, param);
	}

	public Object queryForList(String statementId) throws SQLException
	{
		return sqlMapClient.queryForList(statementId);
	}

	public Object insert(String statementId, Object param) throws SQLException
	{
		return sqlMapClient.insert(statementId, param);
	}

	public Object insert(String statementId) throws SQLException
	{
		return sqlMapClient.insert(statementId);
	}

	public Object update(String statementId, Object param) throws SQLException
	{
		return sqlMapClient.update(statementId, param);
	}

	public Object update(String statementId) throws SQLException
	{
		return sqlMapClient.update(statementId);
	}

	public Object delete(String statementId, Object param) throws SQLException
	{
		return sqlMapClient.delete(statementId, param);
	}

	public Object delete(String statementId) throws SQLException
	{
		return sqlMapClient.delete(statementId);
	}
	
	public void startTransaction() throws SQLException
	{
		sqlMapClient.startTransaction();	
	}
	
	public void commitTransaction() throws SQLException
	{
		sqlMapClient.commitTransaction();
	}
	
	public void endTransaction() throws SQLException
	{
		sqlMapClient.endTransaction();
	}
}
