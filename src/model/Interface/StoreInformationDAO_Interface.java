package model.Interface;

import java.util.List;
import java.util.Set;

import model.StoreInformation;
import model.StoreInformation_Image;

public interface StoreInformationDAO_Interface {
	public abstract StoreInformation findByPrimeKey(Integer storeId);

	public abstract List<StoreInformation> findByStoreName(String storeName);
	
	public abstract List<StoreInformation> getAll();

	public abstract void insert(StoreInformation bean);

	public abstract void update(StoreInformation bean);

	public abstract void delete(Integer storeId);

	public abstract Set<StoreInformation_Image> getStoreInformation_ImageByStoreId(
			Integer storeId);
}
