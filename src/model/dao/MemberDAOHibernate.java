package model.dao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import model.GroupRoom;
import model.Member;
import model.Member_FavoredType;
import model.StoreScore;
import model.TabuUsernameTable;
import model.Interface.AdministratorDAO_Interface;
import model.Interface.MemberDAO_Interface;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class MemberDAOHibernate implements MemberDAO_Interface {
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public Member findByPrimeKey(Integer memberId) {
		Member member = (Member) hibernateTemplate.get(Member.class, memberId);
		return member;
	}

	private static final String GET_BY_EMAIL = "from Member where email = ?";

	@Override
	public List<Member> findByEmail(String email) {
		List<Member> list = hibernateTemplate.find(GET_BY_EMAIL,email);
		return list;
	}

	@Override
	public List<Member> findByUsername(String username) {
		Member member = new Member();
		member.setUsername(username);
		List<Member> list = hibernateTemplate.findByExample(member);
		return list;
	}

	private static final String GET_BY_UNKNOWN = "from Member where username like ? or nickname like ? order by memberId";

	@Override
	public List<Member> findByUnknown(String unknown) {
		String[] unknownQuery = { "%" + unknown + "%", "%" + unknown + "%" };
		List<Member> list = hibernateTemplate
				.find(GET_BY_UNKNOWN, unknownQuery);
		return list;
	}

	private static final String GET_ALL_STMT = "from Member order by memberId";

	@Override
	public List<Member> getAll() {
		List<Member> list = hibernateTemplate.find(GET_ALL_STMT);
		return list;

	}

	@Override
	public void insert(Member bean) {
		hibernateTemplate.saveOrUpdate(bean);
	}

	@Override
	public void update(Member bean) {
		hibernateTemplate.saveOrUpdate(bean);
	}

	@Override
	public void delete(Integer memberId) {
		Member member = (Member) hibernateTemplate.get(Member.class, memberId);
		hibernateTemplate.delete(member);
	}

	public static void main(String[] args) {
		// MemberDAO_Interface dao = new MemberDAOHibernate();
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"model-config1-DriverManagerDataSource.xml");
		MemberDAO_Interface dao = (MemberDAO_Interface) context
				.getBean("MemberDAO");
		// 新增
		Member bean1 = new Member();
		bean1.setUsername("pewdiepie");
		bean1.setPswd("Aa@pdp".getBytes());
		bean1.setEmail("pewdiepie@gmail.com");
		bean1.setLastname("皮");
		bean1.setFirstname("弟派");
		bean1.setGender("男");
		bean1.setNickname("PewDiePie");
		bean1.setBirthday(java.sql.Date.valueOf("1990-10-24"));
		bean1.setIdCard("A154730489");
		bean1.setJoinDate(java.sql.Date.valueOf("2007-2-28"));
		bean1.setPhone("0986731905");
		bean1.setMemberAddress("106台北市大安區復興南路一段390號");
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
			bean1.setMemberImage(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		dao.insert(bean1);

		Member bean2 = new Member();
		bean2.setUsername("opchannel");
		bean2.setPswd("Bb@op".getBytes());
		bean2.setEmail("opchannel@gmail.com");
		bean2.setLastname("皮");
		bean2.setFirstname("老");
		bean2.setGender("男");
		bean2.setNickname("老皮");
		bean2.setBirthday(java.sql.Date.valueOf("1992-5-6"));
		bean2.setIdCard("H189519637");
		bean2.setJoinDate(java.sql.Date.valueOf("2007-5-12"));
		bean2.setPhone("0924496029");
		bean2.setMemberAddress("337桃園縣大園鄉華中街46號");
		String filename2 = "boardgames.jpg";
		bean2.setImgFileName(filename2);
		File f1 = new File("WebContent/res/" + bean2.getImgFileName());
		try {
			InputStream is1 = new FileInputStream(f1);
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int nRead;
			byte[] data = new byte[1024];
			while ((nRead = is1.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
				buffer.flush();
			}
			data = buffer.toByteArray();
			is1.close();
			bean2.setMemberImage(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		dao.insert(bean2);
		// 修改
		// Member bean3 = new MemberVO();
		// bean3.setMemberId(1);
		// bean3.setUsername("qksniper");
		// bean3.setPswd("Cc@qksniper".getBytes());
		// bean3.setEmail("qksniper@gmail.com");
		// bean3.setLastname("Q");
		// bean3.setFirstname("K");
		// bean3.setGender("男");
		// bean3.setNickname("QK");
		// bean3.setBirthday(java.sql.Date.valueOf("1993-8-13"));
		// bean3.setIdCard("A191154952");
		// bean3.setJoinDate(java.sql.Date.valueOf("2009-1-18"));
		// bean3.setPhone("0960636461");
		// bean3.setMemberAddress("106台北市大安區敦化南路一段305號");
		// bean3.setIsGroupBan(true);
		// bean3.setIsCommentBan(true);
		// dao.update(bean3);
		// 刪除
		// dao.delete(1);
		// 查詢單筆
		// Member b1 = dao.findByPrimeKey(1);
		// System.out.println(b1.getUsername());
		// Set<Member_FavoredType> mft = b1.getMemberFavoredTypes();
		// for (Member_FavoredType vo : mft) {
		// System.out.println(vo.getMemberFavoredId());
		// System.out.println(vo.getFavoredType());
		// }
		// List<Member> b2 = dao.findByUsername("pewdiepie");
		// for (Member vo : b2) {
		// System.out.println(vo.getUsername());
		// }
		// 查詢多筆
		List<Member> beans = dao.getAll();
		for (Member vo : beans) {
			System.out.println(vo.getMemberId());
			System.out.println(vo.getUsername());
			System.out.println(vo.getNickname());
		}
	}

	@Override
	public Set<TabuUsernameTable> getTabuUsernameTablesByMemberId(
			Integer memberId) {
		Set<TabuUsernameTable> set = findByPrimeKey(memberId)
				.getTabuUsernameTablesForTabuMemberId();
		return set;
	}

	@Override
	public Set<StoreScore> getStoreScoreByMemberId(Integer memberId) {
		Set<StoreScore> set = findByPrimeKey(memberId).getStoreScores();
		return set;
	}

	@Override
	public Set<GroupRoom> getGroupRoomByMemberId(Integer memberId) {
		Set<GroupRoom> set = findByPrimeKey(memberId).getGroupRooms();
		return set;
	}

	@Override
	public Set<Member_FavoredType> getMember_FavoredTypeByMemberId(
			Integer memberId) {
		Set<Member_FavoredType> set = findByPrimeKey(memberId)
				.getMemberFavoredTypes();
		return set;
	}
}
