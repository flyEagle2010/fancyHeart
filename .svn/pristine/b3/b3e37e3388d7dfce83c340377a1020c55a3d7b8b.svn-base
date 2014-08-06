package com.doteyplay.game.domain.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.doteyplay.core.dbcs.DBCS;
import com.doteyplay.game.CommonConstants;
import com.doteyplay.game.config.ServerConfig;
import com.doteyplay.game.constants.IdType;
import com.doteyplay.game.domain.gamebean.IdBean;
import com.doteyplay.game.persistence.serverdata.idobj.IIdBeanDao;

public class IdGenerator
{

	private static final Logger logger = Logger.getLogger(IdGenerator.class
			.getName());
	
	private final ReentrantLock createLock = new ReentrantLock();

	private Map<IdType, Map<Integer, IdObj>> ID_GENERATOR = new HashMap<IdType, Map<Integer, IdObj>>(
			IdType.values().length);


	private IdGenerator()
	{
		int size = IdType.values().length;
		for (int i = 0; i < size; i++)
		{
			IdType idType = IdType.values()[i];
			ID_GENERATOR.put(idType, initIdGenerator(idType));
		}
	}

	/**
	 * 获取ID
	 * 
	 * @param type
	 * @param gameAreaId
	 * @return -1:未申请到ID
	 */
	public long getId(IdType idType, int gameAreaId)
	{
		if (gameAreaId < 1 || gameAreaId > 1024)
		{
			logger.error("非法的区ID:" + gameAreaId + "!!!!");
			return -1;
		}
		Map<Integer, IdObj> map = ID_GENERATOR.get(idType);
		// 此类型ID无定义,请检查代码,如果申请ID,请先在IdConstants类中定义
		if (map == null)
			throw new RuntimeException(
					"此类型ID无定义,请检查代码,如果申请ID,请先在IdConstants类中定义,ID类型:" + idType);
		IdObj object = map.get(gameAreaId);
		if (object == null)
		{
			try
			{
				createLock.lock();
				long minValue = getMinValueByGameAreaId(gameAreaId);
				object = createAtomicNumberObject(idType, minValue, gameAreaId);
				map.put(gameAreaId, object);
			} catch (Exception e)
			{
				logger.error("申请新" + idType.getTableName() + "ID时出错");
			} finally
			{
				createLock.unlock();
			}
			// }
		}
		return object.getNewId();
	}

	private Map<Integer, IdObj> initIdGenerator(IdType idType)
	{
		List<IdBean> list = DBCS.getExector(IIdBeanDao.class).selectIdBean(
				idType.getTableName(),idType.getGroupName(),idType.getKeyName());

		Map<Integer, IdObj> result = new HashMap<Integer, IdObj>();
		// 刚开服的新区,还未创建过ID
		if (list == null || list.isEmpty())
		{
			long minValue = getMinValueByGameAreaId(ServerConfig.SERVER_AREA_ID);
			result.put(
					ServerConfig.SERVER_AREA_ID,
					createAtomicNumberObject(idType, minValue,
							ServerConfig.SERVER_AREA_ID));
		}
		// 已创建过ID
		else
		{
			int gameAreaId;
			long curValue;
			for (IdBean o : list)
			{
				gameAreaId = (Integer) o.getGameAreaId();
				curValue = o.getNum();
				result.put(gameAreaId,
						createAtomicNumberObject(idType, curValue, gameAreaId));
				logger.error(gameAreaId + "区中的" + idType.getTableName()
						+ "的当前最大值为:" + curValue);
			}

		}

		return result;
	}

	private IdObj createAtomicNumberObject(IdType idType, long curValue,
			int gameAreaId)
	{
		Number maxValue = getMaxValueByGameAreaId(gameAreaId);
		if (maxValue == null)
			throw new RuntimeException("请确认ID类型.目前只支持int和long.当前类型:" + idType);

		IdBean idBean = new IdBean();
		idBean.setGameAreaId(gameAreaId);
		idBean.setKey(idType.getKeyName());
		idBean.setNum(curValue);
		return new IdObj(idBean);
	}

	/**
	 * 区ID上限
	 * 
	 * @param idType
	 * @param gameAreaId
	 * @return
	 */
	private long getMaxValueByGameAreaId(int gameAreaId)
	{
		return gameAreaId * CommonConstants.PER_AREA_ID_DELTA;
	}

	/**
	 * 区ID初始值
	 * 
	 * @param idType
	 * @param gameAreaId
	 * @return
	 */
	private long getMinValueByGameAreaId(int gameAreaId)
	{
		long tmp = (gameAreaId - 1) * CommonConstants.PER_AREA_ID_DELTA ;
		if(tmp == 0)
			tmp = 1;
		return tmp;
	}
	
	////////////////////////////////////////////////////////////////
	private final static IdGenerator instance = new IdGenerator();
	
	public final static IdGenerator getInstance()
	{
		return instance;
	}
}
