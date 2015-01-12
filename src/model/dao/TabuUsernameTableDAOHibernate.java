package model.dao;

import java.util.List;

import model.TabuUsernameTable;
import model.Interface.TabuUsernameTableDAO_Interface;

import org.springframework.orm.hibernate3.HibernateTemplate;

public class TabuUsernameTableDAOHibernate implements
		TabuUsernameTableDAO_Interface {
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public TabuUsernameTable findByPrimeKey(Integer tabuId) {
		TabuUsernameTable tabuUsernameTable = (TabuUsernameTable) hibernateTemplate
				.get(TabuUsernameTable.class, tabuId);
		return tabuUsernameTable;
	}

	private static final String GET_ALL_STMT = "from TabuUsernameTable order by tabuId";

	@Override
	public List<TabuUsernameTable> getAll() {
		List<TabuUsernameTable> list = hibernateTemplate.find(GET_ALL_STMT);
		return list;
	}

	@Override
	public void insert(TabuUsernameTable bean) {
		hibernateTemplate.saveOrUpdate(bean);
	}

	@Override
	public void update(TabuUsernameTable bean) {
		hibernateTemplate.saveOrUpdate(bean);
	}

	@Override
	public void delete(Integer tabuId) {
		TabuUsernameTable tabuUsernameTable = (TabuUsernameTable) hibernateTemplate
				.get(TabuUsernameTable.class, tabuId);
		hibernateTemplate.delete(tabuUsernameTable);
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
