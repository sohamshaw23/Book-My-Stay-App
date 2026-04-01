import java.io.*;
import java.util.*;

/**
 * UseCase12DataPersistenceRecovery
 *
 * Demonstrates persistence of inventory and booking history using serialization.
 * Application can save state before shutdown and restore state on startup.
 *
 * Author: YourName
 * Version: 12.0
 */
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {
        // File paths for persistence
        String inventoryFile = "inventory.ser";
        String bookingFile = "bookingHistory.ser";

        RoomInventory inventory = null;
        List<Reservation> bookingHistory = null;

        // Attempt to restore persisted state
        try {
            inventory = (RoomInventory) deserialize(inventoryFile);
            bookingHistory = (List<Reservation>) deserialize(bookingFile);
            System.out.println("State restored from files.");
        } catch (Exception e) {
            System.out.println("No previous state found. Starting fresh.");
            inventory = new RoomInventory();
            inventory.addRoomType("Single", 5);
            inventory.addRoomType("Double", 3);
            bookingHistory = new ArrayList<>();
        }

        // Simulate new booking
        Reservation newBooking = new Reservation("Alice", "Single", inventory.bookRoom("Single"));
        if (newBooking.roomId != null) {
            bookingHistory.add(newBooking);
            System.out.println("New booking confirmed: " + newBooking);
        }

        // Display current inventory
        System.out.println("\nCurrent Inventory:");
        inventory.displayInventory();

        // Display booking history
        System.out.println("\nBooking History:");
        for (Reservation r : bookingHistory) {
            System.out.println(r);
        }

        // Persist state before shutdown
        try {
            serialize(inventory, inventoryFile);
            serialize(bookingHistory, bookingFile);
            System.out.println("\nState saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Serialization utility
    public static void serialize(Object obj, String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(obj);
        }
    }

    // Deserialization utility
    public static Object deserialize(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return in.readObject();
        }
    }
}

// Serializable Reservation class
class Reservation implements Serializable {
    String guestName;
    String roomType;
    String roomId;

    public Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return guestName + " booked " + roomType + " (" + roomId + ")";
    }
}

// Serializable RoomInventory class
class RoomInventory implements Serializable {
    private Map<String, Integer> availableRooms = new HashMap<>();
    private Map<String, Integer> roomCounters = new HashMap<>();

    public void addRoomType(String roomType, int count) {
        availableRooms.put(roomType, count);
        roomCounters.put(roomType, 1);
    }

    public int getAvailable(String roomType) {
        return availableRooms.getOrDefault(roomType, 0);
    }

    public String bookRoom(String roomType) {
        int count = availableRooms.getOrDefault(roomType, 0);
        if (count <= 0) return null;

        availableRooms.put(roomType, count - 1);
        int roomNum = roomCounters.get(roomType);
        roomCounters.put(roomType, roomNum + 1);
        return roomType.charAt(0) + String.valueOf(roomNum);
    }

    public void displayInventory() {
        for (Map.Entry<String, Integer> entry : availableRooms.entrySet()) {
            System.out.println(entry.getKey() + " -> Available: " + entry.getValue());
        }
    }
}
