package model.Interface;

import java.util.List;

import model.RentalTime;

public interface RentalTimeDAO_Interface {
	public abstract RentalTime findByPrimeKey(Integer storeId);

	public abstract List<RentalTime> getAll();

	public abstract void insert(RentalTime rtbean);

	public abstract void update(RentalTime rtbean);

	public abstract void delete(Integer storeId);
}
