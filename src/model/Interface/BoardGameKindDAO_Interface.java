package model.Interface;

import java.util.List;
import java.util.Set;

import model.BoardGameKind;
import model.BoardGames;
import model.GroupChoiceGames;

public interface BoardGameKindDAO_Interface {
	public abstract BoardGameKind findByPrimeKey(Integer boardGameSerialNumber);

	public abstract List<BoardGameKind> getAll();

	public abstract void insert(BoardGameKind bean);

	public abstract void update(BoardGameKind bean);

	public abstract void delete(Integer boardGameSerialNumber);

	public abstract Set<GroupChoiceGames> getGroupChoiceGamesByBoardGameSerialNumber(
			Integer boardGameSerialNumber);

	public abstract Set<BoardGames> getBoardGamesByBoardGameSerialNumber(
			Integer boardGameSerialNumber);
}
