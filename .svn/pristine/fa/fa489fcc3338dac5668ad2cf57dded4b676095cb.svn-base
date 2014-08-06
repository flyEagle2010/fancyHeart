package com.doteyplay.game.util.excel;

import java.net.URL;
import java.util.Map;

public interface ITemplateService
{

	public abstract void init(URL url);

	public abstract <T extends TemplateObject> T get(int i, Class<T> class1);

	public abstract <T extends TemplateObject> void add(T t);

	public abstract <T extends TemplateObject> Map<Integer, T> getAll(
			Class<T> class1);

	public abstract <T extends TemplateObject> Map<Integer, T> removeAll(
			Class<T> class1);

	public abstract <T extends TemplateObject> boolean isTemplateExist(int i,
			Class<T> class1);
}
