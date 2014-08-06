package com.doteyplay.task.schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.doteyplay.task.schedule.base.ScheduleTaskType;
import com.doteyplay.utils.PackageScaner;
import com.doteyplay.utils.StrUtils;

/**
 * �ƻ��������ע��
 * 
 * @author chgyan
 * 
 */
public class ScheduleRegistry
{
	private static class ItemBean implements Comparable<ItemBean>
	{

		private String className;
		private int order;

		public ItemBean(String className, int order)
		{
			this.className = className;
			this.order = order;
		}

		public String getClassName()
		{
			return className;
		}

		public int getOrder()
		{
			return order;
		}

		public void setOrder(int order)
		{
			this.order = order;
		}

		@Override
		public int compareTo(ItemBean o)
		{
			return order - o.getOrder();
		}

	}

	private static final String CONFIG_FILE = "conf/task-handler-config.xml";

	private List<ScheduleInfo> taskInfo = new ArrayList<ScheduleInfo>();

	private ScheduleRegistry()
	{
		loadConfig();
	}

	// ��ȡ�����ļ�����
	private void loadConfig()
	{
		SAXReader saxReader = new SAXReader();
		try
		{
			Document doc = saxReader.read(ScheduleRegistry.class.getClassLoader().getResourceAsStream(
					CONFIG_FILE));
			List<Node> list = doc.selectNodes("//task");
			if (list != null)
			{
				for (Node taskNode : list)
				{
					ScheduleInfo info = new ScheduleInfo();
					processTask(info, taskNode);
					taskInfo.add(info);
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void processTask(ScheduleInfo info, Node task) throws Exception
	{
		Element elm = (Element) task.selectSingleNode("handler");
		// �趨����Ͳ�����
		if (elm != null)
		{
			info.setClassName(elm.attributeValue("class"));
			info.setParameterClass(elm.attributeValue("parameter"));
		}

		// ����ʱ��
		elm = (Element) task.selectSingleNode("time");
		if (elm != null)
		{
			String time = elm.attributeValue("cron-expression");
			parseTime(info, time);
			info.setSchedulePrecision(StrUtils.parseint(elm.attributeValue("precision"), 0));
		}

		// ���ü�����
		Node node = task.selectSingleNode("items");
		if (node != null)
		{
			info.setItemClassName(parseTaskItem(node));
		}
	}

	private void parseTime(ScheduleInfo info, String time) throws Exception
	{
		CronExpression e = new CronExpression(time);
		int minute = e.getMinute();
		int hour = e.getHour();
		int dayOfMonth = e.getDayOfMonth();
		int dayOfWeek = e.getDayOfWeek();
		int month = e.getMonth();

		// ��ʱ��֧��ÿСʱ�������ȡ�����ķ�ʽҪ�����Ҫ��ʱ����д��.Ӧ����*/*��д����������.
		ScheduleTaskType type;
		if (month != -1)
		{
			type = ScheduleTaskType.YEARLY;
		}
		else if (dayOfMonth != -1)
		{
			type = ScheduleTaskType.MONTHLY;
		}
		else if (dayOfWeek != -1)
		{
			type = ScheduleTaskType.WEEKLY;
		}
		else
		{
			type = ScheduleTaskType.DAILY;
		}

		info.setMinute(minute);
		info.setHour(hour);
		info.setDate(dayOfMonth);
		info.setMonth(month);
		info.setWeek(dayOfWeek);
		info.setTaskType(type.ordinal());
	}

	private String[] parseTaskItem(Node items)
	{

		Map<String, ItemBean> taskItemMap = new HashMap<String, ItemBean>();
		String ext = ".class";
		// �ȼ��Package
		List<Node> packages = items.selectNodes("package");
		if (packages != null)
		{
			for (Node p : packages)
			{

				String value = ((Element) p).attributeValue("value");
				// ������������
				List<Node> exclude = p.selectNodes("exclude");
				Map<String, String> excludeClass = new HashMap<String, String>();
				if (exclude != null)
				{
					String ex;
					for (Node e : exclude)
					{
						ex = ((Element) e).attributeValue("class");
						excludeClass.put(ex, ex);
					}
				}

				// ����package�µ��ļ�[��֧����]

				String[] include = PackageScaner.scanNamespaceFiles(value, ext);
				if (include != null && include.length > 0)
				{
					ItemBean bean;
					String key;
					for (String includeClass : include)
					{
						key = value + "."
								+ includeClass.subSequence(0, includeClass.length() - (ext.length()));
						if (!excludeClass.containsKey(key))
						{
							bean = new ItemBean(key, 0); // 0��Ϊ��Ĭ�ϣ�������
							taskItemMap.put(key, bean);
						}
					}
				}
			}
		}

		// ���������������
		List<Node> include = items.selectNodes("include");
		if (include != null)
		{
			String key;
			Element e;
			ItemBean bean;
			for (Node node : include)
			{
				e = (Element) node;
				key = e.attributeValue("class");
				int order = StrUtils.parseint(e.attributeValue("order"), 0);
				if (taskItemMap.containsKey(key))
				{
					bean = taskItemMap.get(key);
					bean.setOrder(order);
				}
				else
				{
					bean = new ItemBean(key, order);
					taskItemMap.put(key, bean);
				}
			}
		}

		// ����
		ItemBean[] array = new ItemBean[taskItemMap.size()];
		taskItemMap.values().toArray(array);
		Arrays.sort(array);

		String[] result = new String[array.length];
		for (int i = 0; i < result.length; i++)
		{
			result[i] = array[i].getClassName();
		}

		return result;
	}

	public List<ScheduleInfo> getTaskInfo()
	{
		return taskInfo;
	}

	private static ScheduleRegistry _instance = new ScheduleRegistry();

	public static ScheduleRegistry getInstance()
	{
		return _instance;
	}

	public static void main(String[] args)
	{
		ScheduleRegistry.getInstance();
	}

}
