package model.Interface;

import java.util.List;

import model.StoreScore;

public interface StoreScoreDAO_Interface {
	public abstract StoreScore findByPrimeKey(Integer storeScoreId);
	
	public List<StoreScore> findByStoreId(Integer storeId);

	public abstract List<StoreScore> getAll();

	public abstract void insert(StoreScore ssbean);

	public abstract void update(StoreScore ssbean);

	public abstract void delete(Integer storeId);
}
