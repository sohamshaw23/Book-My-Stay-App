import java.util.*;

/**
 * UseCase6RoomAllocationService
 * 
 * Demonstrates booking confirmation and safe room allocation.
 * Ensures no double-booking using Set and maintains inventory consistency.
 * 
 * @author YourName
 * @version 6.0
 */
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 2);
        inventory.addRoomType("Double Room", 1);
        inventory.addRoomType("Suite Room", 1);

        // Initialize booking queue
        Queue<Reservation> bookingQueue = new LinkedList<>();
        bookingQueue.offer(new Reservation("Alice", "Single Room"));
        bookingQueue.offer(new Reservation("Bob", "Single Room"));
        bookingQueue.offer(new Reservation("Charlie", "Single Room")); // Should fail
        bookingQueue.offer(new Reservation("Diana", "Suite Room"));

        // Process bookings
        BookingService bookingService = new BookingService(inventory);
        bookingService.processBookings(bookingQueue);
    }
}

/**
 * Reservation class
 */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/**
 * RoomInventory class (from previous use case)
 */
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int change) {
        int current = getAvailability(roomType);
        inventory.put(roomType, current + change);
    }
}

/**
 * BookingService class
 * Handles allocation and confirmation
 */
class BookingService {

    private RoomInventory inventory;

    // Map to track allocated rooms by type
    private Map<String, Set<String>> allocatedRooms;

    // Global set to ensure uniqueness
    private Set<String> allAllocatedRoomIds;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.allocatedRooms = new HashMap<>();
        this.allAllocatedRoomIds = new HashSet<>();
    }

    // Process booking queue
    public void processBookings(Queue<Reservation> queue) {

        System.out.println("========= Booking Processing =========");

        while (!queue.isEmpty()) {

            Reservation request = queue.poll();
            String roomType = request.getRoomType();

            int available = inventory.getAvailability(roomType);

            // Check availability
            if (available > 0) {

                // Generate unique room ID
                String roomId = generateRoomId(roomType);

                // Ensure uniqueness
                if (!allAllocatedRoomIds.contains(roomId)) {

                    // Add to global set
                    allAllocatedRoomIds.add(roomId);

                    // Add to type-specific set
                    allocatedRooms
                        .computeIfAbsent(roomType, k -> new HashSet<>())
                        .add(roomId);

                    // Update inventory immediately
                    inventory.updateAvailability(roomType, -1);

                    // Confirm booking
                    System.out.println("Booking Confirmed: "
                            + request.getGuestName()
                            + " | " + roomType
                            + " | Room ID: " + roomId);
                }

            } else {
                System.out.println("Booking Failed (No Availability): "
                        + request.getGuestName()
                        + " | " + roomType);
            }
        }
    }

    // Generate unique room ID
    private String generateRoomId(String roomType) {
        return roomType.replace(" ", "").toUpperCase()
                + "-" + UUID.randomUUID().toString().substring(0, 5);
    }
}
