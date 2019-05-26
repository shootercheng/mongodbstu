package com.scd.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FileTypeUtil {
	
	// 缓存文件头信息-文件头信息
		public static  Map<String, String> mFileTypes;
		static {
			Map<String, String> map = new HashMap<String, String>();
//			map.put("FFD8FF", "jpg");
//			map.put("89504E47", "png");
//			map.put("47494638", "gif");
//			map.put("49492A00", "tif");
//			map.put("424D", "bmp");
//			//
//			map.put("41433130", "dwg"); // CAD
//			map.put("38425053", "psd");
//			map.put("7B5C727466", "rtf"); // 日记本
//			map.put("3C3F786D6C", "xml");
//			map.put("68746D6C3E", "html");
//			map.put("44656C69766572792D646174653A", "eml"); // 邮件
//			map.put("D0CF11E0", "doc");
//			map.put("D0CF11E0", "xls");//excel2003版本文件
//			map.put("5374616E64617264204A", "mdb");
//			map.put("252150532D41646F6265", "ps");
//			map.put("255044462D312E", "pdf");
//			map.put("504B0304", "docx");
//			map.put("504B0304", "xlsx");//excel2007以上版本文件
//			map.put("52617221", "rar");
//			map.put("57415645", "wav");
//			map.put("41564920", "avi");
//			map.put("2E524D46", "rm");
//			map.put("000001BA", "mpg");
//			map.put("000001B3", "mpg");
//			map.put("6D6F6F76", "mov");
//			map.put("3026B2758E66CF11", "asf");
//			map.put("4D546864", "mid");
//			map.put("1F8B08", "gz");
			map.put("ffd8ffe000104a464946", "jpg"); //JPEG (jpg)     
			map.put("89504e470d0a1a0a0000", "png"); //PNG (png)     
			map.put("47494638396126026f01", "gif"); //GIF (gif)     
			map.put("49492a00227105008037", "tif"); //TIFF (tif)     
			map.put("424d228c010000000000", "bmp"); //16色位图(bmp)     
			map.put("424d8240090000000000", "bmp"); //24位位图(bmp)     
			map.put("424d8e1b030000000000", "bmp"); //256色位图(bmp)     
			map.put("41433130313500000000", "dwg"); //CAD (dwg)     
			map.put("3c21444f435459504520", "html"); //HTML (html)
			map.put("3c21646f637479706520", "htm"); //HTM (htm)
			map.put("48544d4c207b0d0a0942", "css"); //css
			map.put("696b2e71623d696b2e71", "js"); //js
			map.put("7b5c727466315c616e73", "rtf"); //Rich Text Format (rtf)     
			map.put("38425053000100000000", "psd"); //Photoshop (psd)     
			map.put("46726f6d3a203d3f6762", "eml"); //Email [Outlook Express 6] (eml)       
			map.put("d0cf11e0a1b11ae10000", "doc"); //MS Excel 注意：word、msi 和 excel的文件头一样     
			map.put("d0cf11e0a1b11ae10000", "vsd"); //Visio 绘图     
			map.put("5374616E64617264204A", "mdb"); //MS Access (mdb)      
			map.put("252150532D41646F6265", "ps");     
			map.put("255044462d312e350d0a", "pdf"); //Adobe Acrobat (pdf)   
			map.put("2e524d46000000120001", "rmvb"); //rmvb/rm相同  
			map.put("464c5601050000000900", "flv"); //flv与f4v相同  
			map.put("00000020667479706d70", "mp4"); 
			map.put("49443303000000002176", "mp3"); 
			map.put("000001ba210001000180", "mpg"); //     
			map.put("3026b2758e66cf11a6d9", "wmv"); //wmv与asf相同    
			map.put("52494646e27807005741", "wav"); //Wave (wav)  
			map.put("52494646d07d60074156", "avi");  
			map.put("4d546864000000060001", "mid"); //MIDI (mid)   
			map.put("504b0304140000000800", "zip");    
			map.put("526172211a0700cf9073", "rar"); 
			map.put("235468697320636f6e66", "ini");
			map.put("504b03040a0000000000", "jar");
			map.put("504b03040a0000080000", "jar"); 
			map.put("4d5a9000030000000400", "exe");//可执行文件
			map.put("3c25402070616765206c", "jsp");//jsp文件
			map.put("4d616e69666573742d56", "mf");//MF文件
			map.put("3c3f786d6c2076657273", "xml");//xml文件
			map.put("494e5345525420494e54", "sql");//xml文件
			map.put("7061636b616765207765", "java");//java文件
			map.put("406563686f206f66660d", "bat");//bat文件
			map.put("1f8b0800000000000000", "gz");//gz文件
			map.put("6c6f67346a2e726f6f74", "properties");//bat文件
			map.put("cafebabe0000002e0041", "class");//bat文件
			map.put("49545346030000006000", "chm");//bat文件
			map.put("04000000010000001300", "mxp");//bat文件
			map.put("504b0304140006000800", "docx");//docx文件, excel2007以上版本文件
			map.put("d0cf11e0a1b11ae10000", "wps");//WPS文字wps、表格et、演示dps都是一样的
			map.put("6431303a637265617465", "torrent");
			map.put("3c68746d6c20786d6c6e", "htm");//猎聘、智联简历。htm
			map.put("46726f6d3a3cd3c920cd", "mht");//51job简历。mht  
			map.put("6D6F6F76", "mov"); //Quicktime (mov)  
			map.put("FF575043", "wpd"); //WordPerfect (wpd)   
			map.put("CFAD12FEC5FD746F", "dbx"); //Outlook Express (dbx)     
			map.put("2142444E", "pst"); //Outlook (pst)      
			map.put("AC9EBD8F", "qdf"); //Quicken (qdf)     
			map.put("E3828596", "pwl"); //Windows Password (pwl)         
			map.put("2E7261FD", "ram"); //Real Audio (ram)   
			mFileTypes = Collections.unmodifiableMap(map);
		}
	 
		/**
		 * @author guoxk
		 *
		 * 方法描述：根据文件路径获取文件头信息
		 * @param filePath 文件路径
		 * @return 文件头信息
		 */
		public static String getFileType(String filePath) {
			return mFileTypes.get(getFileHeader(filePath));
		}
	 
		/**
		 * @author guoxk
		 *
		 * 方法描述：根据文件路径获取文件头信息
		 * @param filePath 文件路径
		 * @return 文件头信息
		 */
		public static String getFileHeader(String filePath) {
			FileInputStream is = null;
			String value = null;
			try {
				is = new FileInputStream(filePath);
				byte[] b = new byte[10];
				is.read(b, 0, b.length);
				value = bytesToHexString(b);
			} catch (Exception e) {
			} finally {
				if (null != is) {
					try {
						is.close();
					} catch (IOException e) {
					}
				}
			}
			System.out.println("file header---------:"+value);
			return value;
		}
	 
		/**
		 * @author guoxk
		 *
		 * 方法描述：将要读取文件头信息的文件的byte数组转换成string类型表示
		 * @param src 要读取文件头信息的文件的byte数组
		 * @return   文件头信息
		 */
		private static String bytesToHexString(byte[] src) {
			StringBuilder builder = new StringBuilder();
			if (src == null || src.length <= 0) {
				return null;
			}
			String hv;
			for (int i = 0; i < src.length; i++) {
				// 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
				hv = Integer.toHexString(src[i] & 0xFF);
				if (hv.length() < 2) {
					builder.append(0);
				}
				builder.append(hv);
			}
			return builder.toString();
		}
		/**
		 * @author guoxk
		 *
		 * 方法描述：测试
		 * @param args
		 * @throws Exception
		 */
		public static void main(String[] args) throws Exception {
			String basePath = "C:/Users/chengdu/Desktop/filetype";
			File file = new File(basePath);
			if(file.isDirectory()){
				File[] files = file.listFiles();
				for(File f : files){
					String filePath = f.getAbsolutePath();
					String fileType = getFileType(filePath);
					System.out.println("FilePath :"+filePath +",FileType :"+fileType);
				}
			}
		}
}
