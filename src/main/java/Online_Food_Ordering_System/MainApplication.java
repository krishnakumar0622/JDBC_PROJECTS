package Online_Food_Ordering_System;

import java.sql.Connection;
import java.util.Scanner;

public class MainApplication {

    public static void main(String[] args) {

        try {

            Connection con = ConnectionClass.getConnection();

            if (con != null) {
                System.out.println("Connection success");
            }

            Scanner sc = new Scanner(System.in);

            // Create tables
            CreateTable CD = new CreateTable(con);

            CD.createCustomerTable();
            CD.createMenuTable();
            CD.createOrderTable();
            CD.createOrderItemsTable();

            // Create objects
            CRUDoperations crud = new CRUDoperations(con, sc);

            Transcation_Management TM =
                    new Transcation_Management(con, sc);

            Menu menu = new Menu();

            int choice;

            do {

                menu.mainMenu();

                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                case 1:

                    int customerChoice;

                    do {

                        menu.customerMenu();

                        customerChoice = sc.nextInt();
                        sc.nextLine();

                        switch (customerChoice) {

                        case 1:
                            crud.registerCustomer();
                            break;

                        case 2:
                            crud.displayCustomers();
                            break;

                        case 3:
                            System.out.println("Back to main menu");
                            break;

                        default:
                            System.out.println("Invalid choice!");
                        }

                    } while (customerChoice != 3);

                    break;

                case 2:
                    crud.viewMenu();
                    break;

                case 3:
                    TM.placeOrder();
                    break;

                case 4:
                    crud.orderHistory();
                    break;

                case 5:
                    crud.generateBill();
                    break;

                case 6:
                    System.out.println("Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice!");
                }

            } while (choice != 6);

            sc.close();
            con.close();

        } catch (Exception E) {

            E.printStackTrace();
        }
    }
}
