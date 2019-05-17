package com.scd.service;

import java.io.File;
import java.io.InputStream;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

/**
 * 
 * @author chengdu
 *
 */
@Service
public class MongoFileService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MongoFileService.class);
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	/**
     * 存储文件 
     * @param collectionName 集合名 
     * @param file 文件 
     * @param fileid 文件id 
     * @param companyid 文件的公司id 
     * @param filename 文件名称
     */
    public void saveFile(String collectionName, File file, String fileid, String companyid, String filename) {
        try {
            DB db = mongoTemplate.getDb();
            // 存储fs的根节点
            GridFS gridFS = new GridFS(db, collectionName);
            GridFSInputFile gfs = gridFS.createFile(file);
            gfs.put("aliases", companyid);
            gfs.put("fileid", fileid);
            gfs.put("filename", filename);
            gfs.put("contentType", filename.substring(filename.lastIndexOf(".")));
            gfs.save();
        } catch (Exception e) {
            LOGGER.error("save file {} to collection {} error", filename, collectionName);
        }
    }
    
    /**
     * 存储文件
     * @param collectionName
     * @param inputStream
     * @param fileid
     * @param aliasename
     * @param filename
     */
    public String saveFileByInputStream(String collectionName, InputStream inputStream, String fileid, String aliasename, String filename) {
    	String successobjId = "";
        try {
            DB db = mongoTemplate.getDb();
            // 存储fs的根节点
            GridFS gridFS = new GridFS(db, collectionName);
            GridFSInputFile gfs = gridFS.createFile(inputStream);
            gfs.put("aliases", aliasename);
            gfs.put("fileid", fileid);
            gfs.put("filename", filename);
            gfs.put("contentType", filename.substring(filename.lastIndexOf(".")));
            gfs.save();
            if(!StringUtils.isEmpty(gfs.getId())){
            	successobjId = gfs.getId().toString();
            }
        } catch (Exception e) {
            LOGGER.error("save file {} to collection {} error", filename, collectionName);
        }
        return successobjId;
    }
    
    public GridFSDBFile findFileById(String collectionName, String fileId){
    	DB db = mongoTemplate.getDb();
        // 获取fs的根节点
        GridFS gridFS = new GridFS(db, collectionName);
        DBObject query = new BasicDBObject();
		query.put("fileid", fileId);
        GridFSDBFile dbfile = gridFS.findOne(query);
        InputStream inputStream = dbfile.getInputStream();
        return dbfile;
    }

    // 取出文件
    public GridFSDBFile retrieveFileOne(String collectionName, String filename) {
        try {
            DB db = mongoTemplate.getDb();
            // 获取fs的根节点
            GridFS gridFS = new GridFS(db, collectionName);
            GridFSDBFile dbfile = gridFS.findOne(filename);
            if (dbfile != null) {
                return dbfile;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }
}
