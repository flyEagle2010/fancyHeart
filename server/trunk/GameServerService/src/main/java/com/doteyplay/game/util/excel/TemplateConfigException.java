package com.doteyplay.game.util.excel;

public class TemplateConfigException extends ConfigException
{

	private static final long serialVersionUID = 1L;

	public TemplateConfigException(String sheetName, int templateId,
			String errorInfo)
	{
		super(String.format("[%s][id=%d]%s",
				new Object[] { sheetName, Integer.valueOf(templateId),
						errorInfo }));
	}

	public TemplateConfigException(String sheetName, int templateId,
			String errorInfo, Exception e)
	{
		super(String.format("[%s][id=%d]%s",
				new Object[] { sheetName, Integer.valueOf(templateId),
						errorInfo }), e);
	}

	public TemplateConfigException(String sheetName, int templateId,
			int colNum, String errorInfo)
	{
		super(String.format("[%s][id=%d][%s¡–]%s", new Object[] { sheetName,
				Integer.valueOf(templateId), getColName(colNum), errorInfo }));
	}

	private static String getColName(int colNum)
	{
		String str = "";
		int first = colNum / 26;
		int sec = colNum % 26;
		if (sec == 0)
		{
			first--;
			str = first != 0 ? (new StringBuilder()).append("")
					.append((char) ((65 + first) - 1)).toString() : "";
			str = (new StringBuilder()).append(str).append("Z").toString();
			return str;
		} else
		{
			str = first != 0 ? (new StringBuilder()).append("")
					.append((char) ((65 + first) - 1)).toString() : "";
			str = (new StringBuilder()).append(str)
					.append((char) ((65 + sec) - 1)).toString();
			return str;
		}
	}
}
