package com.doteyplay.game.domain.outfit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.doteyplay.core.dbcs.DBCS;
import com.doteyplay.game.constants.item.OutfitInstallResult;
import com.doteyplay.game.domain.gamebean.OutfitBean;
import com.doteyplay.game.domain.item.RoleItem;
import com.doteyplay.game.domain.sprite.AbstractSprite;
import com.doteyplay.game.message.common.CommonItemUpdateMessage;
import com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog;
import com.doteyplay.game.persistence.serverdata.outfit.IOutfitBeanDao;

public class Outfit
{
	private Map<Integer,RoleItem> itemMap = new HashMap<Integer, RoleItem>();
	
	private OutfitBean outfitBean;
	private AbstractSprite sprite;
	private long roleId;

	public Outfit(long roleId, AbstractSprite sprite,OutfitBean outfitBean)
	{
		this.outfitBean = outfitBean;
		if(this.outfitBean == null)
		{
			this.outfitBean = new OutfitBean();
			this.outfitBean.setRoleId(roleId);
			this.outfitBean.setPetId(sprite.getId());
			
			IOutfitBeanDao dao = DBCS.getExector(IOutfitBeanDao.class);
			dao.insertOutfitBean(this.outfitBean);
		}
		else
		{
			if(this.outfitBean.getOutfitData() != null)
			{
				try
				{
					DataInputStream in = new DataInputStream(new ByteArrayInputStream(
							this.outfitBean.getOutfitData()));
					int size = in.readByte();
					for(int i = 0 ; i < size; i ++)
					{
						int itemId = in.readInt();
						RoleItem item = RoleItem.createRoleItem(roleId, itemId, 1);
						item.setPetId(this.outfitBean.getPetId());
						itemMap.put(itemId, item);
					}
					in.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}	
			}
		}
		
		this.sprite = sprite;
		this.roleId = roleId;
	}
	
	public void saveDB()
	{
		try
		{
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(bout);
			
			if(itemMap != null)
			{
				out.writeByte(itemMap.size());
				for(Integer itemId:itemMap.keySet())
					out.writeInt(itemId);
			}
			out.flush();
			out.close();	
			
			outfitBean.setOutfitData(bout.toByteArray());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		IOutfitBeanDao outfitBeanDao = DBCS.getExector(IOutfitBeanDao.class);
		outfitBeanDao.updateOutfitBean(outfitBean);
		
	}

	public OutfitInstallResult installOutfit(RoleItem item)
	{
		item = RoleItem.closeRoleItem(item.getBean());
		item.getBean().setItemNum(1);
		item.setPetId(this.sprite.getId());
		
		itemMap.put(item.getBean().getItemId(), item);

		PItemChangeLog.Builder builder = PItemChangeLog.newBuilder();
		builder.setItemAddNum(1);
		builder.setItemFinalNum(1);
		builder.setItemId(item.getBean().getItemId());
		builder.setNpcId(this.sprite.getId());
		List<PItemChangeLog.Builder> itemList = new ArrayList<PItemChangeLog.Builder>();
		itemList.add(builder);
		CommonItemUpdateMessage message = new CommonItemUpdateMessage(itemList);
		this.sprite.getTopOwner().sendMsg(message);
		
		this.saveDB();
		return OutfitInstallResult.SUCCESS;
	}

	public OutfitInstallResult installOutfitCheck(RoleItem item)
	{
		if (getQualityItemIdList().indexOf(item.getBean().getItemId()) <= -1)
			return OutfitInstallResult.ITEM_NOT_FIT;
		
		if (itemMap.get(item.getBean().getItemId()) != null)
			return OutfitInstallResult.ITEM_HAS_EXIST;
		return OutfitInstallResult.SUCCESS; 
	}
	public void removeAll()
	{
		List<PItemChangeLog.Builder> itemList = new ArrayList<PItemChangeLog.Builder>();
		for(RoleItem item:itemMap.values())
		{
			PItemChangeLog.Builder builder = PItemChangeLog.newBuilder();
			builder.setItemAddNum(-1);
			builder.setItemFinalNum(0);
			builder.setNpcId(this.sprite.getId());
			builder.setItemId(item.getBean().getItemId());
			itemList.add(builder);	
		}
		
		CommonItemUpdateMessage message = new CommonItemUpdateMessage(itemList);
		this.sprite.getTopOwner().sendMsg(message);
		
		itemMap.clear();
		this.saveDB();
	}

	public AbstractSprite getSprite()
	{
		return sprite;
	}

	public void setSprite(AbstractSprite sprite)
	{
		this.sprite = sprite;
	}

	public Map<Integer, RoleItem> getOutfitMap()
	{
		return itemMap;
	}

	public void setOutfitMap(Map<Integer, RoleItem> outfitMap)
	{
		this.itemMap = outfitMap;
	}

	public long getRoleId()
	{
		return roleId;
	}

	public void setRoleId(long roleId)
	{
		this.roleId = roleId;
	}

	public List<Integer> getQualityItemIdList()
	{
		if(sprite == null)
			return null;
		
		return sprite.getSpriteDataObject().getPropDataList()
				.get(sprite.getSpriteBean().getQuality()).getCurQualityItemList();
	}
	
	public boolean isOutfitFull()
	{
		for(Integer itemId:getQualityItemIdList())
		{
			if(itemMap.get(itemId) == null)
				return false;
		}
		return true;
	}
}
