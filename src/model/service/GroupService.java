package model.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.GroupRoom;
import model.Joiner_Info;
import model.Member;
import model.StoreInformation;
import model.StoreMember;
import model.Interface.GroupRoomDAO_Interface;
import model.Interface.Joiner_InfoDAO_Interface;
import model.Interface.MemberDAO_Interface;
import model.Interface.StoreInformationDAO_Interface;
import model.Interface.StoreMemberDAO_Interface;

public class GroupService {
	private MemberDAO_Interface mdao;
	private StoreMemberDAO_Interface smdao;
	private StoreInformationDAO_Interface sidao;
	private GroupRoomDAO_Interface grdao;
	private Joiner_InfoDAO_Interface jidao;

	private int roomMaxNumber = 0;
	private int thisTimeJoinedNumber = 0;
	private int leftNumber = 0;

	public GroupService() {// 建構子
							// (初始化所使用到DAO-StoreInformationDAO_Interface，GroupRoomDAO_Interface，Joiner_InfoDAO_Interface)
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"model-config1-DriverManagerDataSource.xml");
		// ApplicationContext context = new ClassPathXmlApplicationContext(
		// "model-config2-JndiObjectFactoryBean.xml");
		mdao = (MemberDAO_Interface) context.getBean("MemberDAO");
		smdao = (StoreMemberDAO_Interface) context.getBean("StoreMemberDAO");
		sidao = (StoreInformationDAO_Interface) context
				.getBean("StoreInformationDAO");
		grdao = (GroupRoomDAO_Interface) context.getBean("GroupRoomDAO");
		jidao = (Joiner_InfoDAO_Interface) context.getBean("Joiner_InfoDAO");
	}

	public boolean addGroupRoom(GroupRoom bean) {// 開創揪團房
		if (this.canIAddGroupRoom(bean)) {
			leftNumber = roomMaxNumber - thisTimeJoinedNumber;
			if (bean.getGroupUpperLimit() > leftNumber) {
				bean.setGroupUpperLimit(leftNumber);
			}
			grdao.insert(bean);
			return true;
		}
		return false;
	}

	public boolean updateGroupRoom(GroupRoom bean) {// 更新揪團房資料
		// 先把舊的資料撈出來
		GroupRoom origin = grdao.findByPrimeKey(bean.getGroupSerialNumber());
		// 代表資料庫有資料，才修改不能新增
		if (origin != null) {
			// 不得修改的欄位直接用舊的資料
			// 不得為空值的欄位，如果沒有收到輸入值，就塞回舊資料
			if (bean.getStoreMember() == null) {
				bean.setStoreMember(origin.getStoreMember());
			}
			if (bean.getStoreName() == null) {
				bean.setStoreName(origin.getStoreName());
			}
			if (bean.getMember() == null) {
				bean.setMember(origin.getMember());
			}
			if (bean.getGroupStartTime() == null) {
				bean.setGroupStartTime(origin.getGroupStartTime());
			}
			if (bean.getGroupEndTime() == null) {
				bean.setGroupEndTime(origin.getGroupEndTime());
			}
			if (bean.getGroupRoomName() == null) {
				bean.setGroupRoomName(origin.getGroupRoomName());
			}
			if (bean.getGroupSuggestNumber() == null) {
				bean.setGroupSuggestNumber(origin.getGroupSuggestNumber());
			}
			if (bean.getGroupLowerLimit() == null) {
				bean.setGroupLowerLimit(origin.getGroupLowerLimit());
			}
			if (bean.getGroupUpperLimit() == null) {
				bean.setGroupUpperLimit(origin.getGroupUpperLimit());
			}
			if (bean.getGroupGameTime() == null) {
				bean.setGroupGameTime(origin.getGroupGameTime());
			}
			if (bean.getReserveGroupStartTime() == null) {
				bean.setReserveGroupStartTime(origin.getReserveGroupStartTime());
			}
			if (bean.getReserveGroupEndTime() == null) {
				bean.setReserveGroupEndTime(origin.getReserveGroupEndTime());
			}
			if (bean.getRoomState() == null) {
				bean.setRoomState(origin.getRoomState());
			}
			return true;
		}
		return false;
	}

	public boolean deleteGroupRoom(Integer groupSerialNumber) {// 刪除揪團房
		GroupRoom bean = grdao.findByPrimeKey(groupSerialNumber);
		if (bean.getRoomState() == 0) {
			grdao.delete(groupSerialNumber);
			return true;
		}
		return false;
	}

	public GroupRoom getOneGroupRoom(Integer groupSerialNumber) {// 查詢單筆揪團房(主鍵-groupSerialNumber)
		return grdao.findByPrimeKey(groupSerialNumber);
	}

	public List<GroupRoom> getOneGroupRoom(GroupRoom bean) {// 查詢單筆揪團房(非主鍵(傳bean)-groupRoomName)
		return grdao.findByGroupRoomName(bean.getGroupRoomName());
	}

	public List<GroupRoom> getOneGroupRoom(String unknown) {// 模糊查詢揪團房(非主鍵-unknown)
		List<GroupRoom> list = grdao.findByUnknown(unknown);
		return list;
	}

	public List<GroupRoom> getAll() {// 查全部
		return grdao.getAll();
	}

	private int countJoinedMemberNumber(GroupRoom bean) {// 查衝團(有交集的團)目前已加入的人數總合
		int result = 0;
		java.util.Date inputTimeEnd = bean.getReserveGroupEndTime();
		java.util.Date inputTimeStart = bean.getReserveGroupStartTime();
		// 撈房間資料的時間來比對，找出有交集的團，並回報目前有交集的團(已加入的人數總合)
		List<GroupRoom> data = grdao.getAll();
		for (GroupRoom vo : data) {
			java.util.Date outputStartTime = vo.getReserveGroupStartTime();
			java.util.Date outputEndTime = vo.getReserveGroupEndTime();
			if (outputStartTime.before(inputTimeEnd)) {
				if (outputEndTime.after(inputTimeStart)) {
					List<Joiner_Info> list = jidao.findByGroupSerialNumber(vo
							.getGroupSerialNumber());
					int joinedNumber = list.size();
					result = result + joinedNumber;
				}
			}
		}
		return result;
	}

	private boolean canIAddGroupRoom(GroupRoom bean) {// 驗證創團條件
		// 檢查 店家，會員，專賣店三者是否存在於資料庫
		StoreMember storeMember = smdao.findByPrimeKey(bean.getStoreMember()
				.getStoreMemberId());
		Member member = mdao.findByPrimeKey(bean.getMember().getMemberId());
		List<StoreInformation> list = sidao
				.findByStoreName(bean.getStoreName());
		if (storeMember != null && member != null && list != null) {
			for (StoreInformation vo : list) {
				roomMaxNumber = vo.getGroupUpperLimit();
			}
			thisTimeJoinedNumber = this.countJoinedMemberNumber(bean);
			if (roomMaxNumber - thisTimeJoinedNumber >= 0) {
				return true;
			}
		}
		return false;
	}

	public boolean iWantToJoinThisGroup(Joiner_Info bean) {// 加團
		List<Joiner_Info> list = jidao.findByGroupSerialNumber(bean
				.getGroupRoom().getGroupSerialNumber());
		int thisRoomJoinedNumber = list.size();
		GroupRoom groupRoom = grdao.findByPrimeKey(bean.getGroupRoom()
				.getGroupSerialNumber());
		List<Member> member = mdao.findByUsername(bean.getUsername());
		if (member != null) {
			int thisRoomMaxLimit = groupRoom.getGroupUpperLimit();
			if (thisRoomJoinedNumber + 1 <= thisRoomMaxLimit) {
				jidao.insert(bean);
				return true;
			}
		}
		return false;
	}

	public boolean iWantToLeaveThisGroup(Joiner_Info bean) {// 離團
		Joiner_Info jInfo = jidao.findByPrimeKey(bean
				.getJoinerInfoSerialNumber());
		if (jInfo != null) {
			jidao.delete(jInfo.getJoinerInfoSerialNumber());
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		GroupService service = new GroupService();
		// 創團測試
		// StoreMember sMember = new StoreMember();
		// sMember.setStoreMemberId(1);// 假想我找到1號店家
		// Member member = new Member();
		// member.setMemberId(1);// 假想我是1號會員，我想開團
		// GroupRoom groupRoom_Create = new GroupRoom();
		// groupRoom_Create.setStoreMember(sMember);
		// groupRoom_Create.setMember(member);
		// groupRoom_Create.setStoreName("瘋桌遊-益智遊戲專賣店(汐止店)");
		// groupRoom_Create.setGroupStartTime(java.sql.Date.valueOf("2014-12-24"));
		// groupRoom_Create.setGroupEndTime(java.sql.Date.valueOf("2014-12-31"));
		// groupRoom_Create.setGroupRoomName("一起打桌遊八!");
		// groupRoom_Create.setGroupSuggestNumber("6-15");
		// groupRoom_Create.setGroupLowerLimit(6);
		// groupRoom_Create.setGroupUpperLimit(15);
		// groupRoom_Create.setGroupGameTime(java.sql.Time.valueOf("03:00:00"));
		// groupRoom_Create.setReserveGroupStartTime(java.sql.Timestamp
		// .valueOf("2015-1-1 13:00:00"));
		// groupRoom_Create.setReserveGroupEndTime(java.sql.Timestamp
		// .valueOf("2015-1-1 16:00:00"));
		// groupRoom_Create.setRoomState(0);
		// String filename1 = "boardgames.jpg";
		// groupRoom_Create.setImgFileName(filename1);
		// File f = new File("WebContent/res/" +
		// groupRoom_Create.getImgFileName());
		// try {
		// InputStream is = new FileInputStream(f);
		// ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		// int nRead;
		// byte[] data = new byte[1024];
		// while ((nRead = is.read(data, 0, data.length)) != -1) {
		// buffer.write(data, 0, nRead);
		// buffer.flush();
		// }
		// data = buffer.toByteArray();
		// is.close();
		// groupRoom_Create.setPrivateGroupImage(data);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// service.addGroupRoom(groupRoom_Create);
		// 加團測試
		// GroupRoom groupRoom_ToJoin = new GroupRoom();
		// groupRoom_ToJoin.setGroupSerialNumber(1);
		// Joiner_Info jInfo = new Joiner_Info();
		// jInfo.setGroupRoom(groupRoom_ToJoin);
		// java.util.Date utilDate = java.util.Calendar.getInstance().getTime();
		// SimpleDateFormat sdFormat = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// String JoinDate = sdFormat.format(utilDate);
		// jInfo.setJoinTime(java.sql.Timestamp.valueOf(JoinDate));
		// service.iWantToJoinThisGroup(jInfo);
		// jInfo.setUsername("pewdiepie");// 到session撈資料
		// 退團測試
		// jInfo.setJoinerInfoSerialNumber(9);
		// service.iWantToLeaveThisGroup(jInfo);
		// 找團測試
		// String unknownQuery = "遊";
		// List<GroupRoom> b3 = service.getOneGroupRoom(unknownQuery);
		// for (GroupRoom vo : b3) {
		// System.out.println(vo.getGroupRoomName());
		// }
	}
}
