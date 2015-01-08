package model;

import java.util.List;

public interface StoreInformation_ImageDAO_Interface {
	public abstract StoreInformation_Image findByPrimeKey(Integer storeImageId);

	public abstract List<StoreInformation_Image> getAll();

	public abstract void update(StoreInformation_Image sibean);

	public abstract void insert(StoreInformation_Image sibean);

	public abstract void delete(Integer storeImageId);
}
