package com.doteplay.editor.util.clipremap;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.JOptionPane;

public class Remapper
{
	private int computeW;
	private int computeH;
	private Rectangle[] rectangleSrc;
	private Rect[][] rects;
	private Vector<Rect> vRectsW; //按宽排序
	private Vector<Rect> vRectsH; //按高排序
	private Vector<Rect> vRectsA; //按面积排序
	private Vector<Rect> vUsedRects; //已经排好的矩形
	private Vector<Vector<Rect>> vSortResult = new Vector<Vector<Rect>>(); //存储排列的结果
	private Vector<Integer> vSmallResult = new Vector<Integer>(); //存储排列的结果

	private boolean isExit;

	public Remapper(int width, int imgArea, Rectangle[] rectangle)
	{
		if (width <= 0)
		{
			return;
		}
		this.rectangleSrc = rectangle;
		
		computeW = width;
		computeH = imgArea / width * 3;//高扩大，以免不够
		
		vRectsW = new Vector<Rect>();	//按宽排列
		vRectsH = new Vector<Rect>();	//按高排列
		vRectsA = new Vector<Rect>();	//按面积排列
		vUsedRects = new Vector<Rect>();	//已经排好的
		
		for (int i = 0; i < rectangleSrc.length; i++)
		{
			Rect r = new Rect(rectangleSrc[i].width, rectangleSrc[i].height);
			r.id = i;
			
			vRectsW.add(r);
			vRectsH.add(r);
			vRectsA.add(r);
		}

		RectSorter.sort(vRectsW, RectSorter.SORT_BY_WIDTH);
		RectSorter.sort(vRectsH, RectSorter.SORT_BY_HEIGHT);
		RectSorter.sort(vRectsA, RectSorter.SORT_BY_AREA);

		rects = new Rect[computeH][computeW];
		for (int i = 0; i < rects.length; i++)
		{
			for (int j = 0; j < rects[i].length; j++)
			{
				rects[i][j] = new Rect();
			}
		}
	}

	public void start()
	{
		try
		{
			if (vRectsW.get(0).w > computeW)	//如果最大的块比箱子宽
			{
				JOptionPane.showMessageDialog(null, "宽度不够,最宽的切块宽为" + vRectsW.get(0).w + ",您输入的宽为" + computeW + "!!!");
				return;
			}

			//开始 先把最高的块放进去
			Rect rtH = vRectsH.get(0);
			rtH.x = 0;
			rtH.y = 0;
			addUseRect(rtH);
			
			//把面积最大的放进去
			Rect rtA = vRectsA.get(0);
			findCanAddPos(rtA, getFristPointCanUse());
			addUseRect(rtA);
			
			while (!vRectsW.isEmpty())
			{
				if (isExit)
				{
					return;
				}
				Rect r = vRectsA.get(0);	//按照面积排样
				findCanAddPos(r, getFristPointCanUse());
				addUseRect(r);
			}
			addSortResult(vUsedRects);
		} catch (StackOverflowError e)
		{
			JOptionPane.showMessageDialog(null, e.toString() + "递归异常,请将宽度调大!!!" + "    ");
			return;
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "错误代码 ： " + e.getMessage() + "\n请将宽度调大");
			return;
		}
		
		for (int i = 0; i < vSortResult.get(0).size(); i++)
		{
			Rect r = vSortResult.get(0).get(i);
			rectangleSrc[r.id].x = r.x;
			rectangleSrc[r.id].y = r.y;
		}
	}

	/** 新添加矩形之后的相关处理 */
	private void addUseRect(Rect rect)
	{
		setRectIsUsed(rect, true);
		vUsedRects.add(rect);
		vRectsW.remove(rect);
		vRectsH.remove(rect);
		vRectsA.remove(rect);
	}
	
	/**
	 * @param rect
	 * @param p
	 *
	 * 递归调用方法，需优化
	 */
	private void findCanAddPos(Rect rect, Point p)
	{
		while (true)
		{
			//逐行扫描，检测rect可放置的位置
			rect.x = p.x;
			rect.y = p.y;
			if (rects[p.y][p.x].isUsed)
			{
				p.x += rects[p.y][p.x].w;
			} else if (computeW - p.x < rect.w)
			{
				p.x = 0;
				p.y++;
			} else
			{
				p.x++;
			}
			if (p.x >= computeW)
			{
				p.x = 0;
				p.y++;
			}
			if (isCanAddRect(rect))
			{
				break;
			}
		}
	}

	/**
	 * @return 从原点开始检测，得到第一个没使用的点（可优化）
	 */
	private Point getFristPointCanUse()
	{
		boolean isFind = false;
		
		Point p = new Point();//没使用的点
		p.x = 0;
		p.y = 0;
		while (!isFind)
		{
			if (rects[p.y][p.x].isUsed)	//如果检测点已经使用
			{
				p.x += rects[p.y][p.x].w;	//跳过被使用的部分
				
				if (p.x >= computeW)	//宽度过界，换行
				{
					p.x = 0;
					p.y++;
				}
			} else
			{
				isFind = true;
			}
		}
		return p;
	}

	/**
	 * @param rect
	 * 可优化
	 */
	private boolean isCanAddRect(Rect rect)
	{
		if (rect.x + rect.w > computeW)	//出屏
		{
			return false;		//不可放置
		}
		
		//如果已经使用
		for (int y = rect.y; y < rect.y + rect.h; y++)
		{
			for (int x = rect.x; x < rect.x + rect.w; x++)
			{
				if (rects[y][x].isUsed)
				{
					return false;
				}
			}
		}
		
		//如果超出范围
		if (rect.x + rect.w > computeW)
		{
			return false;
		}
		
		return true;
	}

	/** 把新放置的矩形所占用的面积全部标记为已用 */
	private void setRectIsUsed(Rect rect, boolean b)
	{
		for (int y = rect.y; y < rect.y + rect.h; y++)
		{
			for (int x = rect.x; x < rect.x + rect.w; x++)
			{
				rects[y][x].isUsed = b;
				rects[y][x].id = rect.id;
				rects[y][x].w = rect.w;
				rects[y][x].h = rect.h;
			}
		}
	}

	private void addSortResult(Vector<Rect> v)
	{
		Vector<Rect> vTem = new Vector<Rect>();
		int mw = 0;
		int mh = 0;
		for (int i = 0; i < v.size(); i++)
		{
			Rect r = v.get(i);
			vTem.add(r);
			if (r.x + r.w > mw)
			{
				mw = r.x + r.w;
			}
			if (r.y + r.h > mh)
			{
				mh = r.y + r.h;
			}
		}
		
		int nowArea = mw * mh;
		if (vSmallResult.size() > 0)
		{
			int smallArea = vSmallResult.get(0);
			if (nowArea < smallArea)
			{
				vSmallResult.add(0, nowArea);
				vSortResult.add(0, vTem);
			} else
			{
				vSmallResult.add(nowArea);
				vSortResult.add(vTem);
			}
		} else
		{
			vSmallResult.add(nowArea);
			vSortResult.add(vTem);
		}
	}
}