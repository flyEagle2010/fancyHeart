package com.doteyplay.game.domain.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.doteyplay.game.CommonConstants;
import com.doteyplay.game.config.template.NameDataTemplate;
import com.doteyplay.game.util.excel.TemplateService;

public class NameGenerator
{
	private Random random = new Random();
	private List<String> name1List = new ArrayList<String>();
	private List<String> name2List = new ArrayList<String>();
	private List<String> name3List = new ArrayList<String>();
	
	private NameGenerator(){}
	
	public void init()
	{
		for(NameDataTemplate template:TemplateService.getInstance().getAll(NameDataTemplate.class).values())
		{
			if(template.getName1() != null && !"".equals(template.getName1()))
				name1List.add(template.getName1());
			
			if(template.getName2() != null && !"".equals(template.getName2()))
				name2List.add(template.getName2());
			
			if(template.getName3() != null && !"".equals(template.getName3()))
				name3List.add(template.getName3());
		}
	}
	
	
	public List<String> randomNameList()
	{
		List<String> list = new ArrayList<String>();
		
		while(list.size() < CommonConstants.NAME_RANDOM_NUM)
		{
			String name1 = name1List.get(random.nextInt(name1List.size()));
			String name2 = name2List.get(random.nextInt(name2List.size()));
			String name3 = name3List.get(random.nextInt(name3List.size()));
			
			//FIXME:需要添加全服用户名排重检查
			list.add(name1 + name2 + name3);
		}
		return list;
	}
	
	
	//******************************************************
	private final static NameGenerator instance = new NameGenerator();
	public final static NameGenerator getInstance()
	{
		return instance;
	}
}
