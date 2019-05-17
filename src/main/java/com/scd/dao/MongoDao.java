package com.scd.dao;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.DB;
import com.mongodb.WriteResult;

/**
 * 
 * @author chengdu
 *
 */
@Repository
public class MongoDao {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public void test(){
		DB db = mongoTemplate.getDb();
		System.out.println(db);
	}
	
	public void insert(Object objectToSave, String collectionName){
		mongoTemplate.insert(objectToSave, collectionName);
	}
	
	public void insert(List<Object> batchToSave, String collectionName){
		mongoTemplate.insert(batchToSave, collectionName);
	}
	
	public WriteResult remove(Object object, String collectionName){
		WriteResult writeResult = mongoTemplate.remove(object, collectionName);
		return writeResult;
	}
	
	public WriteResult remove(String key, Object object, String collectionName){
		Query query = new Query(Criteria.where(key).is(object));
		WriteResult writeResult = mongoTemplate.remove(query, collectionName);
		return writeResult;
	}
	
	public WriteResult update(String key,Object object,
			String updateKey,Object updateObj,String collectionName){
		Query query = new Query(Criteria.where(key).is(object));
		Update update = new Update().set(updateKey, updateObj);
		WriteResult writeResult = mongoTemplate.updateFirst(query, update, collectionName);
		return writeResult;
	}
	
	public WriteResult updateMulti(String key,Object object,
			String updateKey,Object updateObj,String collectionName){
		Query query = new Query(Criteria.where(key).is(object));
		Update update = new Update().set(updateKey, updateObj);
		WriteResult writeResult = mongoTemplate.updateMulti(query, update, collectionName);
		return writeResult;
	}
	
	public <T> T findOne(String key, Object value, Class<T> entityClass, String collectionName){
		Query query = new Query(Criteria.where(key).is(value));
		return mongoTemplate.findOne(query, entityClass, collectionName);
	}
	
	public <T> List<T> find(String key, Object value, Class<T> entityClass, String collectionName){
		Query query = new Query(Criteria.where(key).is(value));
		return mongoTemplate.find(query, entityClass, collectionName);
	}
}
