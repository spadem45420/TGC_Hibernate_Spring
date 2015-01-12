package model.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.BoardGames;
import model.RentalTime;
import model.StoreInformation;
import model.StoreMember;
import model.StoreScore;
import model.Interface.*;
import model.dao.StoreMemberDAOHibernate;

public class StoreMemberService {
	
	StoreMemberDAO_Interface dao =null;
	StoreInformationDAO_Interface dao2 =null;
	RentalTimeDAO_Interface dao3 =null;
	StoreScoreDAO_Interface dao4 = null;
	
	public StoreMemberService(){
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"model-config1-DriverManagerDataSource.xml");
		dao = (StoreMemberDAO_Interface) context
				.getBean("StoreMemberDAO");
		dao2 = (StoreInformationDAO_Interface) context
				.getBean("StoreInformationDAO");
		dao3 = (RentalTimeDAO_Interface) context
				.getBean("RentalTimeDAO");
		dao4 = (StoreScoreDAO_Interface) context
				.getBean("StoreScoreDAO");
	}
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");//時間格式
	
	public java.util.Date convertDate(String data) {//String 轉 util.Date
		java.util.Date result = null;
		try {
			result = sdf.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
			result = new java.util.Date(0);
		}
		return result;
	}

	public List<StoreMember> findByUsername(StoreMember bean) {//查詢店家會員By StoreUsername
		List<StoreMember> result = null;
		if (bean != null && bean.getStoreUsername() != null) {
			List<StoreMember> temp = dao.findByUsername(bean.getStoreUsername());
			if (temp != null) {
				result = temp;
			}
		} else {
			return null;
		}
		return result;
	}
	
	public StoreMember findByStoreMemberId(Integer storeMemberId){//查詢店家會員By StoreMemberId
		if(storeMemberId > 0){
			StoreMember storeMember = dao.findByPrimeKey(storeMemberId);
			return storeMember;
		}
		return null;
	}
	
	public StoreInformation findStoreById(Integer storeId){//查詢專賣店By storeId
		StoreInformation store = dao2.findByPrimeKey(storeId);
		if(store != null){
			return store;
		}
		return null;
	}
	
	public List<StoreInformation> findStoreByStoreName(String storeName){//查詢專賣店By storeName
		List<StoreInformation> list = dao2.findByStoreName(storeName);
		return list;
	}

	public List<StoreMember> register(StoreMember bean) {//註冊店家會員
		List<StoreMember> list = new ArrayList<StoreMember>();
		if (bean != null) {
			dao.insert(bean);
			list.add(bean);
			return list;
		}
		return null;
	}
	
	public List<StoreMember> update(StoreMember bean) {//更新店家會員
		StoreMember key = dao.findByPrimeKey(bean.getStoreMemberId());
		if (key != null) {
			List<StoreMember> list = new ArrayList<StoreMember>();
			dao.update(bean);
			list.add(bean);
			return list;
		}
		return null;
	}
	
	public boolean deleteStoreMember(Integer storeMemberId){//刪除店家會員
		StoreMember storeMember = dao.findByPrimeKey(storeMemberId);
		if(storeMember != null){
			dao.delete(storeMemberId);
			return true;
		}
		return false;
	}
	
	public List<StoreInformation> addStore(StoreInformation sbean){//新增店家資料
//		int storeMemberId = sbean.getStoreMember().getStoreMemberId();
//		StoreMember member = dao.findByPrimeKey(storeMemberId);//確認店家是否存在
//		if(member != null){//如果店家已存在
//			return null;
//		}
		if(sbean != null){
			List<StoreInformation> list = new ArrayList<StoreInformation>();
			dao2.insert(sbean);//新增店
			list.add(sbean);
			return list;
		}
		return null;
	}
	
	private List<RentalTime> addStoreTime(RentalTime rbean){//新增店家時間
		int storeId = rbean.getStoreInformation().getStoreId();
		if(rbean != null && storeId > 0){
			List<RentalTime> list = new ArrayList<RentalTime>();
			dao3.insert(rbean);
			list.add(rbean);
			return list;
		}
		return null;
	}
	
	public List<StoreInformation> updateStore(StoreInformation sbean){//更新店家資料
		int storeId = sbean.getStoreId();
		if(sbean != null && storeId > 0){
			List<StoreInformation> list = new ArrayList<StoreInformation>();
			dao2.update(sbean);
			list.add(sbean);
			return list;
		}
		return null;
	}
	
	public List<RentalTime> updateStoreTime(RentalTime rbean){//更新店家時間
		int storeId = rbean.getStoreInformation().getStoreId();
		if(rbean != null && storeId > 0){
			List<RentalTime> list = new ArrayList<RentalTime>();
			dao3.insert(rbean);
			list.add(rbean);
			return list;
		}
		return null;
	}
	
	public boolean deleteStore(int storeId){//刪除專賣店
		StoreMemberService service = new StoreMemberService();
		StoreInformation store = dao2.findByPrimeKey(storeId);//專賣店是否存在
		if(store != null){
			if(service.deleteStoreTime(storeId)){
				dao2.delete(storeId);
				return true;
			}
		}
		return false;
	}
	
	private boolean deleteStoreTime(int storeId){//刪除店家時間
		RentalTime time = dao3.findByPrimeKey(storeId);//營業時間是否存在
		if(time != null){
			dao3.delete(storeId);
			return true;
		}
		return false;
	}
	
	public boolean addStoreImage(){//新增專賣店圖片
		
		return false;
	}
	
	public boolean addStoreBoardGame(){//新增專賣店桌遊
		
		return false;
	}
	
	public List<BoardGames> findGamesByStoreId(int storeId){
		
		return null;
	}
	
	public List<StoreScore> findStoreScore(int storeId){//查詢評分By storeId
		StoreInformation store = dao2.findByPrimeKey(storeId);//專賣店是否存在
		if(store != null){
			List<StoreScore> score = dao4.findByStoreId(storeId);
			return score;
		}
		return null;
	}
	
	public static void main(String[] args) {
		StoreMemberService service = new StoreMemberService();

		// Insert
//		StoreMember bean1 = new StoreMember();
//		try {
//			File f = new File("WebContent/res/boardgames.jpg");
//			long size = 0;
//			InputStream is = null;
//			try {
//			size = f.length();
//			is = new FileInputStream(f);
//			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//			int nRead;
//			byte[] data = new byte[1024];
//			while ((nRead = is.read(data, 0, data.length)) != -1) {
//			  buffer.write(data, 0, nRead);
//			  buffer.flush();
//			}
//			data = buffer.toByteArray();
//			is.close();
//			bean1.setStoreImage(data);
//			} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		bean1.setStoreUsername("littleworld");
//		bean1.setStorePswd("Zz#littleworld".getBytes());
//		bean1.setStoreJoinDate(java.sql.Date.valueOf("2007-6-13"));
//		bean1.setStorePhone("0920949521");
//		bean1.setImgFileName("boardgames.jpg");
//		bean1.setStoreEmail("littleworld@gmail.com");
//		bean1.setStoreWebsite("www.littleworld.com");
//		service.register(bean1);
		
		// Update
//		StoreMember bean1 = new StoreMember();
//		try {
//			File f = new File("WebContent/res/boardgames.jpg");
//			long size = 0;
//			InputStream is = null;
//			try {
//			size = f.length();
//			is = new FileInputStream(f);
//			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//			int nRead;
//			byte[] data = new byte[1024];
//			while ((nRead = is.read(data, 0, data.length)) != -1) {
//			  buffer.write(data, 0, nRead);
//			  buffer.flush();
//			}
//			data = buffer.toByteArray();
//			is.close();
//			bean1.setStoreImage(data);
//			} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		bean1.setStoreMemberId(9);;
//		bean1.setStoreUsername("littleworld");
//		bean1.setStorePswd("Zz#littleworld".getBytes());
//		bean1.setStoreJoinDate(java.sql.Date.valueOf("2007-6-14"));
//		bean1.setStorePhone("0920949521");
//		bean1.setImgFileName("boardgames.jpg");
//		bean1.setStoreEmail("littleworld@gmail.com");
//		bean1.setStoreWebsite("www.littleworld.com");
//		service.update(bean1);
		
		
		
		// Select By Username
//		StoreMember bean2 = new StoreMember();
//		bean2.setStoreUsername("littleworld");
//		List<StoreMember> list = service.findByUsername(bean2);
//		for(StoreMember vo : list){
//			System.out.println(vo.getStoreUsername());
//		}
		
		
		//addStore test
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"model-config1-DriverManagerDataSource.xml");
		StoreMemberDAO_Interface dao = (StoreMemberDAO_Interface) context
				.getBean("StoreMemberDAO");
		StoreInformationDAO_Interface dao2 = (StoreInformationDAO_Interface) context
				.getBean("StoreInformationDAO");
		RentalTimeDAO_Interface dao3 = (RentalTimeDAO_Interface) context
				.getBean("RentalTimeDAO");
		StoreScoreDAO_Interface dao4 = (StoreScoreDAO_Interface) context
				.getBean("StoreScoreDAO");
		
		StoreInformation store = new StoreInformation();//店家
		
		//開店
//		StoreMember storeMember = dao.findByPrimeKey(3);//取得要開店的店家會員
//		store.setStoreMember(storeMember);
////		store.setStoreId(13);//更新店家資料
		store.setStoreName("卡牌屋-嘉義店");
//		store.setStoreAddress("嘉義市開封街一段19號2樓");
//		store.setStoreTel("(08)2311-2981");
//		store.setRentAreaCost(110.0);
//		store.setGroupUpperLimit(68);
//		service.addStore(store);//新增店家資料
////		service.updateStore(store);//更新店家資料
		//設定營業時間
//		List<StoreInformation> list =
//				service.findStoreByStoreName(store.getStoreName());
//		for(StoreInformation bean : list){
//			RentalTime time = new RentalTime();
//			time.setStoreInformation(bean);
////			time.setStoreId(bean.getStoreId());//更新營業時間
//			time.setMonStart(service.convertDate("13:00"));
//			time.setMonEnd(service.convertDate("21:30"));
//			time.setTueStart(service.convertDate("13:00"));
//			time.setTueEnd(service.convertDate("21:30"));
//			time.setWedStart(service.convertDate("13:00"));
//			time.setWedEnd(service.convertDate("21:30"));
//			time.setThuStart(service.convertDate("13:00"));
//			time.setThuEnd(service.convertDate("21:30"));
//			time.setFriStart(service.convertDate("13:00"));
//			time.setFriEnd(service.convertDate("21:30"));
//			time.setSatStart(service.convertDate("13:00"));
//			time.setSatEnd(service.convertDate("21:30"));
//			time.setSunStart(service.convertDate("13:00"));
//			time.setSunEnd(service.convertDate("21:30"));
//			service.addStoreTime(time);
//		}
		
		//刪除專賣店
//		service.deleteStore(13);
		
		//查詢專賣店評分
		
	}
}
