package model;

import java.util.List;

public interface TabuUsernameTableDAO_Interface {
	public abstract TabuUsernameTable findByPrimeKey(Integer tabuId);

	public abstract List<TabuUsernameTable> getAll();

	public abstract void insert(TabuUsernameTable bean);

	public abstract void update(TabuUsernameTable bean);

	public abstract void delete(Integer tabuId);

}
