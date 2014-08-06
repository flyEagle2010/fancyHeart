package com.doteyplay.game.service.bo.virtualworld.chat.patameter;

import java.util.ArrayList;
import java.util.List;

import com.doteyplay.game.config.template.ItemDataObject;
import com.doteyplay.game.util.excel.TemplateService;

/**
 * @className:GoodsPatameterObject.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014年7月10日 下午2:03:47
 */
public class GoodsPatameterObject extends AbstractPatameterObject{
	
	private List<ItemObject> itemObjects;

	public List<ItemObject> getItemObjects() {
		return itemObjects;
	}

	public void setItemObjects(List<ItemObject> itemObjects) {
		this.itemObjects = itemObjects;
	}

	public void addGoods(String[] goodsString){
		if(itemObjects ==null){
			itemObjects = new ArrayList<ItemObject>();
		}
		for (String tempGoodsDesc : goodsString) {
			
			String[] goodsDesc = splitGoodsStr(tempGoodsDesc);
			
			int goodsId=Integer.parseInt(goodsDesc[0]);
			int goodsNum = Integer.parseInt(goodsDesc[1]);
			boolean isOk=checkGoodsIdIsExist(goodsId);
			if(!isOk){
				throw new RuntimeException("数据输入错误!");
			}
			ItemObject obj = new ItemObject();
			obj.itemId = goodsId;
			obj.itemNum = goodsNum;
			itemObjects.add(obj);
			
		}
	}

	private String[] splitGoodsStr(String tempGoodsDesc) {
		String goodsDesc[]=null;
		if(tempGoodsDesc.indexOf("x")>-1){
			goodsDesc = tempGoodsDesc.split("x");
		}else if(tempGoodsDesc.indexOf("X")>-1){
			goodsDesc = tempGoodsDesc.split("X");
		}else{
			throw new RuntimeException("数据输入错误!");
		}	
		
		if(goodsDesc.length!=2){
			throw new RuntimeException("数据输入错误!");
		}
		return goodsDesc;
	}
	
	private boolean checkGoodsIdIsExist(int goodsId){
		ItemDataObject itemDataObject = TemplateService.getInstance().get(goodsId, ItemDataObject.class);
		return itemDataObject!=null;
	}

	public class ItemObject{
		public int itemId;
		
		public int itemNum;

	} 
}
