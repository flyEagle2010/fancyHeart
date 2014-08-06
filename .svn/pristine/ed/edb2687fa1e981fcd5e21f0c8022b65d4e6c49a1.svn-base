package com.doteyplay.core.bhns;

import java.io.Serializable;
import java.util.Iterator;

public interface IServiceData
{
	public Serializable getData(String datatag);

	public void putData(String datatag, Serializable data);

	public void removeData(String datatag);

	public void clearData();

	public Serializable getData(int datagroup, String datakey);

	public void putData(int datagroup, String datakey, Serializable data);

	public void removeData(int datagroup, String datakey);

	public void clearData(int datagroup);

	public void clearAllData();

	public boolean isDataExist(String datatag);

	public boolean isDataExist(int datagroup);

	public Iterator<Serializable> getDataIterator();

	public Iterator<Serializable> getDataIterator(int datagroup);
	
	@Deprecated
	public boolean transfeDataToCluster();
}
