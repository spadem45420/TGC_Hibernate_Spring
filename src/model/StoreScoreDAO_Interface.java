package model;

import java.util.List;

public interface StoreScoreDAO_Interface {
	public abstract StoreScore findByPrimeKey(Integer storeId);

	public abstract List<StoreScore> getAll();

	public abstract void insert(StoreScore ssbean);

	public abstract void update(StoreScore ssbean);

	public abstract void delete(Integer storeId);
}
