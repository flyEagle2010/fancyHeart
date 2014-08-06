package com.doteyplay.core.resourcebundle.util.fieldset;

import java.lang.reflect.Field;

import com.doteyplay.core.resourcebundle.util.convert.Convert;
import com.doteyplay.core.resourcebundle.util.convert.ConvertException;

/**
 * 字符串数组赋值
 * 
 * @author 
 * 
 */
public class StringArrayField implements IField {

	@Override
	public void setField(Field field, String value) throws Exception {
		parseStringArray(field, Convert.convert(value));
	}

	private void parseStringArray(Field field, Object[] objArray)
			throws Exception {
		int level = 1;
		Object obj = objArray[0];
		while (obj.getClass().isArray()) {
			level++;
			obj = ((Object[]) obj)[0];
		}

		if (level == 1) {
			setOneDimensionalArray(field, objArray);
		} else if (level == 2) {
			setTwoDimensionalArray(field, objArray);
		} else if (level == 3) {
			setThreeDimensionalArray(field, objArray);
		} else
			throw new ConvertException("不支持的数组长度" + field.getName() + "["
					+ level + "]");
	}

	private void setOneDimensionalArray(Field field, Object[] obj)
			throws Exception {
		/*
		String[] result = new String[obj.length];
		for (int i = 0; i < obj.length; i++) {
			result[i] = (String) obj[i];
		}
		field.set(null, result);
		*/
		String[] fieldArray = (String[]) field.get(null);
		for (int i = 0; i < obj.length; i ++) {
			if (i >= fieldArray.length) {
				break;
			}
			fieldArray[i] = (String) obj[i];
		}
	}

	private void setTwoDimensionalArray(Field field, Object[] obj)
			throws Exception {
		/*
		String[][] result = new String[obj.length][];
		for (int i = 0; i < obj.length; i++) {
			result[i] = new String[((Object[]) obj[i]).length];
			for (int j = 0; j < result[i].length; j++) {
				result[i][j] = (String) ((Object[]) obj[i])[j];
			}
		}
		field.set(null, result);
		*/
		String[][] fieldArray = (String[][]) field.get(null);
		for (int i = 0; i < obj.length; i ++) {
			if (i >= fieldArray.length) {
				break;
			}
			fieldArray[i] = new String[((Object[]) obj[i]).length];
			for (int j = 0; j < fieldArray[i].length; j++) {
				fieldArray[i][j] = (String) ((Object[]) obj[i])[j];
			}
		}
	}

	private void setThreeDimensionalArray(Field field, Object[] obj)
			throws Exception {
		/*
		String[][][] result = new String[obj.length][][];
		for (int i = 0; i < obj.length; i++) {
			result[i] = new String[((Object[]) obj[i]).length][];
			for (int j = 0; j < result[i].length; j++) {
				result[i][j] = new String[((Object[]) ((Object[]) obj[i])[j]).length];
				for (int k = 0; k < result[i][j].length; k++) {
					result[i][j][k] = (String) (((Object[]) ((Object[]) obj[i])[j]))[k];
				}
			}
		}
		field.set(null, result);
		*/
		String[][][] fieldArray = (String[][][]) field.get(null);
		for (int i = 0; i < obj.length; i++) {
			fieldArray[i] = new String[((Object[]) obj[i]).length][];
			for (int j = 0; j < fieldArray[i].length; j++) {
				fieldArray[i][j] = new String[((Object[]) ((Object[]) obj[i])[j]).length];
				for (int k = 0; k < fieldArray[i][j].length; k++) {
					fieldArray[i][j][k] = (String) (((Object[]) ((Object[]) obj[i])[j]))[k];
				}
			}
		}
	}
}
