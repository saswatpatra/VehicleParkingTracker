import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ParkingRecord {
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
        this.outDate = "N/A";
        this.outTime = "N/A";
    }

    void setOutDetails(String outDate, String outTime) {
        this.outDate = outDate;
        this.outTime = outTime;
    }

    String getFormattedRecord(int serial) {
        return String.format("%-6d %-15s %-20s %-15s %-10s %-15s %-10s",
                serial, vehicleType, vehicleNumber, inDate, inTime, outDate, outTime);
    }
}

public class VehicleParkingTracker {
    static List<ParkingRecord> records = new ArrayList<>();
    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nVehicle Parking Tracker");
            System.out.println("1. Vehicle-In");
            System.out.println("2. Vehicle-Out");
            System.out.println("3. All Records");
            System.out.println("4. Exit");
            System.out.print("\nSelect an option: ");

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
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void vehicleIn(Scanner sc) {
        System.out.print("Vehicle Type?\n");
        String vehicleType = sc.nextLine();

        System.out.print("Vehicle Number?\n");
        String vehicleNumber = sc.nextLine();

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
        String inDate = now.format(dateFormatter);
        String inTime = now.format(timeFormatter);

        records.add(new ParkingRecord(vehicleType, vehicleNumber, inDate, inTime));
        System.out.println("Vehicle added successfully!");
    }

    public static void vehicleOut(Scanner sc) {
        System.out.print("Vehicle Number?\n");
        String vehicleNumber = sc.nextLine();

        boolean found = false;
        for (ParkingRecord record : records) {
            if (record.vehicleNumber.equals(vehicleNumber) && record.outTime.equals("N/A")) {
                ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
                String outDate = now.format(dateFormatter);
                String outTime = now.format(timeFormatter);

                record.setOutDetails(outDate, outTime);
                System.out.println("\nVehicle Details:");
                System.out.println("Vehicle Number: " + record.vehicleNumber);
                System.out.println("In-Time: " + record.inDate + " " + record.inTime);
                System.out.println("Out-Time: " + record.outDate + " " + record.outTime);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No matching vehicle found or vehicle already checked out.");
        }
    }

    public static void displayAllRecords() {
        if (records.isEmpty()) {
            System.out.println("No records found.");
            return;
        }

        System.out.printf("\n%-6s %-15s %-20s %-15s %-10s %-15s %-10s\n", 
                "Serial", "Vehicle-Type", "Vehicle-Number", "In-Date", "In-Time", "Out-Date", "Out-Time");
        int serial = 1;
        for (ParkingRecord record : records) {
            System.out.println(record.getFormattedRecord(serial));
            serial++;
        }
    }
}
