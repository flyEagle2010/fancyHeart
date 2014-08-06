package com.doteyplay.game.util;

import java.util.Date;


public class DateUtil {
	
	
	public static String getAlertTime(int alertTime){
		StringBuffer alertContent = new StringBuffer();
		if(alertTime > 60){
			return alertContent.append((alertTime+1)/60).append("分钟").toString();
		}else{
			return alertContent.append(alertTime).append("秒").toString();
		}
	}
	/**
	 * 获取时间当前时间-date
	 * @param date
	 * @return
	 */
	public static int getTimeForMinute(Date date){
		if(date != null){
			long time =System.currentTimeMillis() - date.getTime(); 
			return (int) (time/(1000*60));
		}
		return 0;
	}
	
	/**
	 * 获取时间显示单位
	 * @param time 秒
	 * @return
	 */
	public static String getTimeUnit(int time){
		int temp = 0;
		int hour = 0;
		int minute = 0;
		int second = 0;
		if(time > 86400){
			temp = time%86400;
			hour = temp/3600;
			return (time/86400)+"天";
		}else if(time > 3600){
			temp = time%3600;
			minute = temp/60;
			return (time/3600)+"小时";
		}else if(time > 60){
			second = time%60;
			
			return (time/60)+"分钟";
		}else{
			return time+"秒";
		}
	}
	
	public static void main(String[] args) {
//		Calendar calendar = Calendar.getInstance();
//		System.out.println("天: " + calendar.get(Calendar.DAY_OF_WEEK));
	}
	
		
}
