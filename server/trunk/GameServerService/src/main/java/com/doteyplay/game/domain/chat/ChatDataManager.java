package com.doteyplay.game.domain.chat;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import com.doteyplay.core.bhns.BOServiceManager;
import com.doteyplay.core.bhns.gateway.IGateWayService;
import com.doteyplay.core.server.task.AbstractTaskService;
import com.doteyplay.core.server.task.Task;
import com.doteyplay.core.server.task.TaskCallbackHandler;
import com.doteyplay.core.task.CommonTaskManager;
import com.doteyplay.game.message.chat.ChatMsgUpdateMessage;

/**
 * @className:ChatDataManager.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014年11月26日 下午6:21:17
 */
public class ChatDataManager {

	private static  ChatDataManager instance = new ChatDataManager();
	
	private ChatDataManager(){
		init();
	}
	
	public static ChatDataManager  getInstance(){
		return instance;
	}
	
	private AbstractTaskService commonTaskService=null;
	
	private TaskCallbackHandler scheduleAtFixedRate = null;
	
	private CopyOnWriteArraySet<Long> roleSet = new CopyOnWriteArraySet<Long>();
	
	//private LinkedBlockingQueue<ChatMsgBean> areadySendMessageQueue = new LinkedBlockingQueue<ChatMsgBean>();
	
	private LinkedBlockingQueue<ChatMsgUpdateMessage> currentMessageQueue = new LinkedBlockingQueue<ChatMsgUpdateMessage>();
	
	private AtomicBoolean open = new AtomicBoolean(false);
	
	private AtomicLong historyTime = new AtomicLong();
	
	public void putMessage(ChatMsgUpdateMessage msg){
		try {
			currentMessageQueue.put(msg);
			boolean compareAndSet = open.compareAndSet(false,true);
			if(compareAndSet){
				openScheduleTask();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void openScheduleTask() {
		// TODO Auto-generated method stub
		scheduleAtFixedRate = commonTaskService.scheduleAtFixedRate(new Task(){
			/**
			 * 传消息给客户端
			 */
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					ChatMsgUpdateMessage msg = null;
					try {
						if(currentMessageQueue.size()>0){
							msg = currentMessageQueue.take();
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(msg!=null){
						
						for (long roleId : roleSet) {
							sendMsg(roleId, msg);
						}
						//areadySendMessageQueue.add(msgBean);
						historyTime.set(System.currentTimeMillis());
					}else{
						
						long now = System.currentTimeMillis();
						if(now-historyTime.get()>1000L*60*5){
							cancleScheduleTask();
						}
						
					}
					
				
				}
			}
			
		});
	}
	
	private void sendMsg(long roleId, ChatMsgUpdateMessage msg) {
		IGateWayService gateWayService = BOServiceManager.findService(
				IGateWayService.PORTAL_ID, roleId);
		gateWayService.sendMessage(msg);
	}
	
	private void cancleScheduleTask(){
		if(scheduleAtFixedRate!=null){
			synchronized (scheduleAtFixedRate) {
				scheduleAtFixedRate.cancel();
				open.set(false);
			}
		}
	}

	/**
	 * 初使化一个线程负责处理,消费者的处理过程.
	 */
	public void init(){
		if(commonTaskService==null){
			commonTaskService = CommonTaskManager.getInstance().getCommonTaskService();
		}
	}
	
	
	public void addRole(long  roleId){
		roleSet.add(roleId);
	}
	
	public void removeRole(long  roleId){
		roleSet.remove(roleId);
	}
	
	
	public void release(){
		
		cancleScheduleTask();
		
		if(commonTaskService!=null){
			commonTaskService = null;
		}
		
		if(roleSet!=null){
			roleSet.clear();
			roleSet = null;
		}
		
		if(currentMessageQueue!=null){
			currentMessageQueue.clear();
			currentMessageQueue = null;
		}
	}
	
}
