package com.doteplay.editor.datahelper;

public class MetaDataField
{
	private int index;
	private String name;
	private String description;
	private int dataType;

	public MetaDataField(String fdname, String fdesc, int datatype, int dindex)
	{
		this.name = fdname;
		this.description = (fdesc != null) ? fdesc : fdname;
		this.dataType = datatype;
		this.index = dindex;
	}

	public int getIndex()
	{
		return index;
	}

	public String getName()
	{
		return name;
	}

    public String getDescription() {
        return description;
    }

    public int getDataType() {
        return dataType;
    }
}
