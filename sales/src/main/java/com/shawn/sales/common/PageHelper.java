/**
 * 
 */
package com.shawn.sales.common;


public class PageHelper {
	
	public static long getStartPosition(int pageIndex, int pageSize) {
		return (pageIndex - 1) * pageSize;
	}
	
	public static long getTotalPageNumber(Long totalNums, Integer pageSize) {
		return (totalNums % pageSize == 0) ? totalNums / pageSize : totalNums /pageSize + 1;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Page returnNullPage(Long pageIndex, Integer pageSize, Long count){
		Page page = new Page(pageIndex, pageSize);
		page.setTotalResults(count);
		page.setList(null);
		return page;
	}
}
