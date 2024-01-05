package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utils.DBUtil;

public class MemberDAOImpl implements MemberDAO{

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		@Override
		public boolean join(Member_DO member) {
			conn= DBUtil.getConnection();
			String sql = "";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(0, null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
		@Override
		public Member_DO login(String id, String pass) {
			
			return null;
		}
	
}
			
			
			
			
