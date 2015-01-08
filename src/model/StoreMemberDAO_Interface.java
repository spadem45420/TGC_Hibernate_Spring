package model;

import java.util.List;
import java.util.Set;

public interface StoreMemberDAO_Interface {
	public abstract StoreMember findByPrimeKey(Integer storeMemberId);

	public abstract List<StoreMember> getAll();

	public abstract void insert(StoreMember bean);

	public abstract void update(StoreMember bean);

	public abstract void delete(Integer storeMemberId);

	public abstract Set<BoardGames> getBoardGamesesByStoreMemberId(
			Integer storeMemberId);

	public abstract Set<StoreInformation> getStoreInformationsByStoreMemberId(
			Integer storeMemberId);

	public abstract Set<GroupRoom> getGroupRoomsByStoreMemberId(
			Integer storeMemberId);

	public abstract List<StoreMember> findByUsername(String storeUsername);
}
