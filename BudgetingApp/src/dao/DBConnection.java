package dao;

import java.sql.*;
import model.Expenditure;
import java.util.*;

public class DBConnection {
	
	static String dbUrl = "jdbc:mysql://localhost:3306/users";
	static String user = "root";
	static String password = "";
	static Connection myConn;
		
	public static String getPassword(String username) {
		
		String string_pw = "";
		try {
			myConn = DriverManager.getConnection(dbUrl, user, password);
		
			// create a statement
			PreparedStatement pstmt = myConn.prepareStatement("select password from students where username = ?");
			pstmt.setString(1, username);
			
			ResultSet resultSet = pstmt.executeQuery();
				//System.out.println("username: " + username);
		if (resultSet.next()) {
			string_pw = resultSet.getString("password");
				//System.out.println("resultSet.getString(password): " + resultSet.getString("password"));
		 }
			pstmt.close();
			myConn.close();	
			
		}catch(Exception e){
			e.printStackTrace();	
		}	
		return string_pw;
	}
	
	public static void expenditure(String date, String day,
										String item, String cost){
		
		String expenditure
		= "INSERT INTO expenditure (date, day, item, cost) VALUES (?,?,?,?)";
        
		try {
			myConn = DriverManager.getConnection(dbUrl, user, password);
		
			PreparedStatement pstmt = myConn.prepareStatement(expenditure);
			pstmt.setString(1, date);
			pstmt.setString(2, day);
			pstmt.setString(3, item);
			pstmt.setString(4, cost);
			
/*			System.out.println("date: " + date);
			System.out.println("day: " + day);
			System.out.println("item: " + item);
			System.out.println("cost: " + cost);*/
			
			pstmt.executeUpdate();
			pstmt.close();
		    
		}catch(Exception e){
			e.printStackTrace();	
		}	
	}
	
	public static ArrayList<Expenditure> expenditureList (String date){
		
		ArrayList<Expenditure> arr = new ArrayList<>();
		ResultSet rs = null;
		String  expenditureList
			= "SELECT * from expenditure WHERE date = ?";
		
		try {
			myConn = DriverManager.getConnection(dbUrl, user, password);
		
			PreparedStatement pstmt = myConn.prepareStatement(expenditureList);
			pstmt.setString(1, date);
			rs=pstmt.executeQuery(); 
			
			while(rs.next()){  
				Expenditure ex = new Expenditure();
				ex.setDate(rs.getString("date"));
				ex.setDay(rs.getString("day"));
				ex.setItem(rs.getString("item"));
				ex.setCost(rs.getString("cost"));
				arr.add(ex);
			}  
			pstmt.close();
		}catch(Exception e){
			e.printStackTrace();	
		}
		return arr;
	}
}
