package edu.tjpu.share.util;

import java.util.ArrayList;
import java.util.List;

public class PageModel<T> {
	private int nextPage = 1;
	private int currentPage = 1;
	private int previousPage = 1;
	private int firstPage = 1;
	private int lastPage = 1;
	private int totalPages = 1;
	private List<T> listPages = new ArrayList<T>();

	private int pageSize = 7;

	public PageModel(List<T> list, int currentPage) {

		if (list != null && list.size() > 0) {
			//获取所有的记录数
			int total = list.size();
			
			//如果总记录数是页数大小的整倍数，相除得总页数。如果比整倍数大，总页数加一页
			if (total % this.pageSize == 0) {
				this.totalPages = total / this.pageSize;
			} else {
				this.totalPages = total / this.pageSize + 1;
			}

			this.lastPage = this.totalPages;
			this.currentPage = currentPage;

			//如果当前页不是最后一页，下一页加一，如果是最后一页，下一页等于最后一页
			if (this.currentPage < this.lastPage) {
				this.nextPage = this.currentPage + 1;
			} else {
				this.nextPage = this.currentPage;
			}

			//如果当前页不是第一页，上一页一页加一，如果是第一页，上一页等于第一页
			if (this.currentPage > 1) {
				this.previousPage = this.currentPage - 1;
			} else {
				this.previousPage = this.currentPage;
			}

			//temp 减一为显示页面的最大编号
			int temp = this.currentPage * this.pageSize;
			
			for (int i = 0; i < total; i++) {
				//如果记录编号大于当前页面最小记录编号小于最大编号则加入新list中
				if (i >= temp - this.pageSize && i <= temp - 1) {
					T t = list.get(i);
					this.listPages.add(t);
				}
			}
		}
	}

	public int getNextPage() {
		return nextPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPreviousPage() {
		return previousPage;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public List<T> getListPages() {
		return listPages;
	}

}
