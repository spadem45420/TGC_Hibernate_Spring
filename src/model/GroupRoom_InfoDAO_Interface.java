package model;

import java.util.List;

public interface GroupRoom_InfoDAO_Interface {
	public abstract GroupRoom_Info findByPrimeKey(String groupSerialNumber);

	public abstract List<GroupRoom_Info> getAll();

	public abstract void insert(GroupRoom_Info bean);

	public abstract void update(GroupRoom_Info bean);

	public abstract void delete(Integer groupSerialNumber);
}
