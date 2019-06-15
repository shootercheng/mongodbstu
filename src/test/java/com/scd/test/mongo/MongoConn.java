package com.scd.test.mongo;


import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class MongoConn {
	
	
	private static MongoClient mongoClient = null;
	
	public static void initMongo() throws UnknownHostException{
		MongoCredential credential = MongoCredential.createCredential("chengdu", "mongotest", "chengdu".toCharArray());
		List<MongoCredential> mongoCredentials = new ArrayList<>(1);
		mongoCredentials.add(credential);
		mongoClient = new MongoClient(new ServerAddress("127.0.0.1", 27017), mongoCredentials);
		System.out.println("Connect to database successfully");
		mongoClient.getUsedDatabases();
	}
	
	public static DB getDb(String dbName){
		return mongoClient.getDB(dbName);
	}
	
	public static Set<String> getColls(String dbname){
		return getDb(dbname).getCollectionNames();
	} 
	

	public static void main(String[] args) throws UnknownHostException {
		initMongo();
		System.out.println(mongoClient.getConnectPoint());
		DB db = getDb("mongotest");
		System.out.println(db.getCollectionNames());
		System.out.println(getColls("mongotest"));
	}

}
