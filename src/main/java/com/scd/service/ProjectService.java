package com.scd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.WriteResult;
import com.scd.dao.MongoDao;

/**
 * 
 * @author chengdu
 *
 */
@Service
public class ProjectService {
	
	@Autowired
	private MongoDao mongoDao;
	
	public void insert(Object objectToSave, String collectionName){
		mongoDao.insert(objectToSave, collectionName);
	}
	
	public void insert(List<Object> batchToSave, String collectionName){
		mongoDao.insert(batchToSave, collectionName);
	}
	
	public WriteResult remove(Object object, String collectionName){
		WriteResult writeResult = mongoDao.remove(object, collectionName);
		return writeResult;
	}
	
	public WriteResult remove(String key, Object object, String collectionName){
		Query query = new Query(Criteria.where(key).is(object));
		WriteResult writeResult = mongoDao.remove(query, collectionName);
		return writeResult;
	}
	
	public WriteResult update(String key,Object object,
			String updateKey,Object updateObj,String collectionName){
		WriteResult writeResult = mongoDao.update(key, object, 
				updateKey, updateObj, collectionName);
		return writeResult;
	}
	
	public WriteResult updateMulti(String key,Object object,
			String updateKey,Object updateObj,String collectionName){
		WriteResult writeResult = mongoDao.updateMulti(key, object, updateKey, 
				updateObj, collectionName);
		return writeResult;
	}
	
	public <T> T findOne(String key, Object value, Class<T> entityClass, String collectionName){
		return mongoDao.findOne(key, value, entityClass, collectionName);
	}
	
	public <T> List<T> find(String key, Object value, Class<T> entityClass, String collectionName){
		return mongoDao.find(key, value, entityClass, collectionName);
	}
}
