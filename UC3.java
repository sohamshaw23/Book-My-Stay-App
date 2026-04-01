import java.util.HashMap;
import java.util.Map;

/**
 * UseCase3InventorySetup
 * 
 * Demonstrates centralized inventory management using HashMap.
 * Replaces scattered variables with a single source of truth.
 * 
 * @author YourName
 * @version 3.1
 */
public class UseCase3InventorySetup {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Register room availability
        inventory.addRoomType("Single Room", 5);
        inventory.addRoomType("Double Room", 3);
        inventory.addRoomType("Suite Room", 2);

        // Display current inventory
        System.out.println("========= Centralized Room Inventory =========");
        inventory.displayInventory();

        // Update availability
        System.out.println("\nUpdating availability (Booking 1 Single Room)...");
        inventory.updateAvailability("Single Room", -1);

        // Display updated inventory
        System.out.println("\n========= Updated Room Inventory =========");
        inventory.displayInventory();
    }
}

/**
 * RoomInventory class
 * Handles centralized storage and management of room availability
 */
class RoomInventory {

    private Map<String, Integer> inventory;

    // Constructor initializes the HashMap
    public RoomInventory() {
        inventory = new HashMap<>();
    }

    // Add new room type
    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Get availability of a specific room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability (controlled update)
    public void updateAvailability(String roomType, int change) {
        int current = getAvailability(roomType);
        int updated = current + change;

        if (updated >= 0) {
            inventory.put(roomType, updated);
        } else {
            System.out.println("Error: Cannot reduce below zero for " + roomType);
        }
    }

    // Display full inventory
    public void displayInventory() {
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> Available Rooms: " + entry.getValue());
        }
    }
}
