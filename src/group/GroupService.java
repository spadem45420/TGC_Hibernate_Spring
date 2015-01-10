package group;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import model.GroupRoom;
import model.GroupRoomDAO_Interface;
import model.Joiner_Info;
import model.Joiner_InfoDAO_Interface;
import model.Member;
import model.MemberDAO_Interface;
import model.StoreInformation;
import model.StoreInformationDAO_Interface;
import model.StoreMember;
import model.StoreMemberDAO_Interface;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GroupService {
	
//	public Joiner_InfoDAO_Interface getDao() {//未更新
//		return dao;
//	}
//
//	public void setDao(Joiner_InfoDAO_Interface dao) {//未更新
//		this.dao = dao;
//	}
	
	private GroupRoomDAO_Interface GRdao = null;
	private Joiner_InfoDAO_Interface JIdao = null;
	
	public GroupService(){
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"model-config1-DriverManagerDataSource.xml");
		GRdao = (GroupRoomDAO_Interface) context
				.getBean("GroupRoomDAO");
		JIdao = (Joiner_InfoDAO_Interface) context
				.getBean("Joiner_InfoDAO");
		
	}

	private static boolean timeComparer(Date date1,Date date2){//比較時間大小
		if(date1.after(date2)){
			return true;
		}
		return false;
	}
	
	private int RoomPeopleSum(GroupRoom bean){//算出該時間該家店的總人數
		
		Date start = bean.getReserveGroupStartTime();//預約開始時間
		Date end = bean.getReserveGroupEndTime();//預約結束時間
		int sum = 0;
		
		
		List<GroupRoom> list = GRdao.getAll();
		for(GroupRoom vo : list){//用foreach撈所有的房間
			Date s1 = vo.getReserveGroupStartTime();
			Date e1 = vo.getReserveGroupEndTime();
			if(GroupService.timeComparer(end, s1)){ 
				if(GroupService.timeComparer(e1, start)){
					int RoomNumber = vo.getGroupSerialNumber();//取出撈到的房間號碼
					sum = sum + JIdao.count(RoomNumber).size();
				}
			}
			
		}
		
		return sum;
	}
	
	private boolean MayICreate(GroupRoom bean){//是否可以開團
		GroupService GS = new GroupService();
		int RoomPeopleSum = 0;
		int StoreUpper = 0;
		String StoreName = bean.getStoreName();
		List<StoreInformation> list = JIdao.getStoreByName(StoreName);
		for(StoreInformation store : list){
			StoreUpper = store.getGroupUpperLimit();
			RoomPeopleSum = GS.RoomPeopleSum(bean);
			if(StoreUpper-RoomPeopleSum > 0){
				return true;
			}
		}
		return false;
	}
	
	private List<GroupRoom> createGroupRoom(GroupRoom bean){//創立房間
		GroupService GS = new GroupService();
		List<GroupRoom> room = new ArrayList<GroupRoom>();
		if(GS.MayICreate(bean)){
			GRdao.insert(bean);
			room.add(bean);
			return room;
		}
		return null;
	}
	
	private List<GroupRoom> updateGroupRoom(GroupRoom bean){//更新房間
		GroupRoom key = null;
		List<GroupRoom> room = new ArrayList<GroupRoom>();
		key = GRdao.findByPrimeKey(bean.getGroupSerialNumber());
		if(key != null){
			GRdao.update(bean);
			room.add(bean);
		}
		return null;
	}
	
	private List<GroupRoom> deleteGroupRoom(int groupSerialNumber){//刪除房間
		GroupService GS = new GroupService();
		GroupRoom room = GRdao.findByPrimeKey(groupSerialNumber);
		int state = -100;//初始值
		state = room.getRoomState();//取得房間狀態
		if(state==0){//如果為一般開團狀態
			GRdao.delete(groupSerialNumber);
		}
		else if(state==1){//如果為已開團成功狀態
			room.setRoomState(-1);
			GS.updateGroupRoom(room);
		}
		return null;
	}
	
	private boolean addGroupRoom(Joiner_Info bean){//加入房間
		int groupSerialNumber = bean.getGroupRoom().getGroupSerialNumber();//取得房間的號碼
		GroupRoom room = GRdao.findByPrimeKey(groupSerialNumber);//取得要加入的房間
		int sum = JIdao.count(groupSerialNumber).size();//取得目前房間人數
		int upper = room.getGroupUpperLimit();//取得房間上限人數
		
		if(sum < upper && bean != null){
			JIdao.insert(bean);
			return true;
		}
		return false;
	}
	
	private boolean exitGroupRoom(Joiner_Info bean){//退出房間
		int JoinerNumber = bean.getJoinerInfoSerialNumber();
		if(bean != null && JIdao.findByPrimeKey(JoinerNumber) != null){
			JIdao.delete(JoinerNumber);
			return true;
		}
		return false;
	}
	
	public static void main(String[] args){
		GroupService GroupService = new GroupService();
		
		//time test
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		try {
//			Date date1 = sdf.parse("2013-10-4 10:16:25");
//			Date date2 = sdf.parse("2013-10-4 10:15:25");
//			System.out.println(JoinerService.timeComparer(date1, date2));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//time test2
//		ApplicationContext context = new ClassPathXmlApplicationContext(
//				"model-config1-DriverManagerDataSource.xml");
//		GroupRoomDAO_Interface dao = (GroupRoomDAO_Interface) context
//				.getBean("GroupRoomDAO");
//		List<GroupRoom> list = dao.getAll();
//		for(GroupRoom vo : list){
//			Date start = vo.getReserveGroupStartTime();
//			Date end = vo.getReserveGroupEndTime();
//			System.out.println(JoinerService.timeComparer(end,start));
//		}
		
		//add test
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"model-config1-DriverManagerDataSource.xml");
		GroupRoomDAO_Interface dao = (GroupRoomDAO_Interface) context
				.getBean("GroupRoomDAO");
		// 新增
		GroupRoom bean1 = new GroupRoom();
		StoreMemberDAO_Interface smdao = (StoreMemberDAO_Interface) context
				.getBean("StoreMemberDAO");
		StoreMember smbean1 = smdao.findByPrimeKey(1);
		bean1.setStoreMember(smbean1);
		MemberDAO_Interface mdao = (MemberDAO_Interface) context
				.getBean("MemberDAO");
		Member mbean1 = mdao.findByPrimeKey(1);
		bean1.setMember(mbean1);
		StoreInformationDAO_Interface sidao = (StoreInformationDAO_Interface) context
				.getBean("StoreInformationDAO");
		StoreInformation sibean = sidao.findByPrimeKey(1);
		bean1.setGroupSerialNumber(1);//update、加入房間時打開
		bean1.setStoreName(sibean.getStoreName());
		bean1.setGroupStartTime(java.sql.Date.valueOf("2014-12-24"));
		bean1.setGroupEndTime(java.sql.Date.valueOf("2014-12-31"));
		bean1.setGroupRoomName("一起打桌遊八555!");
		bean1.setGroupSuggestNumber("6-15");
		bean1.setGroupLowerLimit(6);
		bean1.setGroupUpperLimit(15);
		bean1.setGroupGameTime(java.sql.Time.valueOf("03:00:00"));
		bean1.setReserveGroupStartTime(java.sql.Timestamp
				.valueOf("2015-1-1 11:00:00"));
		bean1.setReserveGroupEndTime(java.sql.Timestamp
				.valueOf("2015-1-1 12:00:00"));
		bean1.setRoomState(0);
//		bean1.setRoomState(1);//開團成功狀態
		String filename1 = "boardgames.jpg";
		bean1.setImgFileName(filename1);
		File f = new File("WebContent/res/" + bean1.getImgFileName());
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
			bean1.setPrivateGroupImage(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//roomPeopleSum test
//		System.out.println(GroupService.RoomPeopleSum(bean1));
		
		//MayIadd test
//		System.out.println(GroupService.MayICreate(bean1));
		
		//create GroupRoom
//		System.out.println(GroupService.createGroupRoom(bean1));
		
		//deleteGroupRoom
//		System.out.println(GroupService.deleteGroupRoom(6));
		
		//updateGroupRoom
//		dao.update(bean1);
		
		//addGroupRoom test
//		Joiner_Info bean2 = new Joiner_Info();
//		bean2.setUsername("GG");
//		bean2.setGroupRoom(bean1);
//		GroupService.addGroupRoom(bean2);
		
		//delete Joiner
//		Joiner_Info bean2 = new Joiner_Info();
//		bean2.setJoinerInfoSerialNumber(15);
//		System.out.println(GroupService.exitGroupRoom(bean2));
		
	}
}
