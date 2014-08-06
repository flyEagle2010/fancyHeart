package com.doteyplay.game.service.bo.virtualworld.chat;

import com.doteyplay.game.constants.chat.ChatConstant;
import com.doteyplay.game.service.bo.virtualworld.chat.handle.EnergyHandlerImpl;
import com.doteyplay.game.service.bo.virtualworld.chat.handle.ExpHandlerImpl;
import com.doteyplay.game.service.bo.virtualworld.chat.handle.GoodsHandlerImpl;
import com.doteyplay.game.service.bo.virtualworld.chat.handle.IChatHandler;
import com.doteyplay.game.service.bo.virtualworld.chat.handle.MoneyHandlerImpl;

/**
 * @className:GMProcessFactory.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014年7月10日 上午11:34:22
 */
public class ChatHanderFactory {

	private static ChatHanderFactory instance = new ChatHanderFactory();

	private ChatHanderFactory() {
	}

	public static ChatHanderFactory getInstance() {
		return instance;
	}

	public IChatHandler<?> getGMProcess(ChatConstant chatConstant) {

		switch (chatConstant) {
		case GOODS_SUFFIX:
			return new GoodsHandlerImpl();
		case MONEY_SUFFIX:
			return new MoneyHandlerImpl();
		case EXP_SUFFIX:
			return new ExpHandlerImpl();
		case ENERGY_SUFFIX:
			return new EnergyHandlerImpl();
		default:
			break;
		}
		return null;
	}

}
