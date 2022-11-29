package lab9;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	
	public static Connection conn = null;
	
	public static void main(String [] args)
	{
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/newSchkema?user=root&password=Shibani23k");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		method1();
		System.out.println();
		method2();
	}
	
	public static void method1()
	{
		Statement st = null;
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = st.executeQuery("SELECT ClassName, COUNT(*) FROM Grades GROUP BY ClassName ORDER BY COUNT(*) ASC;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		try {
			while (rs.next())
			{
				System.out.println(rs.getString("ClassName") + "     " + rs.getString("COUNT(*)"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void method2()
	{
		Statement st = null;
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = st.executeQuery("SELECT g.ClassName, s.Name, g.Grade From Grades g, studInfo s WHERE s.SID=g.SID ORDER BY s.Name DESC");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		try {
			while (rs.next())
			{
				System.out.println(rs.getString("ClassName") + "     " + rs.getString("Name") + "    " + rs.getString("Grade"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
