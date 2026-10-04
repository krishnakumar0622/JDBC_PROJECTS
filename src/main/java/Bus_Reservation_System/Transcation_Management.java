package Bus_Reservation_System;

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

    public void bookTicket() throws Exception {

        System.out.println("Enter bus id");
        int busId = sc.nextInt();

        System.out.println("Enter passenger id");
        int passengerId = sc.nextInt();

        System.out.println("Enter seat number");
        int seatNumber = sc.nextInt();

        try {

            con.setAutoCommit(false);

            // Step 1: Check bus
            String busSql =
                    "SELECT available_seats "
                    + "FROM buses "
                    + "WHERE bus_id = ?";

            PreparedStatement busPs =
                    con.prepareStatement(busSql);

            busPs.setInt(1, busId);

            ResultSet busRs =
                    busPs.executeQuery();

            if (!busRs.next()) {

                System.out.println("Bus not found!");
                con.rollback();
                return;
            }

            int availableSeats =
                    busRs.getInt("available_seats");

            System.out.println(
                    "Available Seats: " + availableSeats
            );

            // Step 2: Check available seats
            if (availableSeats <= 0) {

                System.out.println(
                        "No seats available!"
                );

                con.rollback();
                return;
            }

            // Step 3: Check whether seat is already booked
            String seatSql =
                    "SELECT * FROM bookings "
                    + "WHERE bus_id = ? "
                    + "AND seat_number = ? "
                    + "AND status = 'BOOKED'";

            PreparedStatement seatPs =
                    con.prepareStatement(seatSql);

            seatPs.setInt(1, busId);
            seatPs.setInt(2, seatNumber);

            ResultSet seatRs =
                    seatPs.executeQuery();

            if (seatRs.next()) {

                System.out.println(
                        "This seat is already booked!"
                );

                con.rollback();
                return;
            }

            // Step 4: Insert booking
            String bookingSql =
                    "INSERT INTO bookings "
                    + "(bus_id, passenger_id, seat_number, booking_date, status) "
                    + "VALUES (?, ?, ?, CURDATE(), 'BOOKED')";

            PreparedStatement bookingPs =
                    con.prepareStatement(bookingSql);

            bookingPs.setInt(1, busId);
            bookingPs.setInt(2, passengerId);
            bookingPs.setInt(3, seatNumber);

            bookingPs.executeUpdate();

            // Step 5: Reduce available seats
            String updateBusSql =
                    "UPDATE buses "
                    + "SET available_seats = available_seats - 1 "
                    + "WHERE bus_id = ?";

            PreparedStatement updateBusPs =
                    con.prepareStatement(updateBusSql);

            updateBusPs.setInt(1, busId);

            updateBusPs.executeUpdate();

            // Step 6: Commit
            con.commit();

            System.out.println(
                    "Ticket booked successfully!"
            );

        } catch (Exception e) {

            con.rollback();

            System.out.println(
                    "Booking failed!"
            );

            System.out.println(
                    "All changes have been rolled back."
            );

            e.printStackTrace();

        } finally {

            con.setAutoCommit(true);
        }
    }
    
    public void cancelTicket() throws Exception {

        System.out.println("Enter booking id");
        int bookingId = sc.nextInt();

        try {

            con.setAutoCommit(false);

            // Step 1: Check booking
            String sql =
                    "SELECT bus_id, status "
                    + "FROM bookings "
                    + "WHERE booking_id = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, bookingId);

            ResultSet rs =
                    ps.executeQuery();

            if (!rs.next()) {

                System.out.println("Booking not found!");
                con.rollback();
                return;
            }

            int busId = rs.getInt("bus_id");
            String status = rs.getString("status");

            // Step 2: Check booking status
            if (!status.equals("BOOKED")) {

                System.out.println(
                        "This ticket is already " + status
                );

                con.rollback();
                return;
            }

            // Step 3: Cancel booking
            String updateBooking =
                    "UPDATE bookings "
                    + "SET status = 'CANCELLED' "
                    + "WHERE booking_id = ?";

            PreparedStatement updatePs =
                    con.prepareStatement(updateBooking);

            updatePs.setInt(1, bookingId);

            updatePs.executeUpdate();

            // Step 4: Increase available seats
            String updateBus =
                    "UPDATE buses "
                    + "SET available_seats = available_seats + 1 "
                    + "WHERE bus_id = ?";

            PreparedStatement updateBusPs =
                    con.prepareStatement(updateBus);

            updateBusPs.setInt(1, busId);

            updateBusPs.executeUpdate();

            // Step 5: Commit
            con.commit();

            System.out.println(
                    "Ticket cancelled successfully!"
            );

            System.out.println(
                    "Seat is now available."
            );

        } catch (Exception e) {

            con.rollback();

            System.out.println(
                    "Cancellation failed!"
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
