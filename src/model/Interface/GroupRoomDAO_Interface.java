package model.Interface;

import java.util.List;
import java.util.Set;

import model.GroupChoiceGames;
import model.GroupRoom;
import model.GroupRoom_Info;
import model.GroupRoom_Message;
import model.Joiner_Info;

public interface GroupRoomDAO_Interface {
	public abstract GroupRoom findByPrimeKey(Integer groupSerialNumber);
	
	public abstract List<GroupRoom> findByGroupRoomName(String groupRoomName);
	
	public abstract List<GroupRoom> findByUnknown(String unknown);

	public abstract List<GroupRoom> getAll();

	public abstract void insert(GroupRoom bean);

	public abstract void update(GroupRoom bean);

	public abstract void delete(Integer groupSerialNumber);

	public abstract Set<GroupRoom_Info> getGroupRoom_InfoByGroupSerialNumber(
			Integer groupSerialNumber);

	public abstract Set<Joiner_Info> getJoiner_InfoByGroupSerialNumber(
			Integer groupSerialNumber);

	public abstract Set<GroupRoom_Message> getGroupRoom_MessageByGroupSerialNumber(
			Integer groupSerialNumber);

	public abstract Set<GroupChoiceGames> getGroupChoiceGamesByGroupSerialNumber(
			Integer groupSerialNumber);
}
