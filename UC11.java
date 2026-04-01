import java.util.*;

/**
 * UseCase11ConcurrentBookingSimulation
 * 
 * Demonstrates concurrent booking requests with thread-safe inventory updates.
 * Multiple threads simulate guests trying to book rooms simultaneously.
 * 
 * Author: YourName
 * Version: 11.0
 */
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        // Initialize shared inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 3);
        inventory.addRoomType("Double", 2);

        // Initialize shared booking queue
        Queue<ReservationRequest> bookingQueue = new LinkedList<>();

        // Simulate booking requests
        bookingQueue.add(new ReservationRequest("Alice", "Single"));
        bookingQueue.add(new ReservationRequest("Bob", "Single"));
        bookingQueue.add(new ReservationRequest("Charlie", "Double"));
        bookingQueue.add(new ReservationRequest("Diana", "Single"));
        bookingQueue.add(new ReservationRequest("Eve", "Double"));

        // Create and start threads
        List<Thread> threads = new ArrayList<>();
        while (!bookingQueue.isEmpty()) {
            ReservationRequest request = bookingQueue.poll();
            Thread t = new Thread(() -> {
                BookingProcessor.processBooking(request, inventory);
            });
            threads.add(t);
            t.start();
        }

        // Wait for all threads to finish
        for (Thread t : threads) {
            try { t.join(); } catch (InterruptedException e) { e.printStackTrace(); }
        }

        System.out.println("\n=== Final Inventory ===");
        inventory.displayInventory();
    }
}

/**
 * Reservation request class
 */
class ReservationRequest {
    String guestName;
    String roomType;

    public ReservationRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

/**
 * Thread-safe booking processor
 */
class BookingProcessor {

    public static void processBooking(ReservationRequest request, RoomInventory inventory) {
        synchronized (inventory) {
            if (inventory.getAvailable(request.roomType) > 0) {
                String roomId = inventory.bookRoom(request.roomType);
                System.out.println("Reservation confirmed: " + request.guestName +
                        " booked " + request.roomType + " (" + roomId + ")");
            } else {
                System.out.println("Reservation failed: " + request.guestName +
                        " could not book " + request.roomType + " (Sold Out)");
            }
        }
    }
}

/**
 * Thread-safe RoomInventory class
 */
class RoomInventory {
    private Map<String, Integer> availableRooms = new HashMap<>();
    private Map<String, Integer> roomCounters = new HashMap<>();

    public void addRoomType(String roomType, int count) {
        availableRooms.put(roomType, count);
        roomCounters.put(roomType, 1); // Counter to generate room IDs
    }

    public int getAvailable(String roomType) {
        return availableRooms.getOrDefault(roomType, 0);
    }

    public String bookRoom(String roomType) {
        int count = availableRooms.get(roomType);
        if (count <= 0) return null;

        // Decrement availability
        availableRooms.put(roomType, count - 1);

        // Generate unique room ID
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
