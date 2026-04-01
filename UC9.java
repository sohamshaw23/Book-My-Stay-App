import java.util.*;

/**
 * UseCase9ErrorHandlingValidation
 * 
 * Demonstrates error handling and validation for hotel booking.
 * Invalid input triggers custom exceptions and prevents invalid state changes.
 * 
 * @author YourName
 * @version 9.0
 */
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        // Initialize inventory with room types
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 2);
        inventory.addRoomType("Double", 1);
        inventory.addRoomType("Suite", 0);

        // Sample guest bookings
        try {
            processBooking("Alice", "Single", inventory);
            processBooking("Bob", "Double", inventory);
            processBooking("Charlie", "Suite", inventory); // Should fail
            processBooking("David", "Triple", inventory);  // Should fail invalid type
        } catch (InvalidBookingException e) {
            System.err.println("Booking Error: " + e.getMessage());
        }

        System.out.println("\nCurrent Inventory:");
        inventory.displayInventory();
    }

    // Process a booking request
    public static void processBooking(String guestName, String roomType, RoomInventory inventory)
            throws InvalidBookingException {

        System.out.println("\nProcessing booking for " + guestName + " - Room: " + roomType);

        // Validate room type
        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        // Check availability
        if (inventory.getAvailability(roomType) <= 0) {
            throw new InvalidBookingException("No available rooms for type: " + roomType);
        }

        // Book room
        inventory.bookRoom(roomType);
        System.out.println("Booking confirmed for " + guestName + " in " + roomType + " room.");
    }
}

/**
 * Custom exception for invalid booking scenarios
 */
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

/**
 * RoomInventory class
 * Maintains room availability with validation
 */
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    // Add room type with initial count
    public void addRoomType(String roomType, int count) {
        if (count < 0) {
            count = 0;
        }
        inventory.put(roomType, count);
    }

    // Check if room type exists
    public boolean isValidRoomType(String roomType) {
        return inventory.containsKey(roomType);
    }

    // Get availability for a room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Book a room (decrement count)
    public void bookRoom(String roomType) {
        int count = inventory.get(roomType);
        if (count > 0) {
            inventory.put(roomType, count - 1);
        }
    }

    // Display current inventory
    public void displayInventory() {
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> Available: " + entry.getValue());
        }
    }
}
