package com.easymooc.query;

import java.util.ArrayList;
import java.util.List;

/**
 * @filename PageResult.java
 * @author Reece
 * @description  分页类
 * @version 1.0
 */
public class PageResult<T> {
	/**
	 * 当前页码
	 */
	private int currentPage;
	/**
	 * 页面大小
	 */
	private int pageSize;
	/**
	 * 总行数
	 */
	private int totalRows;
	/**
	 * 总页数
	 */
	private int totalPages;
	/**
	 * 每页的数据
	 */
	private List<T> datas = new ArrayList<T>();
	
	
	/**
	 * 分页
	 * @author Reece
	 * @description 分页
	 * @param currentPage 当前页码
	 * @param pageSize 页大小
	 * @param totalRows 最大数量
	 */
	public PageResult(int currentPage, int pageSize, int totalRows) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalRows = totalRows;
		
		//当前页码大于1
		this.currentPage = currentPage > 1 ? currentPage : 1;
		//页面大小大于1
		this.pageSize = pageSize > 1 ? pageSize : 1;
		//计算最页数
		this.totalPages = (totalRows + this.pageSize - 1) / this.pageSize;
		//当前页码小于总页数
		this.currentPage = Math.min(this.currentPage, this.totalPages);
		
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public List<T> getDatas() {
		return datas;
	}
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
	
	
}
