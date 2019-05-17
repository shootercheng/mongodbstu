package com.scd.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.mongodb.gridfs.GridFSDBFile;
import com.scd.model.Project;
import com.scd.service.MongoFileService;
import com.scd.service.ProjectService;

/**
 * 
 * @author chengdu
 *
 */
@RestController
public class ProjectController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);
	
	private static final String COL_NAME = "project";
	
	private static final String FLE_COL_NAME = "files";
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private MongoFileService mongoFileService;
	
	@RequestMapping(value="/insert.do")
	public String insertOne(){
		Project project = new Project();
		project.setProjectId(UUID.randomUUID().toString());
		project.setProjectCode(UUID.randomUUID().toString());
		project.setProjectName("testName");
		project.setProjectStatus("1");
		projectService.insert(project, COL_NAME);
		return "OK";
	}
	
	@RequestMapping(value="/insertList.do")
	public String insertList(){
		List<Object> projects = new ArrayList<>(100);
		for(int i=0; i < 100; i++){
			Project project = new Project();
			project.setProjectId(UUID.randomUUID().toString());
			project.setProjectCode(UUID.randomUUID().toString());
			project.setProjectName("project"+i);
			project.setProjectStatus("1");
			projects.add(project);
		}
		projectService.insert(projects, COL_NAME);
		return "OK";
	}
	
	@RequestMapping(value="/findone.do")
	public Project findOne(String projectName){
		return projectService.findOne("projectName", projectName, Project.class, COL_NAME);
	}
	
	@RequestMapping(value="/findlist.do")
	public List<Project> findList(){
		return projectService.find("projectStatus", "1", Project.class, COL_NAME);
	}
	
	@RequestMapping(value="/fileupload.do", method = RequestMethod.POST)
	public Map<String, Object> uploadFileToMongo(HttpServletRequest request, String companyid){
		Map<String, Object> map = new HashMap<>();
       try{
    	   Assert.notNull(companyid, "param [companyid] is null");
	       CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
	               request.getSession().getServletContext());
	       if(multipartResolver.isMultipart(request))
	       {
	           MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
	          //获取multiRequest 中所有的文件名
	           Iterator<String> iter=multiRequest.getFileNames();
	           while(iter.hasNext())
	           {
	               //一次遍历所有文件
	               MultipartFile file=multiRequest.getFile(iter.next().toString());
	               if(file!=null)
	               {
	            	   String fileid = UUID.randomUUID().toString();
					   String objId = mongoFileService.saveFileByInputStream(FLE_COL_NAME, file.getInputStream(), fileid, 
								   companyid, file.getOriginalFilename());
					   if(! StringUtils.isEmpty(objId)){
						   map.put("fileid", fileid);
						   map.put("msg", HttpStatus.OK.name());
						   map.put("status", HttpStatus.OK);
					   }
					}
	           }
	       }
       }catch(IllegalArgumentException e){
    	   LOGGER.error("param error", e);
    	   map.put("msg", e.getMessage());
    	   map.put("status", HttpStatus.BAD_REQUEST);
       }catch(Exception e){
    	   LOGGER.error("upload mongo file error", e);
    	   map.put("msg", "upload mongo file error");
    	   map.put("status", HttpStatus.BAD_REQUEST);
       }
       return map; 
	}
	
	@RequestMapping(value="/downloadmongo.do", method = RequestMethod.GET)
	public void downLoad(HttpServletResponse response, String fileId){
		try{
			Assert.notNull(fileId, "param fileId is null");
			GridFSDBFile gridFSDBFile = mongoFileService.findFileById(FLE_COL_NAME, fileId);
			InputStream inputStream = gridFSDBFile.getInputStream();
			String filename = gridFSDBFile.getFilename();
			LOGGER.info("download mongo file {}", filename);
			outputFile(response, inputStream, filename);
		}catch(IllegalArgumentException e){
			e.printStackTrace();
		}catch(Exception e){
			LOGGER.error("download mongo file error {}", e);
		}
	}
	
	@RequestMapping(value="/downloadtest.do", method = RequestMethod.GET)
	public void downLoad(HttpServletResponse response){
		String path = "E:/Download/sbt-1.2.7.zip";
		File file = new File(path);
		if(file.exists() && file.isFile()){
			try{
				InputStream fInputStream = new FileInputStream(path);
				outputFile(response, fInputStream, file.getName());
			}catch(Exception e){
				LOGGER.error("down file error {}", path);
			}
		}else{
			LOGGER.error("file not exists {}", path);
		}
	}
	
	
	private void outputFile(HttpServletResponse response,InputStream inputStream, String filename) throws UnsupportedEncodingException{
		filename = filename.replace(" ", "_");
		filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型   
        response.setContentType("multipart/form-data");   
        //2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)   
        response.setHeader("Content-Disposition", "attachment;fileName="+filename);
        byte[] buffer = new byte[1024];
        BufferedInputStream bis = null;
        try{
	        bis = new BufferedInputStream(inputStream);
	        OutputStream os = response.getOutputStream();
	        int i = bis.read(buffer);
	        while (i != -1) {
	            os.write(buffer, 0, i);
	            i = bis.read(buffer);
	        }
        }catch(Exception e){
        	e.printStackTrace();
        }finally {
			if(bis != null){
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    }
	
	@RequestMapping(value="/image.do", method = RequestMethod.GET, 
			produces = {MediaType.IMAGE_PNG_VALUE})
	public void getImage(String fileId, HttpServletResponse response){
		Assert.notNull(fileId, "param fileId is null");
		GridFSDBFile gridFSDBFile = mongoFileService.findFileById(FLE_COL_NAME, fileId);
		InputStream inputStream = gridFSDBFile.getInputStream();
		byte[] buffer = new byte[1024];
        BufferedInputStream bis = null;
        try{
	        bis = new BufferedInputStream(inputStream);
	        OutputStream os = response.getOutputStream();
	        int i = bis.read(buffer);
	        while (i != -1) {
	            os.write(buffer, 0, i);
	            i = bis.read(buffer);
	        }
        }catch(Exception e){
        	e.printStackTrace();
        }finally {
			if(bis != null){
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
