package model.Interface;

import java.util.List;

import model.Joiner_Info;

public interface Joiner_InfoDAO_Interface {
	public abstract Joiner_Info findByPrimeKey(Integer groupSerialNumber);
	
	public abstract List<Joiner_Info> findByUsername(String username);

	public abstract List<Joiner_Info> findByGroupSerialNumber(
			Integer groupSerialNumber);

	public abstract List<Joiner_Info> getAll();

	public abstract void insert(Joiner_Info bean);

	public abstract void update(Joiner_Info bean);

	public abstract void delete(Integer joiner_InfoSerialNumber);
}
