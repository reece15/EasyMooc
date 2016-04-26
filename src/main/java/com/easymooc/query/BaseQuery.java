package com.easymooc.query;

import java.util.HashMap;
import java.util.Map;



/**
 * @filename BaseQuery.java
 * @author Reece
 * @description  
 * @version 
 */
public abstract class BaseQuery {
	
	//封装的要查询的where条件
	private Map<String, Object> queryMap = new HashMap<String,Object>();
	
	//当前页码
	private int currentPage = 1;
	//页大小
	private int pageSize = 10;
	
	public abstract HashMap<String, Object> buildWhere();

	public Map<String, Object> getQueryMap() {
		return queryMap;
	}

	public void setQueryMap(Map<String, Object> queryMap) {
		this.queryMap = queryMap;
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
	
	
}
