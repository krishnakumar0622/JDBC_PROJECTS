package Online_Food_Ordering_System;


import java.sql.Connection;
import java.sql.Statement;

public class CreateTable {

	
	Connection con;
	
	CreateTable(Connection con){
		this.con=con;
		
	}
	
	public void createCustomerTable() throws Exception {

	    String sql = "CREATE TABLE IF NOT EXISTS customers ("
	            + " customer_id INT PRIMARY KEY AUTO_INCREMENT,"
	            + " customer_name VARCHAR(50) NOT NULL,"
	            + " customer_phone VARCHAR(15) NOT NULL"
	            + ")";

	    Statement std = con.createStatement();

	    std.executeUpdate(sql);

	    System.out.println("Customer table created successfully!");
	}
	
	
	public void createMenuTable() throws Exception {

	    String sql = "CREATE TABLE IF NOT EXISTS  menu ("
	            + " food_id INT PRIMARY KEY AUTO_INCREMENT,"
	            + " food_name VARCHAR(50) NOT NULL,"
	            + " price DECIMAL(10,2) NOT NULL"
	            + ")";

	    Statement std = con.createStatement();

	    std.executeUpdate(sql);

	    System.out.println("Menu table created successfully!");
	}
	
	
	public void createOrderTable() throws Exception {

	    String sql = "CREATE TABLE IF NOT EXISTS  orders ("
	            + " order_id INT PRIMARY KEY AUTO_INCREMENT,"
	            + " customer_id INT NOT NULL,"
	            + " order_date DATE NOT NULL,"
	            + " total_amount DECIMAL(10,2) DEFAULT 0,"
	            + " FOREIGN KEY (customer_id) REFERENCES customers(customer_id)"
	            + ")";

	    Statement std = con.createStatement();

	    std.executeUpdate(sql);

	    System.out.println("Order table created successfully!");
	}
	
	
	public void createOrderItemsTable() throws Exception {

	    String sql = "CREATE TABLE IF NOT EXISTS  order_items ("
	            + " order_item_id INT PRIMARY KEY AUTO_INCREMENT,"
	            + " order_id INT NOT NULL,"
	            + " food_id INT NOT NULL,"
	            + " quantity INT NOT NULL,"
	            + " FOREIGN KEY (order_id) REFERENCES orders(order_id),"
	            + " FOREIGN KEY (food_id) REFERENCES menu(food_id)"
	            + ")";

	    Statement std = con.createStatement();

	    std.executeUpdate(sql);

	    System.out.println("Order items table created successfully!");
	}
	
	
}
