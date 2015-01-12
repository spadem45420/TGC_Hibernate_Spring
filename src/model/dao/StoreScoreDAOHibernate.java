package model.dao;

import java.util.List;

import model.Member;
import model.StoreInformation;
import model.StoreScore;
import model.Interface.MemberDAO_Interface;
import model.Interface.StoreInformationDAO_Interface;
import model.Interface.StoreMemberDAO_Interface;
import model.Interface.StoreScoreDAO_Interface;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class StoreScoreDAOHibernate implements StoreScoreDAO_Interface {
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public StoreScore findByPrimeKey(Integer storeScoreId) {
		StoreScore storeScore = (StoreScore) hibernateTemplate.get(
				StoreScore.class, storeScoreId);
		return storeScore;
	}
	
	private static final String GET_BY_STOREID = "from StoreScore where storeId = ?";
	
	public List<StoreScore> findByStoreId(Integer storeId){
		List<StoreScore> list = hibernateTemplate.find(GET_BY_STOREID, storeId);
		return list;
	}

	private static final String GET_ALL_STMT = "from StoreScore order by storeId";

	@Override
	public List<StoreScore> getAll() {
		List<StoreScore> list = hibernateTemplate.find(GET_ALL_STMT);
		return list;
	}

	@Override
	public void insert(StoreScore ssbean) {
		hibernateTemplate.saveOrUpdate(ssbean);
	}

	@Override
	public void update(StoreScore ssbean) {
		hibernateTemplate.saveOrUpdate(ssbean);
	}

	@Override
	public void delete(Integer storeId) {
		StoreScore storeScore = (StoreScore) hibernateTemplate.get(
				StoreScore.class, storeId);
		hibernateTemplate.delete(storeScore);
	}

	public static void main(String[] args) {
		// StoreScoreDAO_Interface dao = new StoreScoreDAOHibernate();
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"model-config1-DriverManagerDataSource.xml");
		MemberDAO_Interface mdao = (MemberDAO_Interface) context
				.getBean("MemberDAO");
		StoreInformationDAO_Interface sidao = (StoreInformationDAO_Interface) context
				.getBean("StoreInformationDAO");
		StoreScoreDAO_Interface dao = (StoreScoreDAO_Interface) context
				.getBean("StoreScoreDAO");
		// 新增
		StoreScore bean1 = new StoreScore();
		Member mbean1 = mdao.findByPrimeKey(1);
		StoreInformation sibean1 = sidao.findByPrimeKey(1);
		bean1.setStoreInformation(sibean1);
		bean1.setMember(mbean1);
		bean1.setStoreScore(8.5);
		bean1.setStoreScoreReason("真不錯!!");
		dao.insert(bean1);
		// 修改
		// StoreScore bean2 = new StoreScore();
		// MemberDAO_interface mdao1 = new MemberDAOHibernate();
		// Member mbean2 = mdao1.findByPrimeKey(1);
		// StoreInformationDAO_Interface sidao1 = new
		// StoreInformationDAOHibernate();
		// StoreInformation sibean2 = sidao1.findByPrimeKey(1);
		// bean2.setStoreScoreId(1);
		// bean2.setStoreInformation(sibean2);
		// bean2.setMember(mbean2);
		// bean2.setStoreScore(0.5);
		// bean2.setStoreScoreReason("真爛!!");
		// dao.insert(bean2);
		// 刪除
		// 查詢單筆
		// 查詢多筆
		List<StoreScore> beans = dao.getAll();
		for (StoreScore vo : beans) {
			System.out.println(vo.getStoreScoreId());
			System.out.println(vo.getStoreInformation().getStoreId());
			System.out.println(vo.getMember().getMemberId());
			System.out.println(vo.getStoreScore());
			System.out.println(vo.getStoreScoreReason());
		}
	}
}
