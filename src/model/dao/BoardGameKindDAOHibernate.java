package model.dao;

import java.util.List;
import java.util.Set;

import model.BoardGameKind;
import model.BoardGames;
import model.GroupChoiceGames;
import model.Interface.BoardGameKindDAO_Interface;
import model.Interface.StoreInformationDAO_Interface;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class BoardGameKindDAOHibernate implements BoardGameKindDAO_Interface {
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public BoardGameKind findByPrimeKey(Integer boardGameSerialNumber) {
		BoardGameKind boardGameKind = (BoardGameKind) hibernateTemplate.get(
				BoardGameKind.class, boardGameSerialNumber);
		return boardGameKind;
	}

	private static final String GET_ALL_STMT = "from BoardGameKind order by boardGameSerialNumber";

	@Override
	public List<BoardGameKind> getAll() {
		List<BoardGameKind> list = hibernateTemplate.find(GET_ALL_STMT);
		return list;
	}

	@Override
	public void insert(BoardGameKind bean) {
		hibernateTemplate.saveOrUpdate(bean);
	}

	@Override
	public void update(BoardGameKind bean) {
		hibernateTemplate.saveOrUpdate(bean);
	}

	@Override
	public void delete(Integer boardGameSerialNumber) {
		BoardGameKind boardGameKind = (BoardGameKind) hibernateTemplate.get(
				BoardGameKind.class, boardGameSerialNumber);
		hibernateTemplate.delete(boardGameKind);
	}

	public static void main(String[] args) {
		// BoardGameKindDAO_Interface dao = new BoardGameKindDAOHibernate();
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"model-config1-DriverManagerDataSource.xml");
		BoardGameKindDAO_Interface dao = (BoardGameKindDAO_Interface) context
				.getBean("BoardGameKindDAO");
		// 新增
		BoardGameKind bean1 = new BoardGameKind();
		bean1.setBoardGameSerialNumber(1);
		bean1.setBoardGameStyle("策略遊戲");
		dao.insert(bean1);

		BoardGameKind bean2 = new BoardGameKind();
		bean2.setBoardGameSerialNumber(2);
		bean2.setBoardGameStyle("益智遊戲");
		dao.insert(bean2);

		BoardGameKind bean3 = new BoardGameKind();
		bean3.setBoardGameSerialNumber(3);
		bean3.setBoardGameStyle("推理遊戲");
		dao.insert(bean3);

		BoardGameKind bean4 = new BoardGameKind();
		bean4.setBoardGameSerialNumber(4);
		bean4.setBoardGameStyle("角色扮演遊戲");
		dao.insert(bean4);

		BoardGameKind bean5 = new BoardGameKind();
		bean5.setBoardGameSerialNumber(5);
		bean5.setBoardGameStyle("小品遊戲");
		dao.insert(bean5);
		// 修改
		// BoardGameKind bean6 = new BoardGameKind();
		// bean6.setBoardGameNumber(1);
		// bean6.setBoardGameStyle("恐怖遊戲");
		// dao.update(bean6);
		// 刪除
		// dao.delete(1);
		// 查詢單筆
		BoardGameKind b1 = dao.findByPrimeKey(1);
		System.out.println(b1.getBoardGameStyle());
		// 查詢多筆
		List<BoardGameKind> beans = dao.getAll();
		for (BoardGameKind vo : beans) {
			System.out.println(vo.getBoardGameSerialNumber());
			System.out.println(vo.getBoardGameStyle());
		}
	}

	@Override
	public Set<GroupChoiceGames> getGroupChoiceGamesByBoardGameSerialNumber(
			Integer boardGameSerialNumber) {
		Set<GroupChoiceGames> set = findByPrimeKey(boardGameSerialNumber)
				.getGroupChoiceGameses();
		return set;
	}

	@Override
	public Set<BoardGames> getBoardGamesByBoardGameSerialNumber(
			Integer boardGameSerialNumber) {
		Set<BoardGames> set = findByPrimeKey(boardGameSerialNumber)
				.getBoardGameses();
		return set;
	}
}
