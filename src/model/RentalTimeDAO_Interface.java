package model;

import java.util.List;

public interface RentalTimeDAO_Interface {
	public abstract RentalTime findByPrimeKey(Integer storeId);

	public abstract List<RentalTime> getAll();

	public abstract void insert(RentalTime rtbean);

	public abstract void update(RentalTime rtbean);

	public abstract void delete(Integer storeId);
}
