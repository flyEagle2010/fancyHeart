package com.doteyplay.core.resourcebundle.util.fieldset;

import java.lang.reflect.Field;

/**
 * 
 * @author 
 *
 */
public class FieldUtil {

	public static boolean isString(Field field) {
		return field.getType().getSimpleName().equals("String");
	}

	public static boolean isStringArray(Field field) {
		return field.getType().getSimpleName().startsWith("String[");
	}
}
