package model.dao;

import java.util.List;

import model.RentalTime;
import model.StoreInformation;
import model.Interface.BoardGameKindDAO_Interface;
import model.Interface.RentalTimeDAO_Interface;
import model.Interface.StoreInformationDAO_Interface;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class RentalTimeDAOHibernate implements RentalTimeDAO_Interface {
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public RentalTime findByPrimeKey(Integer storeId) {
		RentalTime rentalTime = (RentalTime) hibernateTemplate.get(
				RentalTime.class, storeId);
		return rentalTime;
	}

	private static final String GET_ALL_STMT = "from RentalTime order by storeId";

	@Override
	public List<RentalTime> getAll() {
		List<RentalTime> list = hibernateTemplate.find(GET_ALL_STMT);
		return list;
	}

	@Override
	public void insert(RentalTime rtbean) {
		hibernateTemplate.saveOrUpdate(rtbean);
	}

	@Override
	public void update(RentalTime rtbean) {
		hibernateTemplate.saveOrUpdate(rtbean);
	}

	@Override
	public void delete(Integer storeId) {
		RentalTime rentalTime = (RentalTime) hibernateTemplate.get(
				RentalTime.class, storeId);
		hibernateTemplate.delete(rentalTime);
	}

	public static void main(String[] args) {
		// RentalTimeDAO_Interface dao = new RentalTimeDAOHibernate();
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"model-config1-DriverManagerDataSource.xml");
		RentalTimeDAO_Interface dao = (RentalTimeDAO_Interface) context
				.getBean("RentalTimeDAO");
		// 新增
		RentalTime bean1 = new RentalTime();
		StoreInformationDAO_Interface sidao = (StoreInformationDAO_Interface) context
				.getBean("StoreInformationDAO");
		StoreInformation sibean1 = sidao.findByPrimeKey(1);
		bean1.setStoreInformation(sibean1);
		// 設定時間
		// 平日
		java.sql.Time weekdayStart = java.sql.Time.valueOf("13:00:00");
		java.sql.Time weekdayEnd = java.sql.Time.valueOf("22:00:00");
		// 假日
		java.sql.Time holidayStart = java.sql.Time.valueOf("10:00:00");
		java.sql.Time holidayEnd = java.sql.Time.valueOf("22:00:00");
		// 星期一
		bean1.setMonStart(weekdayStart);
		bean1.setMonEnd(weekdayEnd);
		// 星期二
		bean1.setTueStart(null);
		bean1.setTueEnd(null);
		// 星期三
		bean1.setWedStart(weekdayStart);
		bean1.setWedEnd(weekdayEnd);
		// 星期四
		bean1.setThuStart(weekdayStart);
		bean1.setThuEnd(weekdayEnd);
		// 星期五
		bean1.setFriStart(weekdayStart);
		bean1.setFriEnd(weekdayEnd);
		// 星期六
		bean1.setSatStart(holidayStart);
		bean1.setSatEnd(holidayEnd);
		// 星期日
		bean1.setSunStart(holidayStart);
		bean1.setSunEnd(holidayEnd);
		dao.insert(bean1);
		// 修改
		// RentalTimeVO bean2 = new RentalTimeVO();
		// StoreInformationDAO_Interface sidao1 = new
		// StoreInformationDAOHibernate();
		// StoreInformationVO sibean2 = sidao1.findByPrimeKey(1);
		// bean2.setStoreInformation(sibean2);
		// bean2.setStoreId(1);
		// // 設定時間
		// // 平日
		// java.sql.Time weekdayStart_Update =
		// java.sql.Time.valueOf("13:00:00");
		// java.sql.Time weekdayEnd_Update = java.sql.Time.valueOf("22:00:00");
		// // 假日
		// java.sql.Time holidayStart_Update =
		// java.sql.Time.valueOf("10:00:00");
		// java.sql.Time holidayEnd_Update = java.sql.Time.valueOf("22:00:00");
		// // 星期一
		// bean2.setMonStart(weekdayStart_Update);
		// bean2.setMonEnd(weekdayEnd_Update);
		// // 星期二
		// bean2.setTueStart(null);
		// bean2.setTueEnd(null);
		// // 星期三
		// bean2.setWedStart(weekdayStart_Update);
		// bean2.setWedEnd(weekdayEnd_Update);
		// // 星期四
		// bean2.setThuStart(weekdayStart_Update);
		// bean2.setThuEnd(weekdayEnd_Update);
		// // 星期五
		// bean2.setFriStart(weekdayStart_Update);
		// bean2.setFriEnd(weekdayEnd_Update);
		// // 星期六
		// bean2.setSatStart(holidayStart_Update);
		// bean2.setSatEnd(holidayEnd_Update);
		// // 星期日
		// bean2.setSunStart(holidayStart_Update);
		// bean2.setSunEnd(holidayEnd_Update);
		// dao.update(bean2);
		// 刪除
		// dao.delete(1);
		// 查詢單筆
		RentalTime b1 = dao.findByPrimeKey(1);
		System.out.println(b1.getStoreId());
		// 查詢多筆
		List<RentalTime> beans = dao.getAll();
		for (RentalTime vo : beans) {
			System.out.println(vo.getStoreId());
			System.out.println("MonStart: "
					+ new java.sql.Time(vo.getMonStart().getTime()));
			System.out.println("MonEnd: "
					+ new java.sql.Time(vo.getMonEnd().getTime()));
			/*
			 * 因為null值有問題先Mark起來 System.out.println("TueStart: "+new
			 * java.sql.Time(vo.getTueStart().getTime()));
			 * System.out.println("TueEnd: "+new
			 * java.sql.Time(vo.getTueEnd().getTime()));
			 */
			System.out.println("WedStart: "
					+ new java.sql.Time(vo.getWedStart().getTime()));
			System.out.println("WedEnd: "
					+ new java.sql.Time(vo.getWedEnd().getTime()));
			System.out.println("ThuStart: "
					+ new java.sql.Time(vo.getThuStart().getTime()));
			System.out.println("ThuEnd: "
					+ new java.sql.Time(vo.getThuEnd().getTime()));
			System.out.println("FriStart: "
					+ new java.sql.Time(vo.getFriStart().getTime()));
			System.out.println("FriEnd: "
					+ new java.sql.Time(vo.getFriEnd().getTime()));
			System.out.println("SatStart: "
					+ new java.sql.Time(vo.getSatStart().getTime()));
			System.out.println("SatEnd: "
					+ new java.sql.Time(vo.getSatEnd().getTime()));
			System.out.println("SunStart: "
					+ new java.sql.Time(vo.getSunStart().getTime()));
			System.out.println("SunEnd: "
					+ new java.sql.Time(vo.getSunEnd().getTime()));
		}
	}
}
