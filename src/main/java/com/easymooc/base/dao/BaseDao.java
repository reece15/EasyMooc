package com.easymooc.base.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import com.easymooc.query.BaseQuery;
import com.easymooc.query.PageResult;

/**
 * @filename BaseDao.java
 * @author Reece
 * @description  
 * @version 
 */
public interface BaseDao<T>{
	
	/**
	 * 保存
	 * @author Reece
	 * @description 保存
	 * @param t 要保存的实体
	 */
	public void saveEntity(T t);
	
	/**
	 * 更新数据
	 * @author Reece
	 * @description 更新数据
	 * @param t 要更新的对象
	 */
	public void updateEntity(T t);
	
	/**
	 * 根据id删除数据
	 * @author Reece
	 * @description 根据id删除数据
	 * @param id 行的id
	 */
	public void deleteEntityById(Serializable id);
	
	/**
	 * 根据多个id批量删除
	 * @author Reece
	 * @description 根据多个id批量删除 
	 * @param ids 多个id
	 */
	public void deleteEntitiesByIds(Serializable[] ids);
	
	/**
	 * 分页查询
	 * @author Reece
	 * @description 分页查询
	 * @param baseQuery 查询条件
	 * @return 分页后的结果集
	 */
	public PageResult<T> findPageResult(BaseQuery baseQuery);
	
	/**
	 * 不分页的查询
	 * @author Reece
	 * @description 不分页的查询
	 * @return 所有结果
	 */
	public Collection<T> findEntity();
	
	/**
	 * 根据id查询对象
	 * @author Reece
	 * @description  根据id查询对象
	 * @param id 要查询的对象的id
	 * @return 对象
	 */
	public T findEntityById(Serializable id);
	
	/**
	 * 得到数量
	 * @author Reece
	 * @description 得到数量
	 * @param baseQuery 查询条件
	 * @return 数量
	 */
	public int getCount(final BaseQuery baseQuery);
	/**
	 * 根据多个id查询
	 * @author Reece
	 * @description 根据多个id查询信息
	 * @param ids id数组
	 * @return 结果集合
	 */
	public Set<T> getEntitiesByIds(Serializable[] ids);
}
