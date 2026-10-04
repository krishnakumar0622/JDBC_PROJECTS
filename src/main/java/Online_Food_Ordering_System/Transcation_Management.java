package Online_Food_Ordering_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Transcation_Management {

    Connection con;
    Scanner sc;

    Transcation_Management(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

    public void placeOrder() throws Exception {

        System.out.println("Enter customer id");
        int customerId = sc.nextInt();

        try {

            con.setAutoCommit(false);

            // Step 1: Check customer
            String customerSql =
                    "SELECT customer_id "
                    + "FROM customers "
                    + "WHERE customer_id = ?";

            PreparedStatement customerPs =
                    con.prepareStatement(customerSql);

            customerPs.setInt(1, customerId);

            ResultSet customerRs =
                    customerPs.executeQuery();

            if (!customerRs.next()) {

                System.out.println("Customer not found!");

                con.rollback();
                return;
            }

            // Step 2: Create order
            String orderSql =
                    "INSERT INTO orders "
                    + "(customer_id, order_date, total_amount) "
                    + "VALUES (?, CURDATE(), 0)";

            PreparedStatement orderPs =
                    con.prepareStatement(
                            orderSql,
                            java.sql.Statement.RETURN_GENERATED_KEYS
                    );

            orderPs.setInt(1, customerId);

            orderPs.executeUpdate();

            // Step 3: Get generated order ID
            ResultSet orderRs =
                    orderPs.getGeneratedKeys();

            orderRs.next();

            int orderId =
                    orderRs.getInt(1);

            System.out.println(
                    "Order ID: " + orderId
            );

            double totalAmount = 0;

            String moreItems;

            do {

                // Step 4: Select food
                System.out.println("Enter food id");
                int foodId = sc.nextInt();

                System.out.println("Enter quantity");
                int quantity = sc.nextInt();

                // Step 5: Get food price
                String foodSql =
                        "SELECT price "
                        + "FROM menu "
                        + "WHERE food_id = ?";

                PreparedStatement foodPs =
                        con.prepareStatement(foodSql);

                foodPs.setInt(1, foodId);

                ResultSet foodRs =
                        foodPs.executeQuery();

                if (!foodRs.next()) {

                    System.out.println(
                            "Food item not found!"
                    );

                    con.rollback();
                    return;
                }

                double price =
                        foodRs.getDouble("price");

                // Step 6: Calculate item total
                double itemTotal =
                        price * quantity;

                totalAmount =
                        totalAmount + itemTotal;

                // Step 7: Insert order item
                String itemSql =
                        "INSERT INTO order_items "
                        + "(order_id, food_id, quantity) "
                        + "VALUES (?, ?, ?)";

                PreparedStatement itemPs =
                        con.prepareStatement(itemSql);

                itemPs.setInt(1, orderId);
                itemPs.setInt(2, foodId);
                itemPs.setInt(3, quantity);

                itemPs.executeUpdate();

                System.out.println(
                        "Item added successfully!"
                );

                System.out.println(
                        "Do you want to add another item? (yes/no)"
                );

                moreItems = sc.next();

            } while (moreItems.equalsIgnoreCase("yes"));

            // Step 8: Update total amount
            String updateOrder =
                    "UPDATE orders "
                    + "SET total_amount = ? "
                    + "WHERE order_id = ?";

            PreparedStatement updatePs =
                    con.prepareStatement(updateOrder);

            updatePs.setDouble(1, totalAmount);
            updatePs.setInt(2, orderId);

            updatePs.executeUpdate();

            // Step 9: Commit
            con.commit();

            System.out.println(
                    "Order placed successfully!"
            );

            System.out.println(
                    "Total Amount: ₹" + totalAmount
            );

        } catch (Exception e) {

            con.rollback();

            System.out.println(
                    "Order failed!"
            );

            System.out.println(
                    "All changes have been rolled back."
            );

            e.printStackTrace();

        } finally {

            con.setAutoCommit(true);
        }
    }
}