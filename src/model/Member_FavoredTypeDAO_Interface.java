package model;

import java.util.List;

public interface Member_FavoredTypeDAO_Interface {
	public abstract Member_FavoredType findByPrimeKey(Integer memberFavoredId);

	public abstract List<Member_FavoredType> getAll();

	public abstract void insert(Member_FavoredType bean);

	public abstract void update(Member_FavoredType bean);

	public abstract void delete(Integer memberFavoredId);

}
