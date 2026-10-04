package Online_Food_Ordering_System;

	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.util.Scanner;

	public class CRUDoperations {

	    Connection con;
	    Scanner sc;

	    CRUDoperations(Connection con, Scanner sc) {
	        this.con = con;
	        this.sc = sc;
	    }

	    // 1. Customer Registration
	    public void registerCustomer() throws Exception {

	        System.out.println("Enter customer name");
	        String customerName = sc.nextLine();

	        System.out.println("Enter customer phone");
	        String customerPhone = sc.nextLine();

	        String sql = "INSERT INTO customers "
	                + "(customer_name, customer_phone) "
	                + "VALUES (?, ?)";

	        PreparedStatement ps =
	                con.prepareStatement(sql);

	        ps.setString(1, customerName);
	        ps.setString(2, customerPhone);

	        ps.executeUpdate();

	        System.out.println(
	                "Customer registered successfully!"
	        );
	    }

	    // 2. Display Customers
	    public void displayCustomers() throws Exception {

	        String sql = "SELECT * FROM customers";

	        PreparedStatement ps =
	                con.prepareStatement(sql);

	        ResultSet rs =
	                ps.executeQuery();

	        boolean found = false;

	        while (rs.next()) {

	            found = true;

	            System.out.println("------------------------------");

	            System.out.println(
	                    "Customer ID: "
	                    + rs.getInt("customer_id")
	            );

	            System.out.println(
	                    "Customer Name: "
	                    + rs.getString("customer_name")
	            );

	            System.out.println(
	                    "Customer Phone: "
	                    + rs.getString("customer_phone")
	            );

	            System.out.println("------------------------------");
	        }

	        if (!found) {
	            System.out.println("No customers found!");
	        }
	    }

	    // 3. View Menu
	    public void viewMenu() throws Exception {

	        String sql = "SELECT * FROM menu";

	        PreparedStatement ps =
	                con.prepareStatement(sql);

	        ResultSet rs =
	                ps.executeQuery();

	        boolean found = false;

	        while (rs.next()) {

	            found = true;

	            System.out.println("------------------------------");

	            System.out.println(
	                    "Food ID: "
	                    + rs.getInt("food_id")
	            );

	            System.out.println(
	                    "Food Name: "
	                    + rs.getString("food_name")
	            );

	            System.out.println(
	                    "Price: "
	                    + rs.getDouble("price")
	            );

	            System.out.println("------------------------------");
	        }

	        if (!found) {
	            System.out.println("No food items found!");
	        }
	    }

	    // 4. Display All Orders
	    public void displayOrders() throws Exception {

	        String sql = "SELECT o.order_id, "
	                + "c.customer_name, "
	                + "o.order_date, "
	                + "o.total_amount "
	                + "FROM orders o "
	                + "JOIN customers c "
	                + "ON o.customer_id = c.customer_id";

	        PreparedStatement ps =
	                con.prepareStatement(sql);

	        ResultSet rs =
	                ps.executeQuery();

	        boolean found = false;

	        while (rs.next()) {

	            found = true;

	            System.out.println("------------------------------");

	            System.out.println(
	                    "Order ID: "
	                    + rs.getInt("order_id")
	            );

	            System.out.println(
	                    "Customer Name: "
	                    + rs.getString("customer_name")
	            );

	            System.out.println(
	                    "Order Date: "
	                    + rs.getDate("order_date")
	            );

	            System.out.println(
	                    "Total Amount: ₹"
	                    + rs.getDouble("total_amount")
	            );

	            System.out.println("------------------------------");
	        }

	        if (!found) {
	            System.out.println("No orders found!");
	        }
	    }

	    // 5. Order History for a Customer
	    public void orderHistory() throws Exception {

	        System.out.println("Enter customer id");

	        int customerId = sc.nextInt();
	        sc.nextLine();

	        String sql = "SELECT o.order_id, "
	                + "o.order_date, "
	                + "o.total_amount "
	                + "FROM orders o "
	                + "WHERE o.customer_id = ?";

	        PreparedStatement ps =
	                con.prepareStatement(sql);

	        ps.setInt(1, customerId);

	        ResultSet rs =
	                ps.executeQuery();

	        boolean found = false;

	        while (rs.next()) {

	            found = true;

	            System.out.println("------------------------------");

	            System.out.println(
	                    "Order ID: "
	                    + rs.getInt("order_id")
	            );

	            System.out.println(
	                    "Order Date: "
	                    + rs.getDate("order_date")
	            );

	            System.out.println(
	                    "Total Amount: ₹"
	                    + rs.getDouble("total_amount")
	            );

	            System.out.println("------------------------------");
	        }

	        if (!found) {
	            System.out.println(
	                    "No order history found!"
	            );
	        }
	    }
	    
	    public void generateBill() throws Exception {

	        System.out.println("Enter order id");
	        int orderId = sc.nextInt();

	        String sql = "SELECT o.order_id, "
	                + "c.customer_name, "
	                + "o.order_date, "
	                + "m.food_name, "
	                + "m.price, "
	                + "oi.quantity, "
	                + "(m.price * oi.quantity) AS item_total "
	                + "FROM orders o "
	                + "JOIN customers c "
	                + "ON o.customer_id = c.customer_id "
	                + "JOIN order_items oi "
	                + "ON o.order_id = oi.order_id "
	                + "JOIN menu m "
	                + "ON oi.food_id = m.food_id "
	                + "WHERE o.order_id = ?";

	        PreparedStatement ps =
	                con.prepareStatement(sql);

	        ps.setInt(1, orderId);

	        ResultSet rs =
	                ps.executeQuery();

	        boolean found = false;
	        double total = 0;

	        while (rs.next()) {

	            found = true;

	            if (total == 0) {
	                System.out.println("\n========== BILL ==========");
	                System.out.println("Order ID: "
	                        + rs.getInt("order_id"));

	                System.out.println("Customer Name: "
	                        + rs.getString("customer_name"));

	                System.out.println("Order Date: "
	                        + rs.getDate("order_date"));

	                System.out.println("==========================");
	            }

	            String foodName =
	                    rs.getString("food_name");

	            double price =
	                    rs.getDouble("price");

	            int quantity =
	                    rs.getInt("quantity");

	            double itemTotal =
	                    rs.getDouble("item_total");

	            System.out.println(
	                    foodName + " | ₹" + price
	                    + " x " + quantity
	                    + " = ₹" + itemTotal
	            );

	            total = total + itemTotal;
	        }

	        if (!found) {

	            System.out.println(
	                    "Order not found!"
	            );

	            return;
	        }

	        System.out.println("==========================");
	        System.out.println(
	                "Total Amount: ₹" + total
	        );
	        System.out.println("==========================");
	    }
	}


