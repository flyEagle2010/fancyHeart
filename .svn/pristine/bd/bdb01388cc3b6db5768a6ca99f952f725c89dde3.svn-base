package com.doteyplay.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.doteyplay.game.util.DateTimeUtil;

public abstract class AbstractTCPServer {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AbstractTCPServer.class);

	/**
	 * 8CPU每个处理5个线程，主线程一个
	 */
	protected boolean binded = false;
	
	protected int serverPort;
	protected InetSocketAddress serverAddress;
	protected NioSocketAcceptor acceptor;
	protected IoHandler sessionHandler;
	private ProtocolCodecFactory protocolCodeFactory;
	private DefaultThreadFactory defaultThreadFactory;
	
	public AbstractTCPServer()
	{
		
	}
	
	public boolean getPortBinded()
	{
		return binded;
	}
	
	public final void setServerPort(int serverPort)
	{
		this.serverPort = serverPort;
	}
	
	public final int getServerPort()
	{
		return serverPort;
	}
	
	public final void start() throws IOException
	{
		serverAddress = new InetSocketAddress(serverPort);
		acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors() + 1);
		acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(protocolCodeFactory));
		// 加解密过滤器
//		acceptor.getFilterChain().addLast("encrypt",new DefaultMessageEncryptFilter());
		//限制并发的线程池
		acceptor.getFilterChain().addLast("threadPool" , createExecutorFilter());
		acceptor.getSessionConfig().setReceiveBufferSize(4096);//设置输入缓冲区的大小
		
		//设置为非延迟发送，为true则不组装成大包发送，收到东西马上发出
		acceptor.getSessionConfig().setTcpNoDelay(true);
		acceptor.setHandler(sessionHandler);
		
		try
		{
			acceptor.getSessionConfig().setSoLinger(0);
			acceptor.setReuseAddress(true);
		}
		catch (Exception e)
		{
			logger.error("启动TCP服务器失败",e);
		}
		acceptor.bind(serverAddress);
		
		binded = true;
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				acceptor.unbind(serverAddress);
			}
		});
	}
	
	public final void stop()
	{
		dispose();
		
		acceptor.unbind(serverAddress);
		
		binded = false;
		
		acceptor.dispose();
	}
	
	public abstract void dispose();

	public IoHandler getSessionHandler() {
		return sessionHandler;
	}

	public void setSessionHandler(IoHandler sessionHandler) {
		this.sessionHandler = sessionHandler;
	}

	public ProtocolCodecFactory getProtocolCodeFactory() {
		return protocolCodeFactory;
	}

	public void setProtocolCodeFactory(ProtocolCodecFactory protocolCodeFactory) {
		this.protocolCodeFactory = protocolCodeFactory;
	}
	
	private ExecutorFilter createExecutorFilter() {
		int maximumPoolSize = 60;
		defaultThreadFactory = new DefaultThreadFactory();
		ExecutorFilter fileter = new ExecutorFilter(0,maximumPoolSize,30l,TimeUnit.SECONDS,defaultThreadFactory);
		return fileter;
	}
	
	/**
     * The default thread factory
     */
    static class DefaultThreadFactory implements ThreadFactory {
        static final AtomicInteger poolNumber = new AtomicInteger(1);
        final ThreadGroup group;
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final String namePrefix;

        DefaultThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null)? s.getThreadGroup() :
                                 Thread.currentThread().getThreadGroup();
            namePrefix = "GameServerTcpPool-" +
                          poolNumber.getAndIncrement() +
                         "-thread-";
        }

        @Override
		public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                                  namePrefix + threadNumber.getAndIncrement(),
                                  0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
    public void putOut() {
    	System.out.println("=====================当前线程池状况"+DateTimeUtil.getTimeString(System.currentTimeMillis())+"============================");
		final ThreadGroup group = this.defaultThreadFactory.group;
		// 活跃线程数
		int activeCount = group.activeCount();
		System.out.println("线程池名："+group.getName());
		System.out.println("线程池信息："+group);
		System.out.println("线程池中活跃线程数："+activeCount);
		System.out.println("活跃线程组数："+group.activeGroupCount());
		System.out.println("线程池标准输出开始：");
		// 将有关此线程组的信息输出到标准输出
		group.list();
		System.out.println("线程池标准输出结束：");
		System.out.println("线程池详细输出开始：");
		Thread[] list = new Thread[activeCount];
		int result = group.enumerate(list, true);
		System.out.println("设置活跃线程数组大小："+activeCount+",实际活跃线程数组大小："+result);
		for (Thread t : list) {
			if (t == null)
				continue;
			System.out.println("===========id:"+t.getId()+",name:"+t.getName()+",state:"+t.getState()+"===========");
			StackTraceElement[] se = t.getStackTrace();
			if (se != null) {
				for (StackTraceElement s : se)
					System.out.println(s);
			}
//				Map<Thread,StackTraceElement[]> map = t.getAllStackTraces();
//				System.out.println("map的大小"+map.size()+"======================================================");
//				if (map != null) {
//					for (StackTraceElement[] se : map.values()) {
//						if (se == null)
//							continue;
//						for (StackTraceElement s : se)
//							System.out.println(s);
//					}
//				}
//				System.out.println("======================================================");
		}
		list = null;
		System.out.println("线程池详细输出结束：");
		System.out.println("=====================当前线程池状况============================");
    }
	
}// Class AbstractTCPServer
