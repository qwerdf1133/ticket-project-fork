package login;

public interface MemberDAO {
	
	/**
	 * @Param  : 
	 * @return  : 
	 */
	boolean join(Member_DO member);
	
	/**
	 * @param id
	 * @param pass
	 * @return
	 * SELECT * FROM member WHERE id = ? AND pass = ?
	 */
	Member_DO login(String id, String pass);

}
