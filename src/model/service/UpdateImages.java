package model.service;

import java.io.*;
import java.util.*;

import model.BoardGames;
import model.Interface.BoardGamesDAO_Interface;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UpdateImages {
	public List<String> findImagesNameByPath(String filePath){//用路徑找檔案
		  List<String> list = new ArrayList<String>();
		  
		  File a = new File("WebContent/res/BoardGames");
	      String[] filenames;
	      String fullpath = a.getAbsolutePath();
	      
	      if(a.isDirectory()){
	        filenames=a.list();
	        for (int i = 0 ; i < filenames.length ; i++){         
	          File tempFile = new File(fullpath + "\\" + filenames[i]);
	          if(tempFile.isDirectory()){
	        	  System.out.println("目錄:" + filenames[i]);
	          }
	          else{
	        	  list.add(filenames[i]);
	        	  System.out.println("檔案:" + filenames[i]);
	          }
	        }
	        
	        return list;
	      }
	      else{
	    	  System.out.println("[" + a + "]不是目錄");
	      }
		return null;
	}
	
	
	public static List<String> findImagesName(){//逐筆塞入
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"model-config1-DriverManagerDataSource.xml");
		BoardGamesDAO_Interface dao = (BoardGamesDAO_Interface) context
				.getBean("BoardGamesDAO");
		List<BoardGames> list = dao.getAll();
		List<String> num = new ArrayList<String>();
		for(BoardGames games : list){
			String fileName = games.getImgFileName();
			File f = new File("WebContent/res/BoardGames/" + fileName);
			try {
				InputStream is = new FileInputStream(f);
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
				int nRead;
				byte[] data = new byte[1024];
				while ((nRead = is.read(data, 0, data.length)) != -1) {
					buffer.write(data, 0, nRead);
					buffer.flush();
				}
				data = buffer.toByteArray();
				is.close();
				games.setBoardGameImage(data);
			} catch (IOException e) {
//				e.printStackTrace();
//				System.out.println(i);
				num.add(games.getImgFileName());
			}
			dao.update(games);
		}
		return num;
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"model-config1-DriverManagerDataSource.xml");
		BoardGamesDAO_Interface dao = (BoardGamesDAO_Interface) context
				.getBean("BoardGamesDAO");
		
		UpdateImages img = new UpdateImages();
		//路徑找檔案測試
//		List<String> list = img.findImagesNameByPath("WebContent/res/BoardGames");
//		for(String name : list){
//			System.out.println(name);
//		}
		
		//findImagesName test
		List<String> list = UpdateImages.findImagesName();
		for(String n : list){
			System.out.println(n);
		}
	}
}
