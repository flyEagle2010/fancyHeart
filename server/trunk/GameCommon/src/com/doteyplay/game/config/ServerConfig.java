package com.doteyplay.game.config;

import java.lang.reflect.Field;

import com.doteyplay.game.FieldAnnotation;

/**
 * 服务器相关配置信息，从server-config.xml文件读取的内容
 * 
 */
public class ServerConfig
{

	@FieldAnnotation(name = "游戏名称")
	public final static String SERVER_NAME = "rpgGame";

	@FieldAnnotation(name = "服务编号")
	public static int SERVICE_ID = 0;

	@FieldAnnotation(name = "区域编号")
	public static int SERVER_AREA_ID = 1;

	@FieldAnnotation(name = "分区名称")
	public static String SERVER_AREA_NAME = "";

	@FieldAnnotation(name = "服务器端口")
	public static int SERVER_PORT = 2888;

	@FieldAnnotation(name = "GM端口")
	public static int GM_SERVER_PORT = 9;

	@FieldAnnotation(name = "计费服务器地址")
	public static String FEE_SERVER_IP = "";

	@FieldAnnotation(name = "计费服务器端口")
	public static int FEE_SERVER_PORT = 9;
	
	@FieldAnnotation(name = "认证服务器地址")
	public static String AUTH_SERVER_IP = "";

	@FieldAnnotation(name = "认证服务器端口")
	public static int AUTH_SERVER_PORT = 9;

	@FieldAnnotation(name = "日志服务器地址")
	public static String LOG_SERVER_IP = "";

	@FieldAnnotation(name = "日志服务器端口")
	public static int LOG_SERVER_PORT = 9;

	@FieldAnnotation(name = "服务器最高承载人数上限")
	public static int MAX_PLAYERS = 2000;

	@FieldAnnotation(name = "ResourceBundle文件前缀")
	public static String RESOURCE_BUNDLE_FILE = "";

	@FieldAnnotation(name = "ResourceBundle支持的语言")
	public static String RESOURCE_BUNDLE_LANGUAGE = "";

	@FieldAnnotation(name = "ResourceBundle默认的语言")
	public static String RESOURCE_BUNDLE_DEFAULT = "";

	@FieldAnnotation(name = "玩家消息流量统计开关")
	public static boolean SERVER_USER_MESSAGE_MONITOR = false;

	@FieldAnnotation(name = "是否定时记录用户消息监控信息")
	public static boolean SHOW_USER_MESSAGE_MONITOR_INFO = false;

	@FieldAnnotation(name = "通用服务")
	public static String COMMON_SERVICE_PATH = "/common_service.xml";

	@FieldAnnotation(name = "本地服务")
	public static String LOCAL_SERVICE_PATH = "/local_service.xml";

	@FieldAnnotation(name = "单个房间服务器负载上限")
	public static int GAME_ROOM_LIMIT = 100;
	
	@FieldAnnotation(name = "protobuf文件的路径")
	public static String PROTOBUF_PATH = "D:/myworkspace/网络协议/message.proto";
	
	@FieldAnnotation(name = "配置文件的路径")
	public static String CONFIG_FILE_PATH = "D:/myworkspace/GameServer/src/main/resources/excel";
	

	public static String info()
	{
		StringBuffer str = new StringBuffer();
		Field[] fields = ServerConfig.class.getDeclaredFields();
		for (Field f : fields)
		{
			try
			{
				str.append(((FieldAnnotation) f.getAnnotations()[0]).name())
						.append(":")
						.append(String.valueOf(f.get(ServerConfig.class)))
						.append("\n");
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return str.toString();
	}
}
