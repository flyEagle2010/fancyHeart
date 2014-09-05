package com.doteyplay.game.service.bo.virtualworld.chat.resolve;

import com.doteyplay.game.constants.chat.ChatConstant;
import com.doteyplay.game.constants.chat.ChatUtils;
import com.doteyplay.game.service.bo.virtualworld.chat.patameter.GoodsPatameterObject;
import com.doteyplay.game.service.bo.virtualworld.chat.patameter.PetPatameterObject;

/**
 * @className:GoodsProcessImpl.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014年7月10日 下午2:02:32
 */
public class PetDataTypeResolve implements IChatResolve<PetPatameterObject>{

	private ChatConstant constant;
	
	public PetDataTypeResolve(ChatConstant constant) {
		super();
		this.constant = constant;
	}

	@Override
	public PetPatameterObject handlePatameter(String source) {
		// TODO Auto-generated method stub
		String data = ChatUtils.doGmPre(source, constant.getSuffix());
		String[] split = data.split("\\]\\[");
		if(split.length != 2){
			throw new RuntimeException("gm操作的格式不对,暂不支持");
		}
		//解析roleId
		String tempRoleIdStr=split[0];
		String roleIdStr = tempRoleIdStr.substring(1, tempRoleIdStr.length());
		String[] roleIds = roleIdStr.split(",");
		
		//解析物品Id.
		String tempGoodsStr = split[1];
		String goodsStr = tempGoodsStr.substring(0, tempGoodsStr.length()-1);
		String[] goodsString = goodsStr.split(",");
		
		PetPatameterObject obj = new PetPatameterObject();
		obj.addRoles(roleIds);
		obj.addGoods(goodsString);
		return obj;
	}

	
}
