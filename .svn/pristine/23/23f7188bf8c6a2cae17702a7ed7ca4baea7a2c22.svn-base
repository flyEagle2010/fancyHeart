//package com.doteyplay.core.util;
//
//import java.util.concurrent.ArrayBlockingQueue;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.TimeUnit;
//
//import org.apache.log4j.PropertyConfigurator;
//
//public class QueueTester
//{
//	int size = 50000;
//	CircularDoubleBufferedQueue<Integer> queue = new CircularDoubleBufferedQueue<Integer>(size);
////	BlockingQueue queue = new ArrayBlockingQueue(size);
//	public static void main(String[] args)
//	{
//		System.out.println("-------Test start--------");
//		String userDir = System.getProperty("user.dir");
//		QueueTester qt = new QueueTester();
//		Thread ct1 = new Thread(qt.new Consummer(), "consummer1");
////		Thread ct2 = new Thread(qt.new Consummer(), "consummer2");
////		Thread ct3 = new Thread(qt.new Consummer(), "consummer3");
////		Thread ct4 = new Thread(qt.new Consummer(), "consummer4");
//		
//		Thread pt1 = new Thread(qt.new Productor(), "productor1");
//		Thread pt2 = new Thread(qt.new Productor(), "productor2");
//		Thread pt3 = new Thread(qt.new Productor(), "productor3");
//		Thread pt4 = new Thread(qt.new Productor(), "productor4");
//		Thread pt5 = new Thread(qt.new Productor(), "productor5");
//		
////		pt1.setDaemon(true);
////		pt2.setDaemon(true);
////		pt3.setDaemon(true);
////		pt4.setDaemon(true);
////		pt5.setDaemon(true);
//		
//		pt1.start();
//		pt2.start();
//		pt3.start();
//		pt4.start();
//		pt5.start();
//		
//		System.out.println("--------productor start");
//		try
//		{
//			Thread.sleep(20);
//		}
//		catch (InterruptedException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		ct1.start();
////		ct2.start();
////		ct3.start();
////		ct4.start();
//		
//		System.out.println("--------consummer start");
//	}
//	
//	class Consummer implements Runnable
//	{
//		public void run()
//		{
//			int count = 0;
//			long before = System.currentTimeMillis();
//			while (true)
//			{
//				try
//				{
//					queue.take();
//					count++;
//				}
//				catch (InterruptedException e)
//				{
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
////				try
////				{
////					Thread.sleep(Math.round(Math.random()*10));
////				}
////				catch (InterruptedException e)
////				{
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				}
//				if(count >= size)
//				{
//					System.out.println();
//				}
//				if(count > size+100)
//				{
//					break;
//				}
//			}
//			long after = System.currentTimeMillis();
//			System.out.println("Mission complete cast:" + (after-before));
//			
//		}
//	}
//	volatile static int i = 0;
//	
//	class Productor implements Runnable
//	{
//		public void run()
//		{
//			while (true)
//			{
//				try
//				{
//					queue.put(new Integer(i++),true);
//					if(i > size)
//						System.out.println();
//				}
//				catch (InterruptedException e)
//				{
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
////				try
////				{
////					Thread.sleep(Math.round(Math.random() * 5));
////				}
////				catch (InterruptedException e)
////				{
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				}
//			}
//		}
//	}
//}
