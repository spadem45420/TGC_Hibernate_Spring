package group;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

public class JoinerService {
	
	private static boolean timeComparer(Date date1,Date date2){
		if(date1.after(date2)){
			return true;
		}
		return false;
	}
	
	private List<GroupRoom> RoomPeopleSum(GroupRoom bean){
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"model-config1-DriverManagerDataSource.xml");
		GroupRoomDAO_Interface dao = (GroupRoomDAO_Interface) context
				.getBean("GroupRoomDAO");
		
		ApplicationContext context2 = new ClassPathXmlApplicationContext(
				"model-config1-DriverManagerDataSource.xml");
		Joiner_InfoDAO_Interface dao2 = (Joiner_InfoDAO_Interface) context
				.getBean("Joiner_InfoDAO");
		Date start = bean.getReserveGroupStartTime();//預約開始時間
		Date end = bean.getReserveGroupEndTime();//預約結束時間
		int sum = 0;
		
		
		List<GroupRoom> list = dao.getAll();
		for(GroupRoom vo : list){
			Date s1 = vo.getReserveGroupStartTime();
			Date e1 = vo.getReserveGroupEndTime();
			if(JoinerService.timeComparer(end, s1)){ 
				if(JoinerService.timeComparer(e1, start)){
					int RoomNumber = vo.getGroupSerialNumber();
					sum = sum + dao2.Count(RoomNumber).size();
				}
			}
			
		}
		
		System.out.println(sum);
		
		return null;
	}
	
	private List<GroupRoom> addGroupRoom(){
		return null;
	}
	
	private List<GroupRoom> updateGroupRoom(){
		return null;
	}
	
	private List<GroupRoom> deleteGroupRoom(){
		return null;
	}
	
	public static void main(String[] args){
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
		bean1.setStoreName(sibean.getStoreName());
		bean1.setGroupStartTime(java.sql.Date.valueOf("2014-12-24"));
		bean1.setGroupEndTime(java.sql.Date.valueOf("2014-12-31"));
		bean1.setGroupRoomName("一起打桌遊八3!");
		bean1.setGroupSuggestNumber("6-15");
		bean1.setGroupLowerLimit(6);
		bean1.setGroupUpperLimit(15);
		bean1.setGroupGameTime(java.sql.Time.valueOf("03:00:00"));
		bean1.setReserveGroupStartTime(java.sql.Timestamp
				.valueOf("2015-1-1 11:00:00"));
		bean1.setReserveGroupEndTime(java.sql.Timestamp
				.valueOf("2015-1-1 12:00:00"));
		bean1.setRoomState(0);
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
		JoinerService js = new JoinerService();
		js.RoomPeopleSum(bean1);
	}
}
