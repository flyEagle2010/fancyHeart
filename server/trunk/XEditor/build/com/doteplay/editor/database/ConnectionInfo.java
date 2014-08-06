package com.doteplay.editor.database;

import java.sql.Connection;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

import com.doteplay.editor.util.xml.simpleXML.SimpleAttributes;
import com.doteplay.editor.util.xml.simpleXML.StrUtils;

public class ConnectionInfo
{

	public int id;
	public String title;
	public boolean publisher = false;

	private String _driver;
	private String _url;
	private int _maxActive;
	private int _maxWait;
	private String _username;
	private String _password;

	public boolean valid;

	public ConnectionInfo(SimpleAttributes attributes)
	{
		loadConfig(attributes);
	}

	public void loadConfig(SimpleAttributes attributes)
	{
		valid = false;
		if (attributes != null)
		{
			id = Integer.parseInt(attributes.getValue("id"));
			title = attributes.getValue("title");
			String publisherStr = attributes.getValue("publisher");
			if (publisherStr != null)
				publisher = publisherStr.equals("1");
			_driver = attributes.getValue("driver");
			_url = attributes.getValue("url");
			_maxActive = StrUtils.parseint(attributes.getValue("maxactive"), 4);
			_maxWait = StrUtils.parseint(attributes.getValue("maxwait"), 4);
			_username = attributes.getValue("username");
			_password = attributes.getValue("password");
			valid = (title != null && _driver != null && _url != null
					&& _username != null && _password != null);
		}
	}

	public Properties getDataSourceProperties()
	{
		Properties dbProps = new Properties();
		dbProps.setProperty("driver", _driver);
		dbProps.setProperty("url", _url);
		dbProps.setProperty("username", _username);
		dbProps.setProperty("password", _password);
		dbProps.setProperty("autoCommit", "true");
		return dbProps;
	}

	public BasicDataSource initDataSource()
	{
		BasicDataSource dataSource = null;
		try
		{
			dataSource = new BasicDataSource();
			dataSource.setDriverClassName(_driver);
			dataSource.setUrl(_url); // "jdbc:apache:commons:testdriver"
			dataSource.setMaxActive(_maxActive);
			dataSource.setMaxWait(_maxWait);
			dataSource.setDefaultAutoCommit(true);
			dataSource.setDefaultReadOnly(false);
			dataSource
					.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			dataSource.setUsername(_username);
			dataSource.setPassword(_password);
		} catch (Exception e)
		{
			dataSource = null;
		}
		return dataSource;
	}

	@Override
	public String toString()
	{
		return id + " " + title + " " + publisher;
	}
}
