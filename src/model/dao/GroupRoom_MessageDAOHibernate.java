package model.dao;

import java.util.List;

import model.GroupRoom_Message;
import model.Interface.GroupRoom_MessageDAO_Interface;

import org.springframework.orm.hibernate3.HibernateTemplate;

public class GroupRoom_MessageDAOHibernate implements
		GroupRoom_MessageDAO_Interface {
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public GroupRoom_Message findByPrimeKey(Integer groupRoomMessageSerialNumber) {
		GroupRoom_Message groupRoomMessage = (GroupRoom_Message) hibernateTemplate
				.get(GroupRoom_Message.class, groupRoomMessageSerialNumber);
		return groupRoomMessage;
	}

	private static final String GET_ALL_STMT = "from GroupRoomMessage order by groupSerialNumber";

	@Override
	public List<GroupRoom_Message> getAll() {
		List<GroupRoom_Message> list = hibernateTemplate.find(GET_ALL_STMT);
		return list;
	}

	@Override
	public void insert(GroupRoom_Message bean) {
		hibernateTemplate.saveOrUpdate(bean);
	}

	@Override
	public void update(GroupRoom_Message bean) {
		hibernateTemplate.saveOrUpdate(bean);
	}

	@Override
	public void delete(Integer groupRoomMessageSerialNumber) {
		GroupRoom_Message groupRoomMessage = (GroupRoom_Message) hibernateTemplate
				.get(GroupRoom_Message.class, groupRoomMessageSerialNumber);
		hibernateTemplate.delete(groupRoomMessage);
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
