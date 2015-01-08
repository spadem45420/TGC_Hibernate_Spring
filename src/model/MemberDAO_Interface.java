package model;

import java.util.List;
import java.util.Set;

public interface MemberDAO_Interface {
	public abstract Member findByPrimeKey(Integer memberId);

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
