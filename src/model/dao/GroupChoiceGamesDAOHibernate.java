package model.dao;

import java.util.List;

import model.GroupChoiceGames;
import model.Interface.GroupChoiceGamesDAO_Interface;
import model.Interface.MemberDAO_Interface;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class GroupChoiceGamesDAOHibernate implements
		GroupChoiceGamesDAO_Interface {
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public GroupChoiceGames findByPrimeKey(Integer choiceGamesSerialNumber) {
		GroupChoiceGames groupChoiceGames = (GroupChoiceGames) hibernateTemplate
				.get(GroupChoiceGames.class, choiceGamesSerialNumber);
		return groupChoiceGames;
	}

	private static final String GET_TYPE_FROM_ROOM =
			"select boardGameKind.boardGameSerialNumber from GroupChoiceGames where groupSerialNumber = ? group by boardGameKind.boardGameSerialNumber";

	public List<Integer> getTypeFromRoom(int roomId){
		List<Integer> list = hibernateTemplate.find(GET_TYPE_FROM_ROOM,roomId);
		return list;
	}
	
	private static final String GET_ALL_STMT = "from GroupChoiceGames order by choiceGamesSerialNumber";	
	
	@Override
	public List<GroupChoiceGames> getAll() {
		List<GroupChoiceGames> list = hibernateTemplate.find(GET_ALL_STMT);
		return list;
	}

	@Override
	public void insert(GroupChoiceGames bean) {
		hibernateTemplate.saveOrUpdate(bean);
	}

	@Override
	public void update(GroupChoiceGames bean) {
		hibernateTemplate.saveOrUpdate(bean);
	}

	@Override
	public void delete(String choiceGamesSerialNumber) {
		GroupChoiceGames groupChoiceGames = (GroupChoiceGames) hibernateTemplate
				.get(GroupChoiceGames.class, choiceGamesSerialNumber);
		hibernateTemplate.delete(groupChoiceGames);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 新增
		// 修改
		// 刪除
		// 查詢單筆
		// 查詢多筆
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"model-config1-DriverManagerDataSource.xml");
		GroupChoiceGamesDAO_Interface dao =
				(GroupChoiceGamesDAO_Interface) context.getBean("GroupChoiceGamesDAO");
		
		//getTypeFromRoom
		List<Integer> list = dao.getTypeFromRoom(3);
		for(Integer bean : list){
			System.out.println(bean);
		}
		System.out.println(list);
	}
}
