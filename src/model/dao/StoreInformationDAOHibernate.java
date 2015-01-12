package model.dao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import model.StoreInformation;
import model.StoreInformation_Image;
import model.StoreMember;
import model.Interface.StoreInformationDAO_Interface;
import model.Interface.StoreMemberDAO_Interface;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class StoreInformationDAOHibernate implements
		StoreInformationDAO_Interface {
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public StoreInformation findByPrimeKey(Integer storeId) {
		StoreInformation storeInformation = (StoreInformation) hibernateTemplate
				.get(StoreInformation.class, storeId);
		return storeInformation;
	}

	@Override
	public List<StoreInformation> findByStoreName(String storeName) {
		StoreInformation storeInformation = new StoreInformation();
		storeInformation.setStoreName(storeName);
		List<StoreInformation> list = hibernateTemplate
				.findByExample(storeInformation);
		return list;
	}

	private static final String GET_ALL_STMT = "from StoreInformation order by storeId";

	@Override
	public List<StoreInformation> getAll() {
		List<StoreInformation> list = hibernateTemplate.find(GET_ALL_STMT);
		return list;
	}

	@Override
	public void insert(StoreInformation bean) {
		hibernateTemplate.saveOrUpdate(bean);
	}

	@Override
	public void update(StoreInformation bean) {
		hibernateTemplate.saveOrUpdate(bean);
	}

	@Override
	public void delete(Integer storeId) {
		StoreInformation storeInformation = (StoreInformation) hibernateTemplate
				.get(StoreInformation.class, storeId);
		hibernateTemplate.delete(storeInformation);
	}

	public static void main(String[] args) {
		// StoreInformationDAO_Interface dao = new
		// StoreInformationDAOHibernate();
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"model-config1-DriverManagerDataSource.xml");
		StoreInformationDAO_Interface dao = (StoreInformationDAO_Interface) context
				.getBean("StoreInformationDAO");
		// 新增
		StoreInformation bean1 = new StoreInformation();
		StoreMemberDAO_Interface SMdao = (StoreMemberDAO_Interface) context
				.getBean("StoreMemberDAO");
		StoreMember smbean1 = SMdao.findByPrimeKey(1);
		bean1.setStoreMember(smbean1);
		bean1.setStoreName("瘋桌遊-益智遊戲專賣店(汐止店)");
		bean1.setStoreAddress("新北市汐止區仁愛路160號");
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
			bean1.setStoreImage(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		bean1.setStoreTel("(02)2643-8686");
		bean1.setRentAreaCost(120.0);
		bean1.setGroupUpperLimit(50);
		dao.insert(bean1);
		// 修改
		// StoreInformation bean3 = new StoreInformation();
		// SMdao = new StoreMemberDAOHibernate();
		// smbean1 = SMdao.findByPrimeKey(1);
		// bean3.setStoreMember(smbean1);
		// bean3.setStoreId(1);
		// bean3.setStoreName("卡牌屋-台北店");
		// bean3.setStoreAddress("台北市開封街一段19號2樓");
		// dao.update(bean3);
		// 刪除
		// dao.delete(1);
		// 查詢單筆
		// StoreInformation b1 = dao.findByPrimeKey(1);
		// System.out.println(b1.getStoreName());
		List<StoreInformation> b2 = dao.findByStoreName("瘋桌遊-益智遊戲專賣店(汐止店)");
		for (StoreInformation vo : b2) {
			System.out.println(vo.getStoreName());
		}
		// 查詢多筆
		List<StoreInformation> beans = dao.getAll();
		for (StoreInformation vo : beans) {
			System.out.println(vo.getStoreMember().getStoreMemberId());
			System.out.println(vo.getStoreName());
			System.out.println(vo.getStoreAddress());
		}
	}

	@Override
	public Set<StoreInformation_Image> getStoreInformation_ImageByStoreId(
			Integer storeId) {
		Set<StoreInformation_Image> set = findByPrimeKey(storeId)
				.getStoreInformationImages();
		return set;
	}
}
