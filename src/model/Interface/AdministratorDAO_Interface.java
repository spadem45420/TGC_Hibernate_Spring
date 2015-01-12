package model.Interface;

import java.util.List;

import model.Administrator;

public interface AdministratorDAO_Interface {
	public abstract Administrator findByPrimeKey(Integer administratorId);

	public abstract List<Administrator> getAll();

	public abstract void insert(Administrator bean);

	public abstract void update(Administrator bean);

	public abstract void delete(Integer administratorId);
}
