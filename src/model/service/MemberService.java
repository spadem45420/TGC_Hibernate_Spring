package model.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.aspectj.org.eclipse.jdt.core.dom.ThisExpression;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.Member;
import model.Member_FavoredType;
import model.StoreInformation;
import model.StoreScore;
import model.Interface.MemberDAO_Interface;
import model.Interface.Member_FavoredTypeDAO_Interface;
import model.Interface.StoreInformationDAO_Interface;
import model.Interface.StoreScoreDAO_Interface;

public class MemberService {
	private MemberDAO_Interface mdao;
	private Member_FavoredTypeDAO_Interface mfdao;
	private StoreInformationDAO_Interface sidao;
	private StoreScoreDAO_Interface ssdao;

	public MemberService() {// 建構子
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"model-config1-DriverManagerDataSource.xml");
		// ApplicationContext context = new ClassPathXmlApplicationContext(
		// "model-config2-JndiObjectFactoryBean.xml");
		mdao = (MemberDAO_Interface) context.getBean("MemberDAO");
		mfdao = (Member_FavoredTypeDAO_Interface) context
				.getBean("Member_FavoredTypeDAO");
		sidao = (StoreInformationDAO_Interface) context
				.getBean("StoreInformationDAO");
		ssdao = (StoreScoreDAO_Interface) context.getBean("StoreScoreDAO");
	}

	public boolean addMember(Member bean) {// 註冊會員
		if (!this.isTheMemberExist(bean)) {
			java.util.Date utilDate = java.util.Calendar.getInstance()
					.getTime();
			SimpleDateFormat sdFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String JoinDate = sdFormat.format(utilDate);
			bean.setJoinDate(java.sql.Timestamp.valueOf(JoinDate));
			mdao.insert(bean);
			return true;
		}
		return false;
	}

	public boolean login(Member bean) {// 登入會員
		byte[] passwordFromDatabase = null;
		List<Member> list = mdao.findByUsername(bean.getUsername());
		if (list != null) {
			for (Member vo : list) {
				passwordFromDatabase = vo.getPswd();
				// 內容比對會用到equals但是因為不是String的比對故必須宣告物件.方法()
				if (Arrays.equals(bean.getPswd(), passwordFromDatabase)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean updateMember(Member bean) {// 會員資料修改
		// 先把舊的資料撈出來
		Member origin = mdao.findByPrimeKey(bean.getMemberId());
		// 代表資料庫有資料，才修改不能新增
		if (origin != null) {
			// 不得修改的欄位直接用舊的資料
			bean.setUsername(origin.getUsername());
			bean.setPswd(bean.getPswd());
			bean.setIdCard(origin.getIdCard());
			// 不得為空值的欄位，如果沒有收到輸入值，就塞回舊資料
			if (bean.getEmail() == null) {
				bean.setEmail(origin.getEmail());
			}
			if (bean.getLastname() == null) {
				bean.setLastname(origin.getLastname());
			}
			if (bean.getFirstname() == null) {
				bean.setFirstname(origin.getFirstname());
			}
			mdao.update(bean);
			return true;
		}
		return false;
	}

	public boolean iForgotMyPassword(Member bean) {// 忘記密碼
		List<Member> list = mdao.findByEmail(bean.getEmail());
		for (Member vo : list) {
			if (vo.getEmail().equals(bean.getEmail())) {
				// 先把舊的資料撈出來
				Member origin = mdao.findByPrimeKey(vo.getMemberId());
				// 代表資料庫有資料，才修改不能新增
				if (origin != null) {
					// 不得修改的欄位直接用舊的資料
					origin.setPswd("".getBytes());
					mdao.update(origin);
					return true;
				}
			}
		}
		return false;
	}

	public boolean deleteMember(Integer memberId) {// 刪除會員
		mdao.delete(memberId);
		return true;
	}

	public Member getOneMember(Integer memberId) {// 查詢單筆會員(主鍵-memberId)
		return mdao.findByPrimeKey(memberId);
	}

	public List<Member> getOneMember(Member bean) {// 查詢單筆會員(非主鍵(傳bean)-username)
		return mdao.findByUsername(bean.getUsername());
	}

	public List<Member> getOneMember(String unknown) {// 模糊查詢會員(非主鍵-unknown)
		return mdao.findByUnknown(unknown);
	}

	public List<Member> getAll() {// 查全部
		return mdao.getAll();
	}

	public boolean addMyFavoredType(Member_FavoredType bean) {// 新增最愛項目類型
		Member member = mdao.findByPrimeKey(bean.getMember().getMemberId());
		if (member != null) {
			mfdao.insert(bean);
			return true;
		}
		return false;
	}

	public boolean ratingsScoreToStore(StoreScore bean) {// 評分店家
		// 查詢店家是否存在
		StoreInformation storeInformation = sidao.findByPrimeKey(bean
				.getStoreScoreId());
		// 查詢會員是否存在
		Member member = mdao.findByPrimeKey(bean.getMember().getMemberId());
		// 都確定存在即可允許新增評分
		if (storeInformation != null && member != null) {
			ssdao.insert(bean);
			return true;
		}
		return false;
	}

	private boolean isTheMemberExist(Member bean) { // 查詢會員是否存在
		List<Member> list = mdao.getAll();
		for (Member vo : list) {
			if (bean.getUsername().equals(vo.getUsername())) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		MemberService service = new MemberService();
		// 註冊會員
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
		// 改成系統抓現在時間，請見上面的 addMember()
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
		// service.addMember(bean1);
		// 登入
		Member bean2 = new Member();
		bean2.setUsername("pewdiepie");
		bean2.setPswd("Aa@pdp".getBytes());
		System.out.println(service.login(bean2));
		// 修改會員資料
		// Member bean3 = new Member();
		// bean3.setMemberId(1);// 資料不完全修改
		// service.updateMember(bean3);
		// bean3.setMemberId(1);// 資料全修改
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
		// service.updateMember(bean3);
		// 忘記密碼
		// Member bean4 = new Member();
		// bean4.setEmail("pewdiepie@gmail.com");
		// service.iForgotMyPassword(bean4);
		// System.out.println(service.iForgotMyPassword(bean4));
		// 刪除會員
		// service.deleteMember(1);
		// 查詢單筆會員
		// Member b1 = service.getOneMember(1);
		// System.out.println(b1.getUsername());
		// Member member = new Member();
		// member.setUsername("pewdiepie");
		// List<Member> b2 = service.getOneMember(member);
		// for (Member vo : b2) {
		// System.out.println(vo.getUsername());
		// }
		// 模糊查詢會員
		// String unknownQuery = "";
		// List<Member> b3 = service.getOneMember(unknownQuery);
		// for (Member vo : b3) {
		// System.out.println(vo.getNickname());
		// }
		// 查詢多筆會員
		// List<Member> beans = service.getAll();
		// for (Member vo : beans) {
		// System.out.println(vo.getMemberId());
		// System.out.println(vo.getUsername());
		// System.out.println(vo.getNickname());
		// }
		// 新增最愛項目類型
		// Member member = new Member();
		// member.setMemberId(1);
		// Member_FavoredType mFavoredType = new Member_FavoredType();
		// mFavoredType.setMember(member);
		// mFavoredType.setFavoredType("策略遊戲");
		// service.addMyFavoredType(mFavoredType);

	}
}
