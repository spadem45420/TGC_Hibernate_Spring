package model.dao;

import java.util.List;

import model.BoardGames_Image;
import model.Interface.BoardGames_ImageDAO_Interface;

import org.springframework.orm.hibernate3.HibernateTemplate;

public class BoardGames_ImageDAOHibernate implements
		BoardGames_ImageDAO_Interface {
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public BoardGames_Image findByPrimeKey(Integer storeImageId) {
		BoardGames_Image boardGamesImage = (BoardGames_Image) hibernateTemplate
				.get(BoardGames_Image.class, storeImageId);
		return boardGamesImage;
	}

	private static final String GET_ALL_STMT = "from BoardGamesImage order by storeImageId";

	@Override
	public List<BoardGames_Image> getAll() {
		List<BoardGames_Image> list = hibernateTemplate.find(GET_ALL_STMT);
		return list;
	}

	@Override
	public void insert(BoardGames_Image bean) {
		hibernateTemplate.saveOrUpdate(bean);
	}

	@Override
	public void update(BoardGames_Image bean) {
		hibernateTemplate.saveOrUpdate(bean);
	}

	@Override
	public void delete(Integer storeImageId) {
		BoardGames_Image boardGamesImage = (BoardGames_Image) hibernateTemplate
				.get(BoardGames_Image.class, storeImageId);
		hibernateTemplate.delete(boardGamesImage);
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
