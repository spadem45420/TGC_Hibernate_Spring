package StoreMember;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.StoreMember;
import model.StoreMemberDAO_Interface;
import model.dao.StoreMemberDAOHibernate;

public class StoreMemberService {
	ApplicationContext context = new ClassPathXmlApplicationContext(
			"model-config1-DriverManagerDataSource.xml");
	StoreMemberDAO_Interface dao = (StoreMemberDAO_Interface) context
			.getBean("StoreMemberDAO");

	public List<StoreMember> select(StoreMember bean) {
		List<StoreMember> result = null;
		if (bean != null && bean.getStoreUsername() != null) {
			List<StoreMember> temp = dao.findByUsername(bean.getStoreUsername());
			if (temp != null) {
				result = temp;
			}
		} else {
			return null;
		}
		return result;
	}

	public String register(StoreMember bean) {
		if (bean != null) {
			dao.insert(bean);
			return "register success!";
		}
		return "register fail!";
	}

	public String update(StoreMember bean) {
		if (bean != null) {
			dao.update(bean);
			return "update success!";
		}
		return "update fail!";
	}

	public static void main(String[] args) {
		StoreMemberService service = new StoreMemberService();

		// Insert
//		StoreMember bean1 = new StoreMember();
//		try {
//			File f = new File("WebContent/res/boardgames.jpg");
//			long size = 0;
//			InputStream is = null;
//			try {
//			size = f.length();
//			is = new FileInputStream(f);
//			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//			int nRead;
//			byte[] data = new byte[1024];
//			while ((nRead = is.read(data, 0, data.length)) != -1) {
//			  buffer.write(data, 0, nRead);
//			  buffer.flush();
//			}
//			data = buffer.toByteArray();
//			is.close();
//			bean1.setStoreImage(data);
//			} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		bean1.setStoreUsername("littleworld");
//		bean1.setStorePswd("Zz#littleworld".getBytes());
//		bean1.setStoreJoinDate(java.sql.Date.valueOf("2007-6-13"));
//		bean1.setStorePhone("0920949521");
//		bean1.setImgFileName("boardgames.jpg");
//		bean1.setStoreEmail("littleworld@gmail.com");
//		bean1.setStoreWebsite("www.littleworld.com");
//		service.register(bean1);
		
		// Update
		StoreMember bean1 = new StoreMember();
		try {
			File f = new File("WebContent/res/boardgames.jpg");
			long size = 0;
			InputStream is = null;
			try {
			size = f.length();
			is = new FileInputStream(f);
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
			} catch (FileNotFoundException e) {
			e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bean1.setStoreMemberId(9);;
		bean1.setStoreUsername("littleworld");
		bean1.setStorePswd("Zz#littleworld".getBytes());
		bean1.setStoreJoinDate(java.sql.Date.valueOf("2007-6-14"));
		bean1.setStorePhone("0920949521");
		bean1.setImgFileName("boardgames.jpg");
		bean1.setStoreEmail("littleworld@gmail.com");
		bean1.setStoreWebsite("www.littleworld.com");
		service.update(bean1);
		
		
		
		// Select All
//		StoreMember bean2 = new StoreMember();
//		bean2.setStoreUsername("littleworld");
//		List<StoreMember> list = service.select(bean2);
//		for(StoreMember vo : list){
//			System.out.println(vo.getStoreUsername());
//		}
	}
}
