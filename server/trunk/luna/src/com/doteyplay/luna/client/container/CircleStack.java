package com.doteyplay.luna.client.container;

public class CircleStack<T>
{
	/**
	 * 保存对象的容器类
	 */
	private Object[] elmentData;;
	/**
	 * 默认的缓冲区大小
	 */
	private static final int DEFAULT_SIZE = 1024 * 8;
	/**
	 * 缓冲区大小
	 */
	private int size;

	/**
	 * 出栈索引
	 */
	private int popIndex = 0;
	/**
	 * 入栈索引
	 */
	private int pushIndex = 0;

	public CircleStack()
	{
		this.size = DEFAULT_SIZE;
		this.elmentData = new Object[this.size];
	}

	/**
	 * 缓冲区大小
	 * 
	 * @param size
	 */
	public CircleStack(int size)
	{
		this.size = size;
		this.elmentData = new Object[this.size];
	}

	/**
	 * 出栈操作,出栈之后直接移除数据
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public synchronized T pop()
	{
		int index = this.getPopIndex();
		T t = (T) this.elmentData[index];
		if (t == null)
			this.popIndex = index;
		else
			this.elmentData[index] = null;
		return t;
	}

	/**
	 * 入栈操作
	 * 
	 * @param message
	 */
	public synchronized void push(T message)
	{
		this.elmentData[this.getPushIndex()] = message;
	}

	/**
	 * 获得出栈索引
	 * 
	 * @return
	 */
	public synchronized int getPopIndex()
	{
		if (this.popIndex == this.size)
			this.popIndex = 0;
		return this.popIndex++;
	}

	/**
	 * 获得入栈索引
	 * 
	 * @return
	 */
	public int getPushIndex()
	{
		if (this.pushIndex == this.size)
			this.pushIndex = 0;
		return this.pushIndex++;
	}

	public static void main(String[] args)
	{
		CircleStack<Integer> list = new CircleStack<Integer>(20);

		for (int i = 0; i < 5; i++)
		{
			list.push(i);
		}
		System.out.println("pushindex:" + list.pushIndex);
		for (int i = 0; i < 3; i++)
		{
			System.out.println(list.pop());
		}
		System.out.println("popindex:" + list.popIndex);
		System.out.println("===================");
		for (int i = 0; i < 20; i++)
		{
			list.push(i);
		}
		System.out.println("pushindex:" + list.pushIndex);
		for (int i = 0; i < 21; i++)
		{
			System.out.println(list.pop());
		}
		System.out.println("popindex:" + list.popIndex);
	}
}
