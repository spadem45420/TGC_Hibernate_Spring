package model.Interface;

import java.util.List;
import java.util.Set;

import model.GroupRoom;
import model.Member;
import model.Member_FavoredType;
import model.StoreScore;
import model.TabuUsernameTable;

public interface MemberDAO_Interface {
	public abstract Member findByPrimeKey(Integer memberId);
	
	public abstract List<Member> findByEmail(String email);
	
	public abstract List<Member> findByUsername(String username);
	
	public abstract List<Member> findByUnknown(String unknown);

	public abstract List<Member> getAll();

	public abstract void insert(Member bean);

	public abstract void update(Member bean);

	public abstract void delete(Integer memberId);

	public abstract Set<TabuUsernameTable> getTabuUsernameTablesByMemberId(
			Integer memberId);

	public abstract Set<StoreScore> getStoreScoreByMemberId(Integer memberId);

	public abstract Set<GroupRoom> getGroupRoomByMemberId(Integer memberId);

	public abstract Set<Member_FavoredType> getMember_FavoredTypeByMemberId(
			Integer memberId);
}
