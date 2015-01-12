package model.Interface;

import java.util.List;
import java.util.Set;

import model.BoardGames;
import model.BoardGames_Image;

public interface BoardGamesDAO_Interface {
	public abstract BoardGames findByPrimeKey(Integer boardGamesId);

	public abstract List<BoardGames> getAll();

	public abstract void insert(BoardGames bean);

	public abstract void update(BoardGames bean);

	public abstract void delete(Integer boardGamesId);

	public abstract Set<BoardGames_Image> getBoardGames_ImageByBoardGamesId(
			Integer boardGamesId);
}
