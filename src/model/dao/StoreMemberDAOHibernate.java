package model.dao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import model.BoardGames;
import model.GroupRoom;
import model.StoreInformation;
import model.StoreMember;
import model.Interface.AdministratorDAO_Interface;
import model.Interface.StoreMemberDAO_Interface;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class StoreMemberDAOHibernate implements StoreMemberDAO_Interface {
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public StoreMember findByPrimeKey(Integer storeMemberId) {
		StoreMember storeMember = (StoreMember) hibernateTemplate.get(
				StoreMember.class, storeMemberId);
		return storeMember;
	}
	
	public List<StoreMember> findByUsername(String storeUsername) {
		List<StoreMember> list = null;
		StoreMember bean = new StoreMember();
		bean.setStoreUsername(storeUsername);
		list = hibernateTemplate.findByExample(bean);
		return list;
	}

	private static final String GET_ALL_STMT = "from StoreMember order by storeMemberId";

	@Override
	public List<StoreMember> getAll() {
		List<StoreMember> list = hibernateTemplate.find(GET_ALL_STMT);
		return list;
	}

	@Override
	public void insert(StoreMember bean) {
		hibernateTemplate.saveOrUpdate(bean);
	}

	@Override
	public void update(StoreMember bean) {
		hibernateTemplate.saveOrUpdate(bean);
	}

	@Override
	public void delete(Integer storeMemberId) {
		StoreMember storeMember = (StoreMember) hibernateTemplate.get(
				StoreMember.class, storeMemberId);
		hibernateTemplate.delete(storeMember);
	}

	public static void main(String[] args) {
		// StoreMemberDAO_Interface dao = new StoreMemberDAOHibernate();
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"model-config1-DriverManagerDataSource.xml");
		StoreMemberDAO_Interface dao = (StoreMemberDAO_Interface) context
				.getBean("StoreMemberDAO");
		// 新增
		StoreMember bean1 = new StoreMember();
		bean1.setStoreUsername("littleworld");
		bean1.setStorePswd("Zz#littleworld".getBytes());
		bean1.setStoreJoinDate(java.sql.Date.valueOf("2007-6-13"));
		bean1.setStorePhone("0920949521");
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
		bean1.setStoreEmail("littleworld@gmail.com");
		bean1.setStoreWebsite("www.littleworld.com");
		dao.insert(bean1);

		StoreMember bean2 = new StoreMember();
		bean2.setStoreUsername("Hinforms");
		bean2.setStorePswd("Hinforms".getBytes());
		bean2.setStoreJoinDate(java.sql.Date.valueOf("2007-5-22"));
		bean2.setStorePhone("0968190017");
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
			bean2.setStoreImage(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		bean2.setStoreEmail("Hinforms@gmail.com");
		bean2.setStoreWebsite("www.Hinforms.com");
		dao.insert(bean2);
		// 修改
		// StoreMemberVO bean3 = new StoreMemberVO();
		// bean3.setStoreMemberId(1);
		// bean3.setStoreUsername("Sairing");
		// bean3.setStorePswd("Sairing".getBytes());
		// bean3.setStoreJoinDate(java.sql.Date.valueOf("2009-6-11"));
		// bean3.setStorePhone("0960596493");
		// bean3.setStoreEmail("Sairing@gmail.com");
		// bean3.setStoreWebsite("www.Sairing.com");
		// dao.update(bean3);
		// 刪除
		// dao.delete(1);
		// 查詢單筆
		// StoreMemberVO b1 = dao.findByPrimeKey(1);
		// System.out.println(b1.getStoreUsername());
		// 查詢多筆
		List<StoreMember> beans = dao.getAll();
		for (StoreMember vo : beans) {
			System.out.println(vo.getStoreMemberId());
			System.out.println(vo.getStoreUsername());
		}
	}

	@Override
	public Set<StoreInformation> getStoreInformationsByStoreMemberId(
			Integer storeMemberId) {
		Set<StoreInformation> set = findByPrimeKey(storeMemberId)
				.getStoreInformations();
		return set;
	}

	@Override
	public Set<GroupRoom> getGroupRoomsByStoreMemberId(Integer storeMemberId) {
		Set<GroupRoom> set = findByPrimeKey(storeMemberId).getGroupRooms();
		return set;
	}
}
