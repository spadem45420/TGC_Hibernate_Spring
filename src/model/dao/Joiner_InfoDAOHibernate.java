package model.dao;

import java.util.List;

import model.Joiner_Info;
import model.Joiner_InfoDAO_Interface;
import model.StoreMemberDAO_Interface;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class Joiner_InfoDAOHibernate implements Joiner_InfoDAO_Interface {
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public Joiner_Info findByPrimeKey(String groupSerialNumber) {
		Joiner_Info joiner_Info = (Joiner_Info) hibernateTemplate.get(
				Joiner_Info.class, groupSerialNumber);
		return joiner_Info;
	}

	private static final String GET_ALL_STMT = "from Joiner_Info order by groupSerialNumber";
	
	private static final String GET_BY_GROUPSERIALNUMBER = "from Joiner_Info where groupSerialNumber = ?";

	public List<Joiner_Info> Count(Integer groupSerialNumber) {
		List<Joiner_Info> list = hibernateTemplate.find(GET_BY_GROUPSERIALNUMBER,groupSerialNumber);
		return list;
	}
	
	@Override
	public List<Joiner_Info> getAll() {
		List<Joiner_Info> list = hibernateTemplate.find(GET_ALL_STMT);
		return list;
	}

	@Override
	public void insert(Joiner_Info bean) {
		hibernateTemplate.saveOrUpdate(bean);
	}

	@Override
	public void update(Joiner_Info bean) {
		hibernateTemplate.saveOrUpdate(bean);
	}

	@Override
	public void delete(Integer joiner_InfoSerialNumber) {
		Joiner_Info joiner_Info = (Joiner_Info) hibernateTemplate.get(
				Joiner_Info.class, joiner_InfoSerialNumber);
		hibernateTemplate.delete(joiner_Info);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 新增
		// 修改
		// 刪除
		// 查詢單筆
		// 查詢團人數
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"model-config1-DriverManagerDataSource.xml");
		Joiner_InfoDAO_Interface dao = (Joiner_InfoDAO_Interface) context
				.getBean("Joiner_InfoDAO");
		List<Joiner_Info> list = dao.Count(1);
		System.out.println(list.size());
		// 查詢多筆
	}
}