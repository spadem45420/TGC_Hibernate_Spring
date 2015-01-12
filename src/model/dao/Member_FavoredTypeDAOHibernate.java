package model.dao;

import java.util.List;

import model.Member;
import model.Member_FavoredType;
import model.Interface.MemberDAO_Interface;
import model.Interface.Member_FavoredTypeDAO_Interface;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class Member_FavoredTypeDAOHibernate implements
		Member_FavoredTypeDAO_Interface {
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public Member_FavoredType findByPrimeKey(Integer memberFavoredId) {
		Member_FavoredType memberFavoredType = (Member_FavoredType) hibernateTemplate
				.get(Member_FavoredType.class, memberFavoredId);
		return memberFavoredType;
	}

	private static final String GET_ALL_STMT = "from Member_FavoredType order by memberFavoredId";

	@Override
	public List<Member_FavoredType> getAll() {
		List<Member_FavoredType> list = hibernateTemplate.find(GET_ALL_STMT);
		return list;
	}

	@Override
	public void insert(Member_FavoredType bean) {
		hibernateTemplate.saveOrUpdate(bean);
	}

	@Override
	public void update(Member_FavoredType bean) {
		hibernateTemplate.saveOrUpdate(bean);
	}

	@Override
	public void delete(Integer memberFavoredId) {
		Member_FavoredType memberFavoredType = (Member_FavoredType) hibernateTemplate
				.get(Member_FavoredType.class, memberFavoredId);
		hibernateTemplate.delete(memberFavoredType);
	}

	public static void main(String[] args) {
		// Member_FavoredTypeDAO_Interface dao = new
		// Member_FavoredTypeDAOHibernate();
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"model-config1-DriverManagerDataSource.xml");
		Member_FavoredTypeDAO_Interface dao = (Member_FavoredTypeDAO_Interface) context
				.getBean("Member_FavoredTypeDAO");
		// 新增
		Member_FavoredType bean1 = new Member_FavoredType();
		MemberDAO_Interface mdao = (MemberDAO_Interface) context
				.getBean("MemberDAO");
		Member mbean1 = mdao.findByPrimeKey(1);
		System.out.println(mbean1.getUsername());
		bean1.setMember(mbean1);
		bean1.setFavoredType("策略遊戲");
		dao.insert(bean1);

		Member_FavoredType bean2 = new Member_FavoredType();
		Member mbean2 = mdao.findByPrimeKey(1);
		bean2.setMember(mbean2);
		bean2.setFavoredType("益智遊戲");
		dao.insert(bean2);

		Member_FavoredType bean3 = new Member_FavoredType();
		Member mbean3 = mdao.findByPrimeKey(1);
		bean3.setMember(mbean3);
		bean3.setFavoredType("角色扮演遊戲");
		dao.insert(bean3);
		// 修改
		// 刪除
		// 查詢單筆
		// 查詢多筆
		List<Member_FavoredType> beans = dao.getAll();
		for (Member_FavoredType vo : beans) {
			System.out.println(vo.getMemberFavoredId());
			System.out.println(vo.getMember().getMemberId());
			System.out.println(vo.getFavoredType());
		}
	}
}
