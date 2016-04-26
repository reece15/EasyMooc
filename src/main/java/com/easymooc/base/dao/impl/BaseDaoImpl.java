package com.easymooc.base.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;


import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.easymooc.base.dao.BaseDao;
import com.easymooc.query.BaseQuery;
import com.easymooc.query.PageResult;


/**
 * @filename BaseDaoImpl.java
 * @author Reece
 * @description  
 * @version 
 */
public class BaseDaoImpl<T> implements BaseDao<T>{

	//得到泛型
	private Class clazz;
	
	/**
	 * Hibernate的元数据 描述持久化类的属性
	 */
	private ClassMetadata classMetadata;
	
	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	
	public BaseDaoImpl() {
		//返回表示此 Class 所表示的实体(类、接口、基本类型或 void)的直接超类的 Type 然后将其转换ParameterizedType
		ParameterizedType pType = (ParameterizedType) this.getClass().getGenericSuperclass();
		this.clazz = (Class) pType.getActualTypeArguments()[0];
	}
	
	/**
	 * 初始化方法
	 * @author Reece
	 * @description 初始化方法 hibernate注入后执行
	 */
	@PostConstruct
	public void init(){
		this.classMetadata = this.hibernateTemplate.getSessionFactory()
				.getClassMetadata(this.clazz);
	}
	
	@Override
	public void saveEntity(T t) {
		hibernateTemplate.save(t);
	}

	@Override
	public void updateEntity(T t) {
		hibernateTemplate.update(t);
	}

	@Override
	public void deleteEntityById(Serializable id) {
		T t = (T) this.hibernateTemplate.get(clazz, id);
		this.hibernateTemplate.delete(t);
	}

	@Override
	public void deleteEntitiesByIds(Serializable[] ids) {
		StringBuffer query = new StringBuffer();
		query.append("from " + this.clazz.getSimpleName());
		query.append(" where " + this.classMetadata.getIdentifierPropertyName());
		
		StringBuffer temp = new StringBuffer();
		
		for (int i = 0; i < ids.length; i++) {
			temp.append(ids[i]);
			
			if(i != ids.length - 1){
				temp.append(",");
			}
		}
		
		query.append("in (" + temp + ")");
		//得到结果
		List<T> list = this.hibernateTemplate.find(query.toString());
		this.hibernateTemplate.deleteAll(list);
		
	}

	@Override
	public PageResult<T> findPageResult(final BaseQuery baseQuery) {
		return this.hibernateTemplate.execute(new HibernateCallback<PageResult<T>>() {

			@Override
			public PageResult<T> doInHibernate(Session session) throws HibernateException,
					SQLException {
				
				StringBuffer query = new StringBuffer();
				query.append(" from "+clazz.getSimpleName());
				query.append(" where 1=1 ");
				
				Map<String, Object> queryMap = baseQuery.buildWhere();
				for(Entry<String, Object> entry : queryMap.entrySet()){
					if(entry.getKey().contains(".")){						
						query.append(" and " + entry.getKey() + "=:" + entry.getKey().split("\\.")[1]);
					}else {
						query.append(" and " + entry.getKey() + "=:" + entry.getKey());
					}
				}
				
				Query querys = session.createQuery(query.toString());
				for (Entry<String, Object> entry : queryMap.entrySet()) {
					if(entry.getKey().contains(".")){
						querys.setParameter(entry.getKey().split("\\.")[1], entry.getValue());
					}else {
						querys.setParameter(entry.getKey(), entry.getValue());
					}
				}
				
				//当前页开始行
				int firstRow = (baseQuery.getCurrentPage() - 1) * baseQuery.getPageSize();
				//最多取几条
				int maxRow = baseQuery.getPageSize();
				//设置查询条件
				querys.setFirstResult(firstRow).setMaxResults(maxRow);
				//得到结果
				List<T> list = querys.list();
				PageResult<T> pageResult = new PageResult<T>(baseQuery.getCurrentPage(), baseQuery.getPageSize(), getCount(baseQuery));
				pageResult.setDatas(list);
				
				return pageResult;
			}
		});
	}

	@Override
	public Collection<T> findEntity() {
		return this.hibernateTemplate.find("from " + this.clazz.getSimpleName());
	}

	@Override
	public T findEntityById(Serializable id) {
		return (T) this.hibernateTemplate.get(this.clazz, id);
	}

	@Override
	public int getCount(final BaseQuery baseQuery) {
		return this.hibernateTemplate.execute(new HibernateCallback<Integer>() {

			@Override
			public Integer doInHibernate(Session session) throws HibernateException,
					SQLException {
				StringBuffer query = new StringBuffer();
				
				query.append("select count(" + classMetadata.getIdentifierPropertyName() + ") from " + clazz.getSimpleName());
				query.append(" where 1=1 ");
				
				Map<String, Object> queryMap = baseQuery.buildWhere();
				
				for(Entry<String, Object> entry : queryMap.entrySet()){
					if(entry.getKey().contains(".")){
						query.append(" and " + entry.getKey() + "=:" + entry.getKey().split("\\.")[1]);
					}else {
						query.append(" and " + entry.getKey() + "=:" + entry.getKey());
					}
				}
				
				Query querys = session.createQuery(query.toString());
				for(Entry<String, Object> entry : queryMap.entrySet()){
					if(entry.getKey().contains(".")){
						querys.setParameter(entry.getKey().split("\\.")[1], entry.getValue());
					}else {
						querys.setParameter(entry.getKey(), entry.getValue());
					}
				}
				
				Long count = (Long)querys.uniqueResult();
				return count.intValue();
			}
		});
	}

	@Override
	public Set<T> getEntitiesByIds(Serializable[] ids) {
		//hql语句
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(" from " + this.clazz.getSimpleName());
		stringBuffer.append(" where " + this.classMetadata.getIdentifierPropertyName());
		//查询条件
		StringBuffer tempBuffer = new StringBuffer();
		for (int i = 0; i < ids.length; i++) {
			tempBuffer.append(ids[i]);
			if(i != ids.length - 1){
				tempBuffer.append(ids[i] + ",");
			}
		}
		//拼接语句
		stringBuffer.append(" in (" + tempBuffer.toString() + ")");
		//查找到的实体集
		List<T> list = this.hibernateTemplate.find(stringBuffer.toString());		
		return new HashSet<T>(list);
	}

}
