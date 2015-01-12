package model.dao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import model.Administrator;
import model.Interface.AdministratorDAO_Interface;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class AdministratorDAOHibernate implements AdministratorDAO_Interface {
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public Administrator findByPrimeKey(Integer administratorId) {
		Administrator administrator = (Administrator) hibernateTemplate.get(
				Administrator.class, administratorId);
		return administrator;
	}

	private static final String GET_ALL_STMT = "from Administrator order by adminUsername";

	@Override
	public List<Administrator> getAll() {
		List<Administrator> list = hibernateTemplate.find(GET_ALL_STMT);
		return list;
	}

	@Override
	public void insert(Administrator bean) {
		hibernateTemplate.saveOrUpdate(bean);
	}

	@Override
	public void update(Administrator bean) {
		hibernateTemplate.saveOrUpdate(bean);
	}

	@Override
	public void delete(Integer administratorId) {
		Administrator administrator = (Administrator) hibernateTemplate.get(
				Administrator.class, administratorId);
		hibernateTemplate.delete(administrator);
	}

	public static void main(String[] args) {
		// AdministratorDAO_Interface dao = new AdministratorDAOHibernate();
		// 為方便一般應用程式main方的測試,所以底下的beans-config.xml內部dataSource設定是採用org.springframework.jdbc.datasource.DriverManagerDataSource
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"model-config1-DriverManagerDataSource.xml");
		AdministratorDAO_Interface dao = (AdministratorDAO_Interface) context
				.getBean("AdministratorDAO");
		// 新增
		Administrator bean1 = new Administrator();
		bean1.setAdminUsername("Admin001");
		bean1.setAdminPswd("admin001".getBytes());
		bean1.setImgFileName("boardgames.jpg");
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
			bean1.setAdminMemberImage(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		dao.insert(bean1);

		Administrator bean2 = new Administrator();
		bean2.setAdminUsername("Admin002");
		bean2.setAdminPswd("admin001".getBytes());
		bean2.setImgFileName("boardgames.jpg");
		String filename2 = "boardgames.jpg";
		bean2.setImgFileName(filename2);
		File f1 = new File("WebContent/res/" + bean2.getImgFileName());
		try {
			InputStream is = new FileInputStream(f1);
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int nRead;
			byte[] data = new byte[1024];
			while ((nRead = is.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
				buffer.flush();
			}
			data = buffer.toByteArray();
			is.close();
			bean2.setAdminMemberImage(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		dao.insert(bean2);
		// 修改
		// AdministratorVO bean3 = new AdministratorVO();
		// bean3.setAdministratorId(1);
		// bean3.setAdminUsername("Admin003");
		// dao.update(bean3);
		// 刪除
		// dao.delete(1);
		// 查詢單筆
		// AdministratorVO bean = dao.findByPrimeKey(1);
		// System.out.println(bean.getAdminUsername());
		// 查詢多筆
		List<Administrator> beans = dao.getAll();
		for (Administrator vo : beans) {
			System.out.println(vo.getAdminUsername());
		}
	}
}
