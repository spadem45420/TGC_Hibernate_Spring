package model.dao;

import java.util.List;

import model.GroupRoom_Info;
import model.GroupRoom_InfoDAO_Interface;

import org.springframework.orm.hibernate3.HibernateTemplate;

public class GroupRoom_InfoDAOHibernate implements GroupRoom_InfoDAO_Interface {
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public GroupRoom_Info findByPrimeKey(String groupSerialNumber) {
		GroupRoom_Info groupRoomInfo = (GroupRoom_Info) hibernateTemplate.get(
				GroupRoom_Info.class, groupSerialNumber);
		return groupRoomInfo;
	}

	private static final String GET_ALL_STMT = "from GroupRoomInfo order by groupSerialNumber";

	@Override
	public List<GroupRoom_Info> getAll() {
		List<GroupRoom_Info> list = hibernateTemplate.find(GET_ALL_STMT);
		return list;
	}

	@Override
	public void insert(GroupRoom_Info bean) {
		hibernateTemplate.saveOrUpdate(bean);
	}

	@Override
	public void update(GroupRoom_Info bean) {
		hibernateTemplate.saveOrUpdate(bean);
	}

	@Override
	public void delete(Integer groupSerialNumber) {
		GroupRoom_Info groupRoomInfo = (GroupRoom_Info) hibernateTemplate.get(
				GroupRoom_Info.class, groupSerialNumber);
		hibernateTemplate.delete(groupRoomInfo);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 新增
		// 修改
		// 刪除
		// 查詢單筆
		// 查詢多筆
	}
}
