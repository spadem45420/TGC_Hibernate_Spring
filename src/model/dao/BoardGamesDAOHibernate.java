package model.dao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import model.BoardGameKind;
import model.BoardGames;
import model.BoardGames_Image;
import model.StoreInformation;
import model.StoreMember;
import model.Interface.BoardGameKindDAO_Interface;
import model.Interface.BoardGamesDAO_Interface;
import model.Interface.RentalTimeDAO_Interface;
import model.Interface.StoreInformationDAO_Interface;
import model.Interface.StoreMemberDAO_Interface;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class BoardGamesDAOHibernate implements BoardGamesDAO_Interface {
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public BoardGames findByPrimeKey(Integer boardGamesId) {
		BoardGames boardGames = (BoardGames) hibernateTemplate.get(
				BoardGames.class, boardGamesId);
		return boardGames;
	}
	
	private static final String GET_GAMES_BY_STOREID = "from BoardGames where storeId = ? order by boardGamesId";
	
	public List<BoardGames> findByStoreId(Integer storeId){
		List<BoardGames> list = hibernateTemplate.find(GET_GAMES_BY_STOREID, storeId);
		return list;
	}
	

	private static final String GET_GAMES_BY_TYPE = "from BoardGames where storeId = ? and boardGameSerialNumber = ?";
	
	public List<BoardGames> findGamesByType(int storeId,int type){
		Integer[] i ={storeId,type};
		List<BoardGames> list = hibernateTemplate.find(GET_GAMES_BY_TYPE,i);
		return list;
	}
	
	private static final String GET_GAMES_FROM_RANGE = "from BoardGames where storeId = ? and boardGamesId > ? and boardGamesId < ?";
	
	public List<BoardGames> findByRange(Integer storeId, Integer r1, Integer r2){
		List<BoardGames> list = hibernateTemplate.find(GET_GAMES_FROM_RANGE, storeId,r1,r2);
		return list;
	}

	private static final String GET_ALL_STMT = "from BoardGames order by boardGamesId";

	@Override
	public List<BoardGames> getAll() {
		List<BoardGames> list = hibernateTemplate.find(GET_ALL_STMT);
		return list;
	}

	@Override
	public void insert(BoardGames bean) {
		hibernateTemplate.saveOrUpdate(bean);
	}

	@Override
	public void update(BoardGames bean) {
		hibernateTemplate.saveOrUpdate(bean);
	}

	@Override
	public void delete(Integer boardGamesId) {
		BoardGames boardGames = (BoardGames) hibernateTemplate.get(
				BoardGames.class, boardGamesId);
		hibernateTemplate.delete(boardGames);
	}

	public static void main(String[] args) {
		// BoardGamesDAO_Interface dao = new BoardGamesDAOHibernate();
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"model-config1-DriverManagerDataSource.xml");
		BoardGamesDAO_Interface dao = (BoardGamesDAO_Interface) context
				.getBean("BoardGamesDAO");
		// 新增
		BoardGames bean1 = new BoardGames();
		StoreInformationDAO_Interface sidao = (StoreInformationDAO_Interface) context
				.getBean("StoreInformationDAO");
		StoreInformation sibean1 = sidao.findByPrimeKey(1);
		bean1.setStoreInformation(sibean1);
		bean1.setStoreName(sibean1.getStoreName());
		bean1.setBoardGameEnglishName("Bang!");
		bean1.setBoardGameName("西部無間");
		BoardGameKindDAO_Interface bgkdao = (BoardGameKindDAO_Interface) context
				.getBean("BoardGameKindDAO");
		BoardGameKind bgkbean1 = bgkdao.findByPrimeKey(1);
		bean1.setBoardGameKind(bgkbean1);
		bean1.setBoardGameNumber("4 - 7");
		String filename1 = "Bang.png";
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
			bean1.setBoardGameImage(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		bean1.setBoardGameExplan("如果你活在華語圈，至少家裡會開電視，那麼想必就對上面的對白並不陌生﹔電影無間道在這兩年紅透了半邊天，並且成功地為臥底這個電影的老橋段賦予了新生命，而在 BANG! 這款遊戲中，玩家們來到美國拓荒時期的西部，面對同樣的主題，不同的人物，誰能夠瞞天過海贏得最後的勝利呢？");
		dao.insert(bean1);
		// 修改
		// BoardGames bean2 = new BoardGames();
		// StoreInformationDAO_Interface sidao1 = new
		// StoreInformationDAOHibernate();
		// StoreInformation sibean2 = sidao1.findByPrimeKey(1);
		// bean2.setBoardGamesId(1);
		// bean2.setStoreName(sibean2.getStoreName());
		// bean2.setBoardGameEnglishName("Bang!!!!!!!!!!!!!!!");
		// bean2.setBoardGameName("西部無間");
		// BoardGameKindDAO_Interface bgkdao1 = new BoardGameKindDAOHibernate();
		// BoardGameKind bgkbean2 = bgkdao1.findByPrimeKey(1);
		// bean2.setBoardGameKind(bgkbean2);
		// bean2.setBoardGameNumber("4 - 7");
		// String filename2 = "Bang.png";
		// bean2.setImgFileName(filename2);
		// File f1 = new File("WebContent/res/" + bean2.getImgFileName());
		// try {
		// InputStream is = new FileInputStream(f1);
		// ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		// int nRead;
		// byte[] data = new byte[1024];
		// while ((nRead = is.read(data, 0, data.length)) != -1) {
		// buffer.write(data, 0, nRead);
		// buffer.flush();
		// }
		// data = buffer.toByteArray();
		// is.close();
		// bean2.setBoardGameImage(data);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// bean2.setBoardGameExplan("如果你活在華語圈，至少家裡會開電視，那麼想必就對上面的對白並不陌生﹔電影無間道在這兩年紅透了半邊天，並且成功地為臥底這個電影的老橋段賦予了新生命，而在 BANG! 這款遊戲中，玩家們來到美國拓荒時期的西部，面對同樣的主題，不同的人物，誰能夠瞞天過海贏得最後的勝利呢？");
		// dao.update(bean2);
		// 刪除
		// dao.delete(1);
		// 查詢單筆
		// BoardGames b1 = dao.findByPrimeKey(2);
		// System.out.println(b1.getBoardGameExplan());
		// 查詢多筆
//		List<BoardGames> beans = dao.getAll();
//		for (BoardGames vo : beans) {
//			System.out.println(vo.getBoardGamesId());
//			System.out.println(vo.getBoardGameEnglishName());
//			System.out.println(vo.getBoardGameName());
//			System.out.println(vo.getBoardGameNumber());
//			System.out.println(vo.getBoardGameExplan());
//		}
		
		//找桌遊By類型
//		List<BoardGames> list = dao.findGamesByType(1, 2);
//		for(BoardGames vo : list){
//			System.out.println(vo.getBoardGameName());
//		}
	}

	@Override
	public Set<BoardGames_Image> getBoardGames_ImageByBoardGamesId(
			Integer boardGamesId) {
		Set<BoardGames_Image> set = findByPrimeKey(boardGamesId)
				.getBoardGamesImages();
		return set;
	}
}
