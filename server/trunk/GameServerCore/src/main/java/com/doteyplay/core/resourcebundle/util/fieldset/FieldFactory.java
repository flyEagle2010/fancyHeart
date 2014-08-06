package com.doteyplay.core.resourcebundle.util.fieldset;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;

/**
 * field type factory
 * 
 * @author 
 * 
 */
public class FieldFactory {
	private static final Logger logger = Logger.getLogger(FieldFactory.class);

	public static void setFieldValue(Field field, String value)
			throws Exception {
		if (FieldUtil.isString(field)) { // 字符串的情况下
			new StringField().setField(field, value);
		} else if (FieldUtil.isStringArray(field)) { // 字符数组的情况下
			new StringArrayField().setField(field, value);
		} else {
			logger.error("不支持的格式" + field.getName() + "["
					+ field.getType().getSimpleName() + "]");
		}
	}
}
