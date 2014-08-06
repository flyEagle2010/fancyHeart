package com.doteyplay.game.constants.tollgate;

/**
 * @className:TollgateRewardExp.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014年7月8日 下午5:06:16
 */
public class TollgateRewardExp {

	/**
	 * 返回需要消耗的体力点.
	 * @param tollgateShowType
	 * @return
	 */
	public static int getCostEnergyPoint(int tollgateShowType) {
		int costEnergyPoint = 6;
		switch (tollgateShowType) {
		case 0:
			costEnergyPoint = 6;
			break;
		case 1:
			costEnergyPoint = 12;
			break;
		case 2:
			costEnergyPoint = 6;
			break;

		default:
			break;
		}
		return costEnergyPoint;
	}
	
	/**
	 * 公式还没有写.暂时只乘于100
	 * @param costEnergyPoint
	 * @return
	 */
	public static int rewardRoleExpByEnergyPoint(int costEnergyPoint){
		
		return costEnergyPoint*100;
	}
}
