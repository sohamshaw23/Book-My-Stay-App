import java.util.*;

/**
 * UseCase7AddOnServiceSelection
 * 
 * Demonstrates how add-on services can be attached to a reservation
 * without modifying core booking or inventory logic.
 * 
 * @author YourName
 * @version 7.0
 */
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        // Sample reservation IDs (already created in previous use case)
        String reservation1 = "RES-101";
        String reservation2 = "RES-102";

        // Initialize Add-On Service Manager
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Create services
        AddOnService wifi = new AddOnService("WiFi", 200);
        AddOnService breakfast = new AddOnService("Breakfast", 300);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 500);

        // Guest selects services
        serviceManager.addService(reservation1, wifi);
        serviceManager.addService(reservation1, breakfast);

        serviceManager.addService(reservation2, airportPickup);
        serviceManager.addService(reservation2, breakfast);

        // Display services and total cost
        System.out.println("========= Add-On Services =========");

        serviceManager.displayServices(reservation1);
        serviceManager.displayTotalCost(reservation1);

        System.out.println("----------------------------------");

        serviceManager.displayServices(reservation2);
        serviceManager.displayTotalCost(reservation2);
    }
}

/**
 * AddOnService class
 * Represents a single optional service
 */
class AddOnService {

    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

/**
 * AddOnServiceManager class
 * Manages mapping between reservations and their selected services
 */
class AddOnServiceManager {

    private Map<String, List<AddOnService>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    // Add service to a reservation
    public void addService(String reservationId, AddOnService service) {
        serviceMap
            .computeIfAbsent(reservationId, k -> new ArrayList<>())
            .add(service);

        System.out.println("Service added: " 
                + service.getServiceName() 
                + " -> " + reservationId);
    }

    // Display services for a reservation
    public void displayServices(String reservationId) {
        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected for " + reservationId);
            return;
        }

        System.out.println("Services for " + reservationId + ":");

        for (AddOnService service : services) {
            System.out.println("- " + service.getServiceName() 
                    + " (₹" + service.getCost() + ")");
        }
    }

    // Calculate total cost
    public void displayTotalCost(String reservationId) {
        List<AddOnService> services = serviceMap.get(reservationId);

        double total = 0;

        if (services != null) {
            for (AddOnService service : services) {
                total += service.getCost();
            }
        }

        System.out.println("Total Add-On Cost for " 
                + reservationId + ": ₹" + total);
    }
}
