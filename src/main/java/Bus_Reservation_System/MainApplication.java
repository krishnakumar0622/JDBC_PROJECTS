package Bus_Reservation_System;

import java.sql.Connection;
import java.util.Scanner;

public class MainApplication {

    public static void main(String[] args) {

        try {

            // Database connection
            Connection con = ConnectionClass.getconnection();

            // Scanner
            Scanner sc = new Scanner(System.in);

            // Create Table object
            CreateTable CR = new CreateTable(con);

            // Create tables
            CR.createBusTable();
            CR.createPassengerTable();
            CR.createBookingTable();

            // Check connection
            if (con != null) {
                System.out.println("Connection successfully");
            }

            // Create CRUD object
            CRUDoperations crud =
                    new CRUDoperations(con, sc);

            // Create Transaction object
            Transcation_Management TM =
                    new Transcation_Management(con, sc);

            // Create Menu object
            Menu menu = new Menu();

            int choice;

            do {

                menu.mainMenu();

                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                case 1:

                    crud.SearchBuses();

                    break;

                case 2:

                    int passengerChoice;

                    do {

                        menu.passengerMenu();

                        passengerChoice = sc.nextInt();
                        sc.nextLine();

                        switch (passengerChoice) {

                        case 1:

                            crud.registerPassenger();

                            break;

                        case 2:

                            crud.displayPassengers();

                            break;

                        case 3:

                            System.out.println(
                                    "Returning to main menu..."
                            );

                            break;

                        default:

                            System.out.println(
                                    "Invalid choice!"
                            );
                        }

                    } while (passengerChoice != 3);

                    break;

                case 3:

                    TM.bookTicket();

                    break;

                case 4:

                    TM.cancelTicket();

                    break;

                case 5:

                    crud.displayBookings();

                    break;

                case 6:

                    System.out.println(
                            "Thank you for using Bus Reservation System!"
                    );

                    break;

                default:

                    System.out.println(
                            "Invalid choice!"
                    );
                }

            } while (choice != 6);

            sc.close();
            con.close();

        }

        catch (Exception e) {

            e.printStackTrace();

        }

    }
}