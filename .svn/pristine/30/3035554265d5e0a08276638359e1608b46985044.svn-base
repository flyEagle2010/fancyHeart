package com.doteplay.editor;

import java.awt.image.BufferedImage;
import java.io.File;

import com.doteplay.editor.common.DataManager;
import com.doteplay.editor.common.PermissionManager;
import com.doteplay.editor.database.BaseConnectionPool;
import com.doteplay.editor.util.Util;
import com.doteplay.editor.util.xml.simpleXML.ISimpleXmlSupport;
import com.doteplay.editor.util.xml.simpleXML.SimpleAttributes;
import com.doteplay.editor.util.xml.simpleXML.SimpleXmlParser;
import com.doteplay.editor.util.xml.simpleXML.StrUtils;

// 修改原则:
// 1、简化产品发布配置，将各种配置集中到一个文件中,便于查看和修改
// 2、使用xml作为配置文档载体，要求对每一个配置属性有基本的名称，便于理解
// 3、配置类只负责处理配置文件的读取，将业务逻辑全部移出
public class EditorConfig
{

	public static String rootPath = System.getProperty("user.dir");
	public static String imgPath = System.getProperty("user.dir")
			+ File.separatorChar + "img" + File.separatorChar;
	public static String dataPath = System.getProperty("user.dir")
			+ File.separatorChar + "data" + File.separatorChar;

	public static String confPath = System.getProperty("user.dir")
			+ File.separatorChar + "conf" + File.separatorChar;

	// 编译版本信息
	public final static String VERSION = "XEditor v1.01.12";
	// 注意版本字符串格式Build:xxxx.xx.xx.x位数不可变
	public static String VERSION_BUILD = VERSION + " Build:2012.04.18.0";

	public static File CURRENT_DIR = new File(".");

	public static boolean IS_OPEN_COPYRIGHT_WORD_FILTER = false;

	// 产品版本信息
	public static int VERSION_DEFAULT_TYPE = 0;
	public static int VERSION_TYPE = 0;
	public static String[] VERSION_NAMES =
	{ "240*320版本", "176*208高版本", "1000*600版本" };
	public static String[] VERSION_NAMES_SHORT =
	{ "240x320", "176x208", "1000x600" };
	// public static short[] VERSION_DEFAULT_UNIT = { 16, 12, 8 };
	// public static byte VERSION_MODULUS_MAP = 0;
	// public static byte VERSION_MODULUS_SPRITE = 1;

	/**
	 * 比例缩放系数
	 * 
	 */
	// public static int[/* 版本 */][/* 系数类型 */] VERSION_MODULUS =
	// {
	// { 1000, 1000 }, { 750, 1000 }, { 500, 750 }
	// };
	public static int FPS = 10;
	public static int MSPF = 1000 / FPS;

	/**
	 * 版本数据 0:地图格宽 1:地图格高 2:世界地图格宽 3:世界地图格高
	 */
	public static short[][] VERSION_DATA =
	{
	{ 16, 16, 8, 8 },
	{ 12, 12, 6, 6 },
	{ 48, 48, 24, 24 } };
	static
	{
		VERSION_DATA = new short[][]
		{
		{ 16, 16, 8, 8 },
		{ 12, 12, 6, 6 },
		{ 48, 48, 16, 16 } };

		FPS = 24;
		MSPF = 1000 / FPS;
	}

	public static final void setVersionType(int i)
	{
		VERSION_TYPE = i;
		short[] vd = VERSION_DATA[VERSION_TYPE];
		GW = vd[0];
		GH = vd[1];
		WORLD_MAP_GW = vd[2];
		WORLD_MAP_GH = vd[3];
		BLOCKIMG = Util.loadBufferedImage(imgPath + "/blocks0.png");

		BLOCKIMG = Util.scaleImage(BLOCKIMG, GW / 16f);
		// GW2=vd[2];
		// GH2=vd[3];
	}

	public static short GW = 16;
	public static short GH = 16;
	// public static short GW=16,GW2=8;
	// public static short GH=16,GH2=8;
	// public static byte GWB=4,GWB2=3,GHB=4,GHB2=3;
	// public static int GWMASK=0x000f,GHMASK=0x000f;
	// public static int MASK_W=0xfffffff0,MASK_H=0xfffffff0;

	public static short WORLD_MAP_GW = 8;
	public static short WORLD_MAP_GH = 8;

	/**
	 * 组选择常量
	 */
	public static int GROUPSELECT_NORMAL = 0;
	public static int GROUPSELECT_TILE = 1;
	public static int GROUPSELECT_CLIP = 2;

	/**
	 * 碰撞标志数量
	 */
	public static int BLOCKNUM = 14;
	/**
	 * 碰撞标志 : 无碰撞
	 */
	public static int BLOCK_NONE = 0;
	/**
	 * 碰撞标志 : 全碰撞
	 */
	public static int BLOCK_FULL = 13;

	public static BufferedImage BLOCKIMG; // @jve:decl-index=0:
	public static BufferedImage ORIGINIMG; // @jve:decl-index=0:

	// public static int DIR_NUM = 4;
	public static int DIR_UP = 0;
	public static int DIR_RIGHT = 1;
	public static int DIR_DOWN = 2;
	public static int DIR_LEFT = 3;

	public static int DIR_RIGHT_UP = 4;
	public static int DIR_RIGHT_DOWN = 5;
	public static int DIR_LEFT_DOWN = 6;
	public static int DIR_LEFT_UP = 7;

	public static String[] DIR_NAMES_CHINESE =
	{ "上", "右", "下", "左", "右上", "右下", "左下", "左上" };
	public static String[] DIR_NAMES_ENGLISH =
	{ "U", "R", "D", "L", "UR", "RD", "DL", "LU" };

	public static final int DIR_TYPE_1D = 0;
	public static final int DIR_TYPE_2D = 1;
	public static final int DIR_TYPE_4D = 2;
	public static final int DIR_TYPE_8D = 3;
	public static final String[] TYPE_DIR_STR =
	{ "1方向", "2方向", "4方向", "8方向" };

	public static final int[][] DIRECTION_TYPES = new int[][]
	{
			{ EditorConfig.DIR_DOWN },
			{ EditorConfig.DIR_RIGHT, EditorConfig.DIR_LEFT },
			{ EditorConfig.DIR_UP, EditorConfig.DIR_RIGHT,
					EditorConfig.DIR_DOWN, EditorConfig.DIR_LEFT },
			{ EditorConfig.DIR_UP, EditorConfig.DIR_RIGHT,
					EditorConfig.DIR_DOWN, EditorConfig.DIR_LEFT,
					EditorConfig.DIR_RIGHT_UP, EditorConfig.DIR_RIGHT_DOWN,
					EditorConfig.DIR_LEFT_DOWN, EditorConfig.DIR_LEFT_UP } };

	public static final int[] DIRECTION_MIRROR = new int[]
	{ DIR_UP, DIR_LEFT, DIR_DOWN, DIR_RIGHT,

	DIR_LEFT_UP, DIR_LEFT_DOWN, DIR_RIGHT_DOWN, DIR_RIGHT_UP, };

	// public static final int getDirIndexInDirTypes(int dir){
	//
	// }

	// Tile地表层
	public static final int LAYER_PHYSIC_TERRAIN = 0;
	// Tile装饰层
	public static final int LAYER_PHYSIC_DECO = 1;
	// Tile物体层
	public static final int LAYER_PHYSIC_OBJECT = 2;
	// Tile天空层
	public static final int LAYER_PHYSIC_SKY = 3;

	// 逻辑地表层
	public static final int LAYER_LOGIC_TERRAIN = 4;
	// 逻辑装饰层
	public static final int LAYER_LOGIC_DECO = 5;
	// 逻辑物体层
	public static final int LAYER_LOGIC_DOODAD = 6;
	// 逻辑天空层
	public static final int LAYER_LOGIC_SKY = 7;
	// 逻辑精灵层
	public static final int LAYER_LOGIC_UNIT = 8;

	// 切片地表层
	public static final int LAYER_PHYSIC_CLIP = 9;
	// 切片装饰层
	public static final int LAYER_PHYSIC_CLIP_DECO = 10;
	// 切片物体层
	public static final int LAYER_PHYSIC_CLIP_OBJECT = 11;
	// 切片天空层
	public static final int LAYER_PHYSIC_CLIP_SKY = 12;

	// 传送层
	public static final int LAYER_LOGIC_AREA = 13;
	// 地点编辑层
	public static final int LAYER_LOGIC_LOCATION = 14;
	// 碰撞层
	public static final int LAYER_BLOCK = 15;

	// 世界地图文字层
	public static final int LAYER_WORLD_WORDS = 16;
	// 覆盖层
	public static final int LAYER_COVER = 17;
	// 占地层
	public static final int LAYER_AREA = 18;
	// 可见范围层
	public static final int LAYER_BOUNDINGBOX = 19;
	// 空格标志层
	public static final int LAYER_EMPTYFLAG = 20;

	// 地图路径层
	public static int LAYER_WAYPOINT = 21;

	/**
	 * 物理层数量
	 */
	public static int LAYER_PHYSIC_NUM = 4;
	/**
	 * 可编辑层数量
	 */
	public static int LAYER_EDITNUM = 17;

	/**
	 * 场景类型
	 */
	public static String[] SCENETYPE_NAMES =
	{ "场景类型_1", "场景类型_2" };

	public static int SCENE_TYPE_1 = 0;
	public static int SCENE_TYPE_2 = 1;

	public static String[] LAYER_NAMES =
	{ "Tile地表层", "Tile装饰层", "Tile物体层", "Tile天空层", "逻辑地表层", "逻辑装饰层", "逻辑物体层",
			"逻辑天空层", "逻辑精灵层", "切片地表层", "切片装饰层", "切片物体层", "切片天空层", "区域层",
			"地点编辑层", "碰撞层", "世界地图文字层", "覆盖层", "占地层", "可见范围层", "空格标志层", "地图路径层" };

	public static int[][] MOBILE_VERSION =
	{
	{ 176, 208 },
	{ 240, 320 },
	{ 640, 480 },
	{ 1000, 600 } };

	public static String[] MOBILE_VERSION_NAME =
	{ "176*208", "240*320", "640*480", "1000*600" };
	/**
	 * 地图精灵类型
	 */
	public static int MAP_SPRITTYPE_DOODAD = 0;
	public static int MAP_SPRITTYPE_SINGLEFRAME = 1;
	public static int MAP_SPRITTYPE_SINGLEACTION = 2;

	/**
	 * 编辑模式
	 */
	public static int EDITMODE_ADD = 1;
	public static int EDITMODE_SELECT = 0;
	public static int EDITMODE_DELETE = 2;
	public static int EDITMODE_SELECTCOLOR = 3;
	public static int EDITMODE_DRAGPANEL = 4;
	public static int EDITMODE_SELECTPOINT = 5;
	public static int EDITMODE_ADDBLOCK = 6;
	public static int EDITMODE_DRAGPANEL_WORLDMAP = 7;

	/**
	 * 多层选择模式
	 */
	public static int LAYERCOPYMODE_TERRAIN = 1;
	public static int LAYERCOPYMODE_CLIP = 2;

	public static int IMAGE_MAX_HEIGHT = 10;

	public static int GENIUS_GW = 20;
	public static int GENIUS_GH = 20;
	public static int GENIUS_WW = 240;
	public static int GENIUS_WH = 320;
	public static int GENIUS_WGW = 12;
	public static int GENIUS_WGH = 16;

	public static boolean SHOW_LINE = false;
	public static boolean MOVE_CANVAS = false;

	// public static int IMAGE_MAX_COVER=10;

	public static boolean useDataBase = false;
	public static String dbname = "";
	public static boolean isPublisher = false;

	public static boolean BASEDATA_INFORMATION_FULL = true;

	protected static class ConfigParser implements ISimpleXmlSupport
	{

		@Override
		public void readXmlData(String nodename, SimpleAttributes attributes)
		{
			try
			{
				if ("config".equals(nodename))
				{
					// 基本配置
					useDataBase = StrUtils.parseboolean(
							attributes.getValue("usedb"), true);
				} else if ("database".equals(nodename))
				{
					// 生成数据库连接配置列表,将来会移出，暂时还留在此处
					BaseConnectionPool.regConnectionInfo(attributes);
				} else if ("datagroup".equals(nodename))
				{
					// 数据组登记
					DataManager.loadDataGroupConfig(attributes);
				} else if ("permission".equals(nodename))
				{
					// 权限点登记
					PermissionManager.regPermissionPoint(attributes
							.getValue("name"));
				} else if ("rolegroup".equals(nodename))
				{
					// 权限点登记
					PermissionManager.regPermissionGroup(
							StrUtils.parseint(attributes.getValue("id")),
							attributes.getValue("name"),
							attributes.getValue("desc"),
							attributes.getValue("permission"));
				} else if ("datapath".equals(nodename))
				{
					String path = attributes.getValue("path");
					if (!path.equals(""))
					{
						dataPath = path;
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public EditorConfig()
	{
	}

	public static int getLayerIndex(String name)
	{
		for (int i = 0; i < LAYER_NAMES.length; i++)
		{
			if (LAYER_NAMES[i].equals(name))
			{
				return i;
			}
		}
		return -1;
	}

	public static boolean init()
	{

		File file = new File(dataPath);
		if (!file.exists())
			file.mkdir();

		String config = confPath + "config.xml";
		SimpleXmlParser.parseXmlFramFile(config, new ConfigParser());

		setVersionType(VERSION_DEFAULT_TYPE);
		ORIGINIMG = Util.loadBufferedImage(imgPath + "/origin.png");
		return true;
	}

	/**
	 * flash场景存储的层关系
	 */
	public static final int FLASH_LAYER_TERRAIN_CLIP = 0;
	public static final int FLASH_LAYER_OBJECT = 1;
	public static final int FLASH_LAYER_SKY_CLIP = 2;
	public static final int FLASH_LAYER_TERRAIN_CLIP2 = 3;
	public static final int FLASH_LAYER_TERRAIN_DOODED1 = 4;
	public static final int FLASH_LAYER_TERRAIN_DOODED2 = 5;
	public static final int FLASH_LAYER_SKY_DOODED = 6;

	/**
	 * object 偏移量数据定义
	 */
	@Deprecated
	public static final int[] OBJECT_OFFSET =
	{ 0, -3, -2, -1, 1, 2, 3 };

	public static final float JPG_QUALITY = 0.80f;

	public static final String RES_DATA_TABLE_EDITOR = "t_game_resource_data";

	public static final String RES_DATA_TABLE_LOCK = "t_game_resource_data_lock";

	/**
	 * 编辑器中具有特殊权限的用户Id
	 */
	public final static int[] SPECIAL_RIGHT_OF_USER =
	{ 0 };
	public final static int ADMINUSERID = 0;

}
