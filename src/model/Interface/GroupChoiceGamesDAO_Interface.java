package model.Interface;

import java.util.List;

import model.GroupChoiceGames;

public interface GroupChoiceGamesDAO_Interface {
	public abstract GroupChoiceGames findByPrimeKey(Integer choiceGamesSerialNumber);

	public abstract List<GroupChoiceGames> getAll();

	public abstract void insert(GroupChoiceGames bean);

	public abstract void update(GroupChoiceGames bean);

	public abstract void delete(String choiceGamesSerialNumber);
}
