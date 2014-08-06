package com.doteyplay.game.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具类, 类提供了对list和array的排序。
 * 获取总页数:
 * @see getTotalPageNum(int totalRecordNum, int perPageRecordNum)
 * 分页方法：
 * @see getCurrentPageList(T[] arrays, int curPageNum)
 * @see getCurrentPageList(List<T> list, int curPageNum, int perPageRecordNum)
 * 如果需要使用此类进行排序：只需使用上面提供的分页方法。
 * 这些分页方法是overload.它支持使用一个指定的每页记录数，进行分页处理；
 * 同时，也给予了一个默认大小的每页记录排序方法。
 */
public final class PageUtil {
	
	/**
	 * 空数组
	 */
	private static final Object[] EMPTY_ARRAY = new Object[0];
	
	/**
	 * 空LIST
	 */
	@SuppressWarnings("rawtypes")
	private static final List EMPTY_LIST = new ArrayList(0);
	
	/**
	 * 默认每页总记录数
	 */
	private static final byte DEFAULT_PER_PAGE_RECORD_NUM = 20;
		
	private PageUtil() {}
	
	
	/**
	 * 获取总页数
	 * @param totalRecordNum
	 * @return
	 * int 
	 * author luowei
	 */
	public static int getTotalPageNum(int totalRecordNum) {
		return getTotalPageNum(totalRecordNum, PageUtil.DEFAULT_PER_PAGE_RECORD_NUM);
	}
	
	/**
	 * 获得总页数
	 * @author luowei
	 * @param totalRecordNum
	 * @param perPageRecordNum
	 * void
	 */
	public static int getTotalPageNum(int totalRecordNum, int perPageRecordNum) {
		//总记录数小于每页记录数
		if (totalRecordNum < perPageRecordNum) {
			return 1;
		}
		//如果整除
		if (totalRecordNum % perPageRecordNum == 0) {
			return totalRecordNum / perPageRecordNum;
		}
		return totalRecordNum / perPageRecordNum + 1;
	}
	
	public static <T> T[] getCurrentPageList(T[] arrays, int curPageNum) {
		return getCurrentPageList(arrays, curPageNum, PageUtil.DEFAULT_PER_PAGE_RECORD_NUM);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T[] getCurrentPageList(T[] arrays, 
			int curPageNum, int perPageRecordNum) {
		assertNULL(arrays);
		int totalRecordNum = arrays.length;
		int totalPageNum = getTotalPageNum(totalRecordNum, perPageRecordNum);
		int beginIndex = getBeginIndex(totalPageNum, curPageNum, perPageRecordNum);		
		if (isValidBoundsOfBeginIndex(beginIndex, totalRecordNum)) {
			return (T[]) EMPTY_ARRAY;
		}
		int endIndex = getEndIndex(totalPageNum, totalRecordNum, beginIndex);
		
		@SuppressWarnings("rawtypes")
		Class c = arrays.getClass();
		T[] newArray = (T[]) createNewArray(c, endIndex - beginIndex + 1);
		System.arraycopy(arrays, beginIndex, newArray, 0, newArray.length);
		
		return newArray;
	}
	
	/**
	 * 获取当前页
	 * @param <T>
	 * @param list
	 * @param curPageNum
	 * @param perPageRecordNum 每页记录数
	 * @return
	 * List<T> 
	 * author luowei
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getCurrentPageList(List<T> list,
			int curPageNum, int perPageRecordNum) {
		assertNULL(list);
		int totalRecordNum = list.size();
		int totalPageNum = getTotalPageNum(totalRecordNum, perPageRecordNum);
		int beginIndex = getBeginIndex(totalPageNum, curPageNum, perPageRecordNum);		
		if (isValidBoundsOfBeginIndex(beginIndex, totalRecordNum)) {
			return EMPTY_LIST;
		}
		int endIndex = getEndIndex(perPageRecordNum, totalRecordNum, beginIndex);
		
		return list.subList(beginIndex, endIndex);
	}
	
	public static <T> List<T> getCurrentPageList(List<T> list, int curPageNum) {
		return getCurrentPageList(list, curPageNum, PageUtil.DEFAULT_PER_PAGE_RECORD_NUM);
	}
	
	/**
	 * 创建一个新的数组
	 * @param c
	 * @param length
	 * @return
	 * Object 
	 * author luowei
	 */
	private static Object createNewArray(Class<Object> c, int length) {
		Class<?> componentType = c.getComponentType();
		return Array.newInstance(componentType, length);
	}
	
	/**
	 * 获取每页的第一个值的索引
	 * @param totalPageNum
	 * @param curPageNum
	 * @return
	 * int 
	 * author luowei
	 */
	private static int getBeginIndex(int totalPageNum, int curPageNum, int perPageRecordNum) {
		//当前页码
		int curPage = PageUtil.getCurPage(totalPageNum, curPageNum);
		
		return perPageRecordNum * (curPage - 1);
	}
	
	/**
	 * 获取每页最后一个值的索引
	 * @param perPageRecordNum 每页记录数
	 * @param totalRecordNum
	 * @param beginIndex
	 * @return
	 * int 
	 * author luowei
	 */
	private static int getEndIndex(int perPageRecordNum, int totalRecordNum, int beginIndex) {
		int endIndex = beginIndex + perPageRecordNum;
		if (endIndex > totalRecordNum) {
			endIndex = totalRecordNum;
		}
		
		return endIndex;
	}
	
	/**
	 * 获取当前页码数
	 * @author luowei
	 * @param totalPageNum
	 * @param curPageNum
	 * @return
	 * int
	 */
	private static int getCurPage(int totalPageNum, int curPageNum) {
		if (curPageNum < 1) {
			return 1;
		}
		if (curPageNum > totalPageNum) {
			return totalPageNum;
		}
		return curPageNum;
	}
	
	/**
	 * 断言对象是否为空
	 * @param obj
	 * void 
	 * author luowei
	 */
	private static void assertNULL(Object obj) {
		if (obj == null) {
			throw new NullPointerException();
		}
	}
	
	/**
	 * 每页的开始索引是否是有效的范围
	 * @param beginIndex
	 * @param totalRecordNum
	 * @return
	 * boolean 
	 * author luowei
	 */
	private static boolean isValidBoundsOfBeginIndex(int beginIndex, int totalRecordNum) {
		if (beginIndex < 0 || beginIndex > totalRecordNum) {
			return true;
		}
		
		return false;
	}
}
