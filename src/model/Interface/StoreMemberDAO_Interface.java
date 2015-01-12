package model.Interface;

import java.util.List;
import java.util.Set;

import model.BoardGames;
import model.GroupRoom;
import model.StoreInformation;
import model.StoreMember;

public interface StoreMemberDAO_Interface {
	public abstract StoreMember findByPrimeKey(Integer storeMemberId);

	public List<StoreMember> findByUsername(String storeUsername);
	
	public abstract List<StoreMember> getAll();

	public abstract void insert(StoreMember bean);

	public abstract void update(StoreMember bean);

	public abstract void delete(Integer storeMemberId);

	public abstract Set<StoreInformation> getStoreInformationsByStoreMemberId(
			Integer storeMemberId);

	public abstract Set<GroupRoom> getGroupRoomsByStoreMemberId(
			Integer storeMemberId);
}
