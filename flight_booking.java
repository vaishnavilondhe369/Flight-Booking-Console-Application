
package Application_program;

import java.util.ArrayList;
import java.util.Scanner;

abstract class Passenger {
    private String name;
    private int age;

    Passenger(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    abstract double getDiscount();

    abstract String getType();
}

class RegularPassenger extends Passenger {
    RegularPassenger(String name, int age) {
        super(name, age);
    }

    double getDiscount() {
        return 0;
    }

    String getType() {
        return "Regular";
    }
}

class StudentPassenger extends Passenger {
    StudentPassenger(String name, int age) {
        super(name, age);
    }

    double getDiscount() {
        return 10;
    }

    String getType() {
        return "Student";
    }
}

class Flight {
    private String code;
    private String source;
    private String destination;
    private int seats;
    private double fare;

    Flight(String code, String source, String destination,
           int seats, double fare) {
        this.code = code;
        this.source = source;
        this.destination = destination;
        this.seats = seats;
        this.fare = fare;
    }

    String getCode() {
        return code;
    }

    int getSeats() {
        return seats;
    }

    double getFare() {
        return fare;
    }

    String getDetails() {
        return source + " to " + destination;
    }

    void display() {
        System.out.println(code + " | " + getDetails()
                + " | Fare: Rs." + fare
                + " | Seats: " + seats);
    }

    boolean hasSeats(int count) {
        return count > 0 && count <= seats;
    }

    void bookSeats(int count) {
        seats = seats - count;
    }
}

class Booking {
    private String id;
    private Flight flight;
    private Passenger passenger;
    private int tickets;
    private double subtotal;
    private double discount;
    private double total;

    Booking(String id, Flight flight,
            Passenger passenger, int tickets) {
        this.id = id;
        this.flight = flight;
        this.passenger = passenger;
        this.tickets = tickets;

        subtotal = flight.getFare() * tickets;
        discount = subtotal * passenger.getDiscount() / 100;
        total = subtotal - discount;
    }

    void displaySummary() {
        System.out.println("\n--- Booking Summary ---");
        System.out.println("Booking ID: " + id);
        System.out.println("Flight: " + flight.getCode()
                + " | " + flight.getDetails());
        System.out.println("Passenger: " + passenger.getName());
        System.out.println("Age: " + passenger.getAge());
        System.out.println("Passenger Type: " + passenger.getType());
        System.out.println("Tickets: " + tickets);
        System.out.println("Base fare: Rs." + flight.getFare());
        System.out.println("Subtotal: Rs." + subtotal);
        System.out.println("Discount: Rs." + discount);
        System.out.println("Final amount: Rs." + total);
    }
}

public class flight_booking {
    private Scanner sc = new Scanner(System.in);
    private ArrayList<Flight> flights = new ArrayList<>();
    private ArrayList<Booking> bookings = new ArrayList<>();
    private int nextId = 1001;

    public flight_booking() {
        flights.add(new Flight("PN101", "Pune", "Delhi", 20, 4000));
        flights.add(new Flight("MB102", "Mumbai", "Bengaluru", 30, 3500));
        flights.add(new Flight("PN103", "Pune", "Hyderabad", 25, 3000));
    }

    private Flight findFlight(String code) {
        for (Flight f : flights) {
            if (f.getCode().equalsIgnoreCase(code)) {
                return f;
            }
        }
        return null;
    }

    private void displayFlights() {
        System.out.println("\n--- Available Flights ---");
        for (Flight f : flights) {
            f.display();
        }
    }

    private int readInt(String message) {
        System.out.print(message);
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void bookFlight() {
        displayFlights();

        System.out.print("Enter flight code: ");
        Flight flight = findFlight(sc.nextLine().trim());

        if (flight == null) {
            System.out.println("Error: Flight not found.");
            return;
        }

        System.out.print("Enter passenger name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Error: Name cannot be blank.");
            return;
        }

        int age = readInt("Enter age: ");
        if (age <= 0) {
            System.out.println("Error: Invalid age.");
            return;
        }

        System.out.print("Passenger type (Regular/Student): ");
        String type = sc.nextLine().trim();

        Passenger passenger;

        if (type.equalsIgnoreCase("Regular")) {
            passenger = new RegularPassenger(name, age);
        } else if (type.equalsIgnoreCase("Student")) {
            passenger = new StudentPassenger(name, age);
        } else {
            System.out.println("Error: Invalid passenger type.");
            return;
        }

        int tickets = readInt("Enter number of tickets: ");

        if (tickets <= 0) {
            System.out.println("Error: Invalid ticket count.");
            return;
        }

        if (!flight.hasSeats(tickets)) {
            System.out.println("Booking failed: Not enough seats.");
            return;
        }

        Booking booking = new Booking(
                "BK" + nextId, flight, passenger, tickets);

        flight.bookSeats(tickets);
        bookings.add(booking);
        nextId++;

        System.out.println("\nBooking confirmed!");
        booking.displaySummary();
    }

    private void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        for (Booking b : bookings) {
            b.displaySummary();
        }
    }

    public void start() {
        while (true) {
            System.out.println("\nWelcome to Kiran Air Booking");
            System.out.println("1. View Flights");
            System.out.println("2. Book Flight");
            System.out.println("3. View Booking");
            System.out.println("4. Exit");

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    displayFlights();
                    break;
                case 2:
                    bookFlight();
                    break;
                case 3:
                    viewBookings();
                    break;
                case 4:
                    System.out.println("Thank you for using Kiran Air!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void main(String[] args) {
        flight_booking app = new flight_booking();
        app.start();
    }
}
