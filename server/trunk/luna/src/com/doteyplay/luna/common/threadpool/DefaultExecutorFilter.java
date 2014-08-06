package com.doteyplay.luna.common.threadpool;

import java.util.EnumSet;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.filterchain.IoFilterChain;
import org.apache.mina.core.filterchain.IoFilterEvent;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoEventType;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;
import org.apache.mina.filter.executor.IoEventQueueHandler;
import org.apache.mina.filter.executor.OrderedThreadPoolExecutor;

public class DefaultExecutorFilter extends IoFilterAdapter
{
	/** The list of handled events */
	private EnumSet<IoEventType> eventTypes;

	/** The associated executor */
	private Executor executor;

	/** A flag set if the executor can be managed */
	private boolean manageableExecutor;

	/** The default pool size */
	private static final int DEFAULT_MAX_POOL_SIZE = 16;

	/** The number of thread to create at startup */
	private static final int BASE_THREAD_NUMBER = 0;

	/** The default KeepAlive time, in seconds */
	private static final long DEFAULT_KEEPALIVE_TIME = 30;

	/**
	 * A set of flags used to tell if the Executor has been created in the
	 * constructor or passed as an argument. In the second case, the executor
	 * state can be managed.
	 **/
	private static final boolean MANAGEABLE_EXECUTOR = true;
	private static final boolean NOT_MANAGEABLE_EXECUTOR = false;

	/** A list of default EventTypes to be handled by the executor */
	private static IoEventType[] DEFAULT_EVENT_SET = new IoEventType[] {
			IoEventType.EXCEPTION_CAUGHT, IoEventType.MESSAGE_RECEIVED,
			IoEventType.MESSAGE_SENT, IoEventType.SESSION_CLOSED,
			IoEventType.SESSION_IDLE, IoEventType.SESSION_OPENED };

	/**
	 * (Convenience constructor) Creates a new instance with a new
	 * {@link OrderedThreadPoolExecutor}, no thread in the pool, and a maximum
	 * of 16 threads in the pool. All the event will be handled by this default
	 * executor.
	 */
	public DefaultExecutorFilter()
	{
		// Create a new default Executor
		Executor executor = createDefaultExecutor(BASE_THREAD_NUMBER,
				DEFAULT_MAX_POOL_SIZE, DEFAULT_KEEPALIVE_TIME,
				TimeUnit.SECONDS, Executors.defaultThreadFactory(), null);

		// Initialize the filter
		init(executor, MANAGEABLE_EXECUTOR);
	}

	/**
	 * (Convenience constructor) Creates a new instance with a new
	 * {@link OrderedThreadPoolExecutor}, no thread in the pool, but a maximum
	 * of threads in the pool is given. All the event will be handled by this
	 * default executor.
	 * 
	 * @param maximumPoolSize
	 *            The maximum pool size
	 */
	public DefaultExecutorFilter(int maximumPoolSize)
	{
		// Create a new default Executor
		Executor executor = createDefaultExecutor(BASE_THREAD_NUMBER,
				maximumPoolSize, DEFAULT_KEEPALIVE_TIME, TimeUnit.SECONDS,
				Executors.defaultThreadFactory(), null);

		// Initialize the filter
		init(executor, MANAGEABLE_EXECUTOR);
	}

	/**
	 * (Convenience constructor) Creates a new instance with a new
	 * {@link OrderedThreadPoolExecutor}, a number of thread to start with, a
	 * maximum of threads the pool can contain. All the event will be handled by
	 * this default executor.
	 * 
	 * @param corePoolSize
	 *            The initial pool size
	 * @param maximumPoolSize
	 *            The maximum pool size
	 */
	public DefaultExecutorFilter(int corePoolSize, int maximumPoolSize)
	{
		// Create a new default Executor
		Executor executor = createDefaultExecutor(corePoolSize,
				maximumPoolSize, DEFAULT_KEEPALIVE_TIME, TimeUnit.SECONDS,
				Executors.defaultThreadFactory(), null);

		// Initialize the filter
		init(executor, MANAGEABLE_EXECUTOR);
	}

	/**
	 * (Convenience constructor) Creates a new instance with a new
	 * {@link OrderedThreadPoolExecutor}.
	 * 
	 * @param corePoolSize
	 *            The initial pool size
	 * @param maximumPoolSize
	 *            The maximum pool size
	 * @param keepAliveTime
	 *            Default duration for a thread
	 * @param unit
	 *            Time unit used for the keepAlive value
	 */
	public DefaultExecutorFilter(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit)
	{
		// Create a new default Executor
		Executor executor = createDefaultExecutor(corePoolSize,
				maximumPoolSize, keepAliveTime, unit,
				Executors.defaultThreadFactory(), null);

		// Initialize the filter
		init(executor, MANAGEABLE_EXECUTOR);
	}

	/**
	 * (Convenience constructor) Creates a new instance with a new
	 * {@link OrderedThreadPoolExecutor}.
	 * 
	 * @param corePoolSize
	 *            The initial pool size
	 * @param maximumPoolSize
	 *            The maximum pool size
	 * @param keepAliveTime
	 *            Default duration for a thread
	 * @param unit
	 *            Time unit used for the keepAlive value
	 * @param queueHandler
	 *            The queue used to store events
	 */
	public DefaultExecutorFilter(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, IoEventQueueHandler queueHandler)
	{
		// Create a new default Executor
		Executor executor = createDefaultExecutor(corePoolSize,
				maximumPoolSize, keepAliveTime, unit,
				Executors.defaultThreadFactory(), queueHandler);

		// Initialize the filter
		init(executor, MANAGEABLE_EXECUTOR);
	}

	/**
	 * (Convenience constructor) Creates a new instance with a new
	 * {@link OrderedThreadPoolExecutor}.
	 * 
	 * @param corePoolSize
	 *            The initial pool size
	 * @param maximumPoolSize
	 *            The maximum pool size
	 * @param keepAliveTime
	 *            Default duration for a thread
	 * @param unit
	 *            Time unit used for the keepAlive value
	 * @param threadFactory
	 *            The factory used to create threads
	 */
	public DefaultExecutorFilter(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, ThreadFactory threadFactory)
	{
		// Create a new default Executor
		Executor executor = createDefaultExecutor(corePoolSize,
				maximumPoolSize, keepAliveTime, unit, threadFactory, null);

		// Initialize the filter
		init(executor, MANAGEABLE_EXECUTOR);
	}

	/**
	 * (Convenience constructor) Creates a new instance with a new
	 * {@link OrderedThreadPoolExecutor}.
	 * 
	 * @param corePoolSize
	 *            The initial pool size
	 * @param maximumPoolSize
	 *            The maximum pool size
	 * @param keepAliveTime
	 *            Default duration for a thread
	 * @param unit
	 *            Time unit used for the keepAlive value
	 * @param threadFactory
	 *            The factory used to create threads
	 * @param queueHandler
	 *            The queue used to store events
	 */
	public DefaultExecutorFilter(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, ThreadFactory threadFactory,
			IoEventQueueHandler queueHandler)
	{
		// Create a new default Executor
		Executor executor = new OrderedThreadPoolExecutor(corePoolSize,
				maximumPoolSize, keepAliveTime, unit, threadFactory,
				queueHandler);

		// Initialize the filter
		init(executor, MANAGEABLE_EXECUTOR);
	}

	/**
	 * (Convenience constructor) Creates a new instance with a new
	 * {@link OrderedThreadPoolExecutor}.
	 * 
	 * @param eventTypes
	 *            The event for which the executor will be used
	 */
	public DefaultExecutorFilter(IoEventType... eventTypes)
	{
		// Create a new default Executor
		Executor executor = createDefaultExecutor(BASE_THREAD_NUMBER,
				DEFAULT_MAX_POOL_SIZE, DEFAULT_KEEPALIVE_TIME,
				TimeUnit.SECONDS, Executors.defaultThreadFactory(), null);

		// Initialize the filter
		init(executor, MANAGEABLE_EXECUTOR, eventTypes);
	}

	/**
	 * (Convenience constructor) Creates a new instance with a new
	 * {@link OrderedThreadPoolExecutor}.
	 * 
	 * @param maximumPoolSize
	 *            The maximum pool size
	 * @param eventTypes
	 *            The event for which the executor will be used
	 */
	public DefaultExecutorFilter(int maximumPoolSize, IoEventType... eventTypes)
	{
		// Create a new default Executor
		Executor executor = createDefaultExecutor(BASE_THREAD_NUMBER,
				maximumPoolSize, DEFAULT_KEEPALIVE_TIME, TimeUnit.SECONDS,
				Executors.defaultThreadFactory(), null);

		// Initialize the filter
		init(executor, MANAGEABLE_EXECUTOR, eventTypes);
	}

	/**
	 * (Convenience constructor) Creates a new instance with a new
	 * {@link OrderedThreadPoolExecutor}.
	 * 
	 * @param corePoolSize
	 *            The initial pool size
	 * @param maximumPoolSize
	 *            The maximum pool size
	 * @param eventTypes
	 *            The event for which the executor will be used
	 */
	public DefaultExecutorFilter(int corePoolSize, int maximumPoolSize,
			IoEventType... eventTypes)
	{
		// Create a new default Executor
		Executor executor = createDefaultExecutor(corePoolSize,
				maximumPoolSize, DEFAULT_KEEPALIVE_TIME, TimeUnit.SECONDS,
				Executors.defaultThreadFactory(), null);

		// Initialize the filter
		init(executor, MANAGEABLE_EXECUTOR, eventTypes);
	}

	/**
	 * (Convenience constructor) Creates a new instance with a new
	 * {@link OrderedThreadPoolExecutor}.
	 * 
	 * @param corePoolSize
	 *            The initial pool size
	 * @param maximumPoolSize
	 *            The maximum pool size
	 * @param keepAliveTime
	 *            Default duration for a thread
	 * @param unit
	 *            Time unit used for the keepAlive value
	 * @param eventTypes
	 *            The event for which the executor will be used
	 */
	public DefaultExecutorFilter(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, IoEventType... eventTypes)
	{
		// Create a new default Executor
		Executor executor = createDefaultExecutor(corePoolSize,
				maximumPoolSize, keepAliveTime, unit,
				Executors.defaultThreadFactory(), null);

		// Initialize the filter
		init(executor, MANAGEABLE_EXECUTOR, eventTypes);
	}

	/**
	 * (Convenience constructor) Creates a new instance with a new
	 * {@link OrderedThreadPoolExecutor}.
	 * 
	 * @param corePoolSize
	 *            The initial pool size
	 * @param maximumPoolSize
	 *            The maximum pool size
	 * @param keepAliveTime
	 *            Default duration for a thread
	 * @param unit
	 *            Time unit used for the keepAlive value
	 * @param queueHandler
	 *            The queue used to store events
	 * @param eventTypes
	 *            The event for which the executor will be used
	 */
	public DefaultExecutorFilter(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit,
			IoEventQueueHandler queueHandler, IoEventType... eventTypes)
	{
		// Create a new default Executor
		Executor executor = createDefaultExecutor(corePoolSize,
				maximumPoolSize, keepAliveTime, unit,
				Executors.defaultThreadFactory(), queueHandler);

		// Initialize the filter
		init(executor, MANAGEABLE_EXECUTOR, eventTypes);
	}

	/**
	 * (Convenience constructor) Creates a new instance with a new
	 * {@link OrderedThreadPoolExecutor}.
	 * 
	 * @param corePoolSize
	 *            The initial pool size
	 * @param maximumPoolSize
	 *            The maximum pool size
	 * @param keepAliveTime
	 *            Default duration for a thread
	 * @param unit
	 *            Time unit used for the keepAlive value
	 * @param threadFactory
	 *            The factory used to create threads
	 * @param eventTypes
	 *            The event for which the executor will be used
	 */
	public DefaultExecutorFilter(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, ThreadFactory threadFactory,
			IoEventType... eventTypes)
	{
		// Create a new default Executor
		Executor executor = createDefaultExecutor(corePoolSize,
				maximumPoolSize, keepAliveTime, unit, threadFactory, null);

		// Initialize the filter
		init(executor, MANAGEABLE_EXECUTOR, eventTypes);
	}

	/**
	 * (Convenience constructor) Creates a new instance with a new
	 * {@link OrderedThreadPoolExecutor}.
	 * 
	 * @param corePoolSize
	 *            The initial pool size
	 * @param maximumPoolSize
	 *            The maximum pool size
	 * @param keepAliveTime
	 *            Default duration for a thread
	 * @param unit
	 *            Time unit used for the keepAlive value
	 * @param threadFactory
	 *            The factory used to create threads
	 * @param queueHandler
	 *            The queue used to store events
	 * @param eventTypes
	 *            The event for which the executor will be used
	 */
	public DefaultExecutorFilter(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, ThreadFactory threadFactory,
			IoEventQueueHandler queueHandler, IoEventType... eventTypes)
	{
		// Create a new default Executor
		Executor executor = new OrderedThreadPoolExecutor(corePoolSize,
				maximumPoolSize, keepAliveTime, unit, threadFactory,
				queueHandler);

		// Initialize the filter
		init(executor, MANAGEABLE_EXECUTOR, eventTypes);
	}

	/**
	 * FIXME:其实整个类就是为了修改此处唯一一个不同的参数 Creates a new instance with the specified
	 * {@link Executor}.
	 * 
	 * @param executor
	 *            the user's managed Executor to use in this filter
	 */
	public DefaultExecutorFilter(Executor executor)
	{
		// Initialize the filter
		init(executor, MANAGEABLE_EXECUTOR);
	}

	/**
	 * Creates a new instance with the specified {@link Executor}.
	 * 
	 * @param executor
	 *            the user's managed Executor to use in this filter
	 * @param eventTypes
	 *            The event for which the executor will be used
	 */
	public DefaultExecutorFilter(Executor executor, IoEventType... eventTypes)
	{
		// Initialize the filter
		init(executor, NOT_MANAGEABLE_EXECUTOR, eventTypes);
	}

	/**
	 * Create an OrderedThreadPool executor.
	 * 
	 * @param corePoolSize
	 *            The initial pool sizePoolSize
	 * @param maximumPoolSize
	 *            The maximum pool size
	 * @param keepAliveTime
	 *            Default duration for a thread
	 * @param unit
	 *            Time unit used for the keepAlive value
	 * @param threadFactory
	 *            The factory used to create threads
	 * @param queueHandler
	 *            The queue used to store events
	 * @return An instance of the created Executor
	 */
	private Executor createDefaultExecutor(int corePoolSize,
			int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			ThreadFactory threadFactory, IoEventQueueHandler queueHandler)
	{
		// Create a new Executor
		Executor executor = new OrderedThreadPoolExecutor(corePoolSize,
				maximumPoolSize, keepAliveTime, unit, threadFactory,
				queueHandler);

		return executor;
	}

	/**
	 * Create an EnumSet from an array of EventTypes, and set the associated
	 * eventTypes field.
	 * 
	 * @param eventTypes
	 *            The array of handled events
	 */
	private void initEventTypes(IoEventType... eventTypes)
	{
		if ((eventTypes == null) || (eventTypes.length == 0))
		{
			eventTypes = DEFAULT_EVENT_SET;
		}

		// Copy the list of handled events in the event set
		this.eventTypes = EnumSet.of(eventTypes[0], eventTypes);

		// Check that we don't have the SESSION_CREATED event in the set
		if (this.eventTypes.contains(IoEventType.SESSION_CREATED))
		{
			this.eventTypes = null;
			throw new IllegalArgumentException(IoEventType.SESSION_CREATED
					+ " is not allowed.");
		}
	}

	/**
	 * Creates a new instance of DefaultExecutorFilter. This private constructor
	 * is called by all the public constructor.
	 * 
	 * @param executor
	 *            The underlying {@link Executor} in charge of managing the
	 *            Thread pool.
	 * @param manageableExecutor
	 *            Tells if the Executor's Life Cycle can be managed or not
	 * @param eventTypes
	 *            The lit of event which are handled by the executor
	 * @param
	 */
	private void init(Executor executor, boolean manageableExecutor,
			IoEventType... eventTypes)
	{
		if (executor == null)
		{
			throw new IllegalArgumentException("executor");
		}

		initEventTypes(eventTypes);
		this.executor = executor;
		this.manageableExecutor = manageableExecutor;
	}

	/**
	 * Shuts down the underlying executor if this filter hase been created via a
	 * convenience constructor.
	 */
	@Override
	public void destroy()
	{
		if (manageableExecutor)
		{
			((ExecutorService) executor).shutdown();
		}
	}

	/**
	 * Returns the underlying {@link Executor} instance this filter uses.
	 * 
	 * @return The underlying {@link Executor}
	 */
	public final Executor getExecutor()
	{
		return executor;
	}

	/**
	 * Fires the specified event through the underlying executor.
	 * 
	 * @param event
	 *            The filtered event
	 */
	protected void fireEvent(IoFilterEvent event)
	{
		executor.execute(event);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onPreAdd(IoFilterChain parent, String name,
			NextFilter nextFilter) throws Exception
	{
		if (parent.contains(this))
		{
			throw new IllegalArgumentException(
					"You can't add the same filter instance more than once.  Create another instance and add it.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void sessionOpened(NextFilter nextFilter, IoSession session)
	{
		if (eventTypes.contains(IoEventType.SESSION_OPENED))
		{
			IoFilterEvent event = new IoFilterEvent(nextFilter,
					IoEventType.SESSION_OPENED, session, null);
			fireEvent(event);
		} else
		{
			nextFilter.sessionOpened(session);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void sessionClosed(NextFilter nextFilter, IoSession session)
	{
		if (eventTypes.contains(IoEventType.SESSION_CLOSED))
		{
			IoFilterEvent event = new IoFilterEvent(nextFilter,
					IoEventType.SESSION_CLOSED, session, null);
			fireEvent(event);
		} else
		{
			nextFilter.sessionClosed(session);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void sessionIdle(NextFilter nextFilter, IoSession session,
			IdleStatus status)
	{
		if (eventTypes.contains(IoEventType.SESSION_IDLE))
		{
			IoFilterEvent event = new IoFilterEvent(nextFilter,
					IoEventType.SESSION_IDLE, session, status);
			fireEvent(event);
		} else
		{
			nextFilter.sessionIdle(session, status);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void exceptionCaught(NextFilter nextFilter, IoSession session,
			Throwable cause)
	{
		if (eventTypes.contains(IoEventType.EXCEPTION_CAUGHT))
		{
			IoFilterEvent event = new IoFilterEvent(nextFilter,
					IoEventType.EXCEPTION_CAUGHT, session, cause);
			fireEvent(event);
		} else
		{
			nextFilter.exceptionCaught(session, cause);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void messageReceived(NextFilter nextFilter, IoSession session,
			Object message)
	{
		if (eventTypes.contains(IoEventType.MESSAGE_RECEIVED))
		{
			IoFilterEvent event = new IoFilterEvent(nextFilter,
					IoEventType.MESSAGE_RECEIVED, session, message);
			fireEvent(event);
		} else
		{
			nextFilter.messageReceived(session, message);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void messageSent(NextFilter nextFilter, IoSession session,
			WriteRequest writeRequest)
	{
		if (eventTypes.contains(IoEventType.MESSAGE_SENT))
		{
			IoFilterEvent event = new IoFilterEvent(nextFilter,
					IoEventType.MESSAGE_SENT, session, writeRequest);
			fireEvent(event);
		} else
		{
			nextFilter.messageSent(session, writeRequest);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void filterWrite(NextFilter nextFilter, IoSession session,
			WriteRequest writeRequest)
	{
		if (eventTypes.contains(IoEventType.WRITE))
		{
			IoFilterEvent event = new IoFilterEvent(nextFilter,
					IoEventType.WRITE, session, writeRequest);
			fireEvent(event);
		} else
		{
			nextFilter.filterWrite(session, writeRequest);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void filterClose(NextFilter nextFilter, IoSession session)
			throws Exception
	{
		if (eventTypes.contains(IoEventType.CLOSE))
		{
			IoFilterEvent event = new IoFilterEvent(nextFilter,
					IoEventType.CLOSE, session, null);
			fireEvent(event);
		} else
		{
			nextFilter.filterClose(session);
		}
	}
}
