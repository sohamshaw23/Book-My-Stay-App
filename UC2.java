/**
 * UseCase2RoomInitialization
 * 
 * This class demonstrates basic object-oriented design using
 * abstraction, inheritance, and encapsulation in a Hotel Booking System.
 * 
 * It initializes different room types and displays their
 * availability using simple variables.
 * 
 * @author YourName
 * @version 2.1
 */
public class UseCase2RoomInitialization {

    public static void main(String[] args) {

        // Create Room Objects
        Room singleRoom = new SingleRoom(1, 200, 1000);
        Room doubleRoom = new DoubleRoom(2, 350, 1800);
        Room suiteRoom = new SuiteRoom(3, 500, 3000);

        // Static Availability (simple variables)
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // Display Details
        System.out.println("========= Room Availability =========");

        singleRoom.displayDetails();
        System.out.println("Available Rooms: " + singleAvailable);
        System.out.println("------------------------------------");

        doubleRoom.displayDetails();
        System.out.println("Available Rooms: " + doubleAvailable);
        System.out.println("------------------------------------");

        suiteRoom.displayDetails();
        System.out.println("Available Rooms: " + suiteAvailable);
        System.out.println("------------------------------------");
    }
}

/**
 * Abstract Room class
 * Defines common structure for all room types
 */
abstract class Room {

    protected int beds;
    protected int size;
    protected double price;

    // Constructor
    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    // Abstract method
    public abstract void displayDetails();
}

/**
 * Single Room Class
 */
class SingleRoom extends Room {

    public SingleRoom(int beds, int size, double price) {
        super(beds, size, price);
    }

    @Override
    public void displayDetails() {
        System.out.println("Room Type: Single Room");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq ft");
        System.out.println("Price: ₹" + price);
    }
}

/**
 * Double Room Class
 */
class DoubleRoom extends Room {

    public DoubleRoom(int beds, int size, double price) {
        super(beds, size, price);
    }

    @Override
    public void displayDetails() {
        System.out.println("Room Type: Double Room");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq ft");
        System.out.println("Price: ₹" + price);
    }
}

/**
 * Suite Room Class
 */
class SuiteRoom extends Room {

    public SuiteRoom(int beds, int size, double price) {
        super(beds, size, price);
    }

    @Override
    public void displayDetails() {
        System.out.println("Room Type: Suite Room");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq ft");
        System.out.println("Price: ₹" + price);
    }
}
