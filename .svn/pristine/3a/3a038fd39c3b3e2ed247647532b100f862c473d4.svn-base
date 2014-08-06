package com.doteyplay.task.base;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * ͨ�õ���������ṩ��
 * 
 * @author lipengbin
 * 
 */
public class SimpleTaskService extends AbstractTaskService {

	/**
	 * ����ִ�е��̳߳ض���
	 */
	private final ScheduledThreadPoolExecutor scheduler = (ScheduledThreadPoolExecutor) Executors
			.newScheduledThreadPool(16);
	/**
	 * ��һ��ִ�е��ӳ�ʱ��
	 */
	private static long initialDelay = 20001;

	/**
	 * �������
	 */
	private static SimpleTaskService instance = new SimpleTaskService();

	private SimpleTaskService() {
	}

	public static SimpleTaskService getInstance() {
		return instance;
	}

	@Override
	protected long getDelay() {
		return initialDelay;
	}

	@Override
	protected ScheduledThreadPoolExecutor getExecutor() {
		return scheduler;
	}

	@Override
	protected String getName() {
		return "ͨ�����������";
	}

	@Override
	protected long getPeriod() {
		return ServiceConstants.MILLISECOND_PER_FRAME;
	}

}
