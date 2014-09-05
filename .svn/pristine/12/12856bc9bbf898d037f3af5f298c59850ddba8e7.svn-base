package com.doteyplay.game.service.bo.virtualworld.chat.patameter;

import com.doteyplay.game.config.template.SpriteDataObject;
import com.doteyplay.game.util.excel.TemplateService;

/**
 * @className:GoodsPatameterObject.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014年7月10日 下午2:03:47
 */
public class PetPatameterObject extends GoodsPatameterObject {
	@Override
	protected boolean checkGoodsIdIsExist(int petId) {
		SpriteDataObject itemDataObject = TemplateService.getInstance().get(
				petId, SpriteDataObject.class);
		return itemDataObject != null;
	}

}
