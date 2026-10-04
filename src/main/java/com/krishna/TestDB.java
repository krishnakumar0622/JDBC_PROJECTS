package com.krishna;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class TestDB {
	
	static final String URL="jdbc:mysql://localhost:3306/college";
	static final String username="root";
	static final String password="Krishna@123";
    
	public static void main(String[] args) {
		
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con=DriverManager.getConnection(URL,username,password);
			
			String insert="insert into Student(sname,semail) values (?,?)";
			
			PreparedStatement psinsert=con.prepareStatement(insert);
			psinsert.setString(1,"anbu");
			psinsert.setString(2, "anbu@gmail.com");
			
			
			psinsert.executeUpdate();
			
			System.out.println("records inserted");
			
			String Update="update Student set semail=? where sid=?";
			
			PreparedStatement psupdate=con.prepareStatement(Update);
			psupdate.setString(1, "k@gmail.com");
			psupdate.setInt(2, 1);
			
			psupdate.executeUpdate();
			
			System.out.println("updated succesfully");
			
			
			String delete="delete from  Student where sid=?";
			PreparedStatement psdelete=con.prepareStatement(delete);
			psdelete.setInt(1, 2);
			
			psdelete.executeUpdate();
			
			System.out.println("deleted successfully");
			
			
			String fetch="select * from Student";
			
			Statement st=con.createStatement();
			
			ResultSet rs=st.executeQuery(fetch);
			
			
			while(rs.next()) {
				System.out.println(rs.getInt("sid")+"|" +rs.getString("sname")+"|"+rs.getString("semail"));
			}
			
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			
		}
		
		
		
		
	}
}
