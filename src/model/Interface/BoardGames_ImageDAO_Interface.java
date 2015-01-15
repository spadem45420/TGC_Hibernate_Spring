package model.Interface;

import java.util.List;

import model.BoardGames;
import model.BoardGames_Image;

public interface BoardGames_ImageDAO_Interface {
	public abstract BoardGames_Image findByPrimeKey(Integer storeImageId);

	public abstract List<BoardGames_Image> getAll();

	public abstract void insert(BoardGames_Image bean);

	public abstract void update(BoardGames_Image bean);

	public abstract void delete(Integer storeImageId);
}
