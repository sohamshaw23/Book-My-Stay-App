import java.util.LinkedList;
import java.util.Queue;

/**
 * UseCase5BookingRequestQueue
 * 
 * Demonstrates booking request handling using FIFO Queue.
 * Requests are stored in arrival order without processing allocation.
 * 
 * @author YourName
 * @version 5.0
 */
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulate incoming booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));
        bookingQueue.addRequest(new Reservation("Diana", "Single Room"));

        // Display queued requests
        System.out.println("========= Booking Request Queue =========");
        bookingQueue.displayQueue();
    }
}

/**
 * Reservation class
 * Represents a guest booking request
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
 * BookingRequestQueue class
 * Manages booking requests using FIFO Queue
 */
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add request to queue
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added: " 
            + reservation.getGuestName() + " -> " 
            + reservation.getRoomType());
    }

    // Display all queued requests
    public void displayQueue() {
        if (queue.isEmpty()) {
            System.out.println("No booking requests in queue.");
            return;
        }

        for (Reservation res : queue) {
            System.out.println("Guest: " + res.getGuestName()
                    + " | Room Type: " + res.getRoomType());
        }
    }
}
