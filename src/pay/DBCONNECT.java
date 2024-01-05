package pay;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBCONNECT {
	
	private static Connection conn;
	
		public static Connection getConnect () {
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost/dummyticket","root","1234");
				
				System.out.println("연결");
				
				return conn;
				
				
				
			} catch (Exception e) {
				return null;
			}
			
		}
		

}
