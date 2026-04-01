import java.util.*;

/**
 * UseCase10BookingCancellation
 * 
 * Demonstrates booking cancellation with safe inventory rollback.
 * Uses a stack to track released room IDs and ensures system consistency.
 * 
 * @author YourName
 * @version 10.0
 */
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 2);
        inventory.addRoomType("Double", 1);

        // Track confirmed bookings
        BookingHistory history = new BookingHistory();

        // Simulate room allocations
        Reservation res1 = new Reservation("Alice", "Single", "S1");
        Reservation res2 = new Reservation("Bob", "Double", "D1");

        history.addReservation(res1);
        history.addReservation(res2);

        inventory.bookRoom("Single");
        inventory.bookRoom("Double");

        System.out.println("=== Before Cancellation ===");
        inventory.displayInventory();
        history.displayHistory();

        // Initialize cancellation service
        CancellationService cancellationService = new CancellationService(inventory, history);

        // Process cancellations
        System.out.println("\nCancelling Alice's reservation...");
        cancellationService.cancelReservation("S1");

        System.out.println("\nCancelling non-existent reservation R99...");
        cancellationService.cancelReservation("R99"); // Should handle safely

        System.out.println("\n=== After Cancellation ===");
        inventory.displayInventory();
        history.displayHistory();
    }
}

/**
 * Reservation class
 */
class Reservation {
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
        return guestName + " - " + roomType + " (" + roomId + ")";
    }
}

/**
 * RoomInventory class
 */
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public void bookRoom(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) - 1);
    }

    public void releaseRoom(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> Available: " + entry.getValue());
        }
    }
}

/**
 * BookingHistory class
 */
class BookingHistory {
    private List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation res) {
        reservations.add(res);
    }

    public Reservation findReservationById(String roomId) {
        for (Reservation r : reservations) {
            if (r.roomId.equals(roomId)) return r;
        }
        return null;
    }

    public void removeReservation(Reservation res) {
        reservations.remove(res);
    }

    public void displayHistory() {
        System.out.println("Booking History:");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }
}

/**
 * CancellationService class
 */
class CancellationService {
    private RoomInventory inventory;
    private BookingHistory history;
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
    }

    public void cancelReservation(String roomId) {
        Reservation res = history.findReservationById(roomId);

        if (res == null) {
            System.out.println("Cannot cancel. Reservation ID " + roomId + " not found.");
            return;
        }

        // Push to rollback stack
        rollbackStack.push(res.roomId);

        // Restore inventory
        inventory.releaseRoom(res.roomType);

        // Remove from history
        history.removeReservation(res);

        System.out.println("Reservation " + roomId + " cancelled successfully.");
    }
}
