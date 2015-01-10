package StoreMember;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.RentalTime;
import model.RentalTimeDAO_Interface;
import model.StoreInformation;
import model.StoreInformationDAO_Interface;
import model.StoreMember;
import model.StoreMemberDAO_Interface;
import model.dao.StoreMemberDAOHibernate;

public class StoreMemberService {
	
	StoreMemberDAO_Interface dao =null;
	StoreInformationDAO_Interface dao2 =null;
	RentalTimeDAO_Interface dao3 =null;
	
	public StoreMemberService(){
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"model-config1-DriverManagerDataSource.xml");
		dao = (StoreMemberDAO_Interface) context
				.getBean("StoreMemberDAO");
		dao2 = (StoreInformationDAO_Interface) context
				.getBean("StoreInformationDAO");
		dao3 = (RentalTimeDAO_Interface) context
				.getBean("RentalTimeDAO");
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
	
	
	private List<RentalTime> addStoreTime(RentalTime rbean){//新增店家時間
		int storeId = rbean.getStoreId();
		RentalTime time = dao3.findByPrimeKey(storeId);
		if(time != null){
			List<RentalTime> list = new ArrayList<RentalTime>();
			dao3.insert(rbean);
			list.add(rbean);
			return list;
		}
		return null;
	}
	
	
	public List<StoreInformation> addStore(StoreInformation sbean){//新增店家
		int storeMemberId = sbean.getStoreMember().getStoreMemberId();
		StoreMember member = dao.findByPrimeKey(storeMemberId);//確認店家是否存在
		if(member != null){//如果店家已存在
			return null;
		}
		if(sbean != null){
			List<StoreInformation> list = new ArrayList<StoreInformation>();
			dao2.insert(sbean);//新增店
			list.add(sbean);
			return list;
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
		
		StoreInformation store = new StoreInformation();//店家
		RentalTime time = new RentalTime();//店家時間
		
		//開店
		StoreMember storeMember = dao.findByPrimeKey(3);//取得要開店的店家會員
		store.setStoreMember(storeMember);
		store.setStoreName("卡牌屋-台北店");
		store.setStoreAddress("台北市開封街一段19號2樓");
		store.setStoreTel("(02)2311-2981");
		store.setRentAreaCost(110.0);
		store.setGroupUpperLimit(65);
		service.addStore(store);
		
		//設定營業時間
//		service.findStoreById(storeId);
		
		
		
	}
}
