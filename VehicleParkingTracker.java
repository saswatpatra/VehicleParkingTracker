import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

class ParkingRecord {
    static final String NOT_AVAILABLE = "N/A"; // Constant for "N/A"

    String vehicleType;
    String vehicleNumber;
    String inDate;
    String inTime;
    String outDate;
    String outTime;

    ParkingRecord(String vehicleType, String vehicleNumber, String inDate, String inTime) {
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
        this.inDate = inDate;
        this.inTime = inTime;
        this.outDate = NOT_AVAILABLE;
        this.outTime = NOT_AVAILABLE;
    }

    void setOutDetails(String outDate, String outTime) {
        this.outDate = outDate;
        this.outTime = outTime;
    }

    String formatRecordWithSerial(int serial) {
        return String.format("%-6d %-15s %-20s %-15s %-10s %-15s %-10s",
                serial, vehicleType, vehicleNumber, inDate, inTime, outDate, outTime);
    }
}

public class VehicleParkingTracker {
    static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    static List<ParkingRecord> records = new ArrayList<>();

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("\nVehicle Parking Tracker");
                System.out.println("1. Vehicle-In");
                System.out.println("2. Vehicle-Out");
                System.out.println("3. All Records");
                System.out.println("4. Exit");
                System.out.print("\nSelect an option: ");

                if (!sc.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.nextLine(); // Clear the invalid input
                    continue;
                }

                int choice = sc.nextInt();
                sc.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        vehicleIn(sc);
                        break;
                    case 2:
                        vehicleOut(sc);
                        break;
                    case 3:
                        displayAllRecords();
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }

    public static void vehicleIn(Scanner sc) {
        System.out.print("Vehicle Type?\n");
        String vehicleType = sc.nextLine();
        while (vehicleType.trim().isEmpty()) {
            System.out.println("Vehicle type cannot be empty. Please enter again:");
            vehicleType = sc.nextLine();
        }

        System.out.print("Vehicle Number?\n");
        String vehicleNumber = sc.nextLine();

        LocalDateTime now = LocalDateTime.now();
        String inDate = now.format(DATE_FORMATTER);
        String inTime = now.format(TIME_FORMATTER);

        records.add(new ParkingRecord(vehicleType, vehicleNumber, inDate, inTime));
        System.out.println("Vehicle added successfully!");
    }

    public static void vehicleOut(Scanner sc) {
        if (records.isEmpty()) {
            System.out.println("No records exist. Please add a vehicle first.");
            return;
        }

        System.out.print("Vehicle Number?\n");
        String vehicleNumber = sc.nextLine();

        boolean found = false;
        for (ParkingRecord record : records) {
            if (record.vehicleNumber.equals(vehicleNumber)) {
                if (record.outTime.equals(ParkingRecord.NOT_AVAILABLE)) {
                    LocalDateTime now = LocalDateTime.now();
                    String outDate = now.format(DATE_FORMATTER);
                    String outTime = now.format(TIME_FORMATTER);

                    record.setOutDetails(outDate, outTime);
                    System.out.println("\nVehicle Details:");
                    System.out.println("Vehicle Number: " + record.vehicleNumber);
                    System.out.println("In-Time: " + record.inDate + " " + record.inTime);
                    System.out.println("Out-Time: " + record.outDate + " " + record.outTime);
                    found = true;
                    break;
                } else {
                    System.out.println("Error: Vehicle with number " + vehicleNumber + " is already checked out.");
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            System.out.println("Error: No vehicle found with the number " + vehicleNumber + ".");
        }
    }

    public static void displayAllRecords() {
        if (records.isEmpty()) {
            System.out.println("No records found. The parking lot is empty.");
            return;
        }

        System.out.printf("\n%-6s %-15s %-20s %-15s %-10s %-15s %-10s\n", 
                "Serial", "Vehicle-Type", "Vehicle-Number", "In-Date", "In-Time", "Out-Date", "Out-Time");
        int serial = 1;
        for (ParkingRecord record : records) {
            System.out.println(record.formatRecordWithSerial(serial));
            serial++;
        }
    }
}
