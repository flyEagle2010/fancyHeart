package com.doteyplay.core.resourcebundle.util.fieldset;

import com.doteyplay.core.resourcebundle.IMultipleLanguageSetting;
import com.doteyplay.core.resourcebundle.util.convert.Convert;



/**
 * Ã¶¾Ù¸³Öµ
 * @author 
 *
 */
public class EnumField{

	public void setField(IMultipleLanguageSetting c, String value) throws Exception{
		Object[] obj=Convert.convert(value);
		String[] result = new String[obj.length];
		for (int i = 0; i < obj.length; i++) {
			result[i] = (String) obj[i];
		}
		c.setting(result);
	}

}
