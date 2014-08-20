package com.shawn.sales.common;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {

	private static final long serialVersionUID = -2940983877096774934L;

	private List<T> list;

	private long pageIndex; // 当前页
	private long firstResult; // 当前页的起始记录
	private long totalResults; // 总共记录数
	private int pageSize; // 每页的数量
	private long totalPage; // 总共多少页
	private long nextPage; // 下一页
	private long previousPage; // 上一页

	public Page() {

	}

	public Page(long pageIndex, int pageSize) {
		if (pageIndex > 1)
			this.pageIndex = pageIndex;
		else
			this.pageIndex = 1;
		this.pageSize = pageSize;
		this.firstResult = (this.pageIndex - 1) * this.pageSize + 1;
	}

	public long getPageIndex() {
		return pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageIndex(long pageIndex) {
		this.pageIndex = pageIndex;
		if (pageIndex <= 0)
			this.pageIndex = 1;
		if (pageIndex > this.totalPage)
			this.pageIndex = totalPage;
		this.firstResult = (this.pageIndex - 1) * this.pageSize;

	}

	public long getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(long totalResults) {
		this.totalResults = totalResults;
		if (totalResults % this.pageSize == 0) {
			this.totalPage = totalResults / this.pageSize;
		} else {
			this.totalPage = (long) Math.floor(totalResults / this.pageSize) + 1;
		}

		if (this.totalPage == 0) {
			this.totalPage = 1;
		}
		if (this.pageIndex > totalPage) {
			this.pageIndex = totalPage;
			this.firstResult = (this.pageIndex - 1) * this.pageSize;

		}
		if (this.pageIndex > 1) {
			this.previousPage = this.pageIndex - 1;
		} else {
			this.previousPage = 1;
		}
	}

	public long getFirstResult() {
		return firstResult;
	}

	public long getNextPage() {
		if (this.pageIndex < this.totalPage) {
			this.nextPage = this.pageIndex + 1;
		} else {
			this.nextPage = this.totalPage;
		}
		return nextPage;
	}

	public long getPreviousPage() {
		return previousPage;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "Page [list=" + list + ", firstResult=" + firstResult + ", nextPage=" + nextPage + ", pageIndex=" + pageIndex + ", pageSize=" + pageSize + ", previousPage=" + previousPage + ", totalPage=" + totalPage + ", totalResults=" + totalResults + "]";
	}

}
