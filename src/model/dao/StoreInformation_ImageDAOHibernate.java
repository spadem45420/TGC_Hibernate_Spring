package model.dao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import model.StoreInformation;
import model.StoreInformation_Image;
import model.Interface.BoardGameKindDAO_Interface;
import model.Interface.StoreInformationDAO_Interface;
import model.Interface.StoreInformation_ImageDAO_Interface;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class StoreInformation_ImageDAOHibernate implements
		StoreInformation_ImageDAO_Interface {
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public StoreInformation_Image findByPrimeKey(Integer storeImageId) {
		StoreInformation_Image storeInformationImage = (StoreInformation_Image) hibernateTemplate
				.get(StoreInformation_Image.class, storeImageId);
		return storeInformationImage;
	}

	private static final String GET_ALL_STMT = "from StoreInformation_Image order by storeImageId";

	@Override
	public List<StoreInformation_Image> getAll() {
		List<StoreInformation_Image> list = hibernateTemplate
				.find(GET_ALL_STMT);
		return list;
	}

	@Override
	public void update(StoreInformation_Image sibean) {
		hibernateTemplate.saveOrUpdate(sibean);
	}

	@Override
	public void insert(StoreInformation_Image sibean) {
		hibernateTemplate.saveOrUpdate(sibean);
	}

	@Override
	public void delete(Integer storeImageId) {
		StoreInformation_Image storeInformationImage = (StoreInformation_Image) hibernateTemplate
				.get(StoreInformation_Image.class, storeImageId);
		hibernateTemplate.delete(storeInformationImage);
	}

	public static void main(String[] args) {
		// StoreInformation_ImageDAO_Interface dao = new
		// StoreInformation_ImageDAOHibernate();
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"model-config1-DriverManagerDataSource.xml");
		StoreInformation_ImageDAO_Interface dao = (StoreInformation_ImageDAO_Interface) context
				.getBean("StoreInformation_ImageDAO");
		// 新增
		StoreInformation_Image bean1 = new StoreInformation_Image();
		StoreInformationDAO_Interface sidao = (StoreInformationDAO_Interface) context
				.getBean("StoreInformationDAO");
		StoreInformation sibean1 = sidao.findByPrimeKey(1);
		bean1.setStoreInformation(sibean1);
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
			bean1.setAreaImage(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		dao.insert(bean1);
		// 修改
		// StoreInformation_ImageVO bean2 = new StoreInformation_ImageVO();
		// StoreInformationDAO_Interface sidao1 = new
		// StoreInformationDAOHibernate();
		// StoreInformationVO sibean2 = sidao1.findByPrimeKey(1);
		// bean2.setStoreInformation(sibean2);
		// bean2.setStoreImageId(2);
		// String filename2 = "boardgames-1.jpg";
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
		// bean2.setAreaImage(data);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// dao.update(bean2);
		// 刪除
		// dao.delete(1);
		// 查詢單筆
		// StoreInformation_ImageVO b1 = dao.findByPrimeKey(1);
		// System.out.println(b1.getImgFileName());
		// 查詢多筆
		List<StoreInformation_Image> beans = dao.getAll();
		for (StoreInformation_Image vo : beans) {
			System.out.println(vo.getStoreInformation().getStoreId());
			System.out.println(vo.getStoreImageId());
			System.out.println(vo.getImgFileName());
		}
	}
}
