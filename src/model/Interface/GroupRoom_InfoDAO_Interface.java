package model.Interface;

import java.util.List;

import model.GroupRoom_Info;

public interface GroupRoom_InfoDAO_Interface {
	public abstract GroupRoom_Info findByPrimeKey(Integer groupSerialNumber);

	public abstract List<GroupRoom_Info> getAll();

	public abstract void insert(GroupRoom_Info bean);

	public abstract void update(GroupRoom_Info bean);

	public abstract void delete(Integer groupSerialNumber);
}
