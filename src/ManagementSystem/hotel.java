package ManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class hotel {
	
	
	public static void main(String[] args) {
		String url="jdbc:mysql://localhost:3306/hotelmanagementsystem";
		String Username="YOUR USERNAME";
		String password="YOUR PASSWORD";
		Connection connection =null;
		Scanner scanner = new Scanner(System.in);
		try {
			//1.Load the Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			//2.Connection Establish
			connection = DriverManager.getConnection(url,Username,password);
			System.out.println(connection);
			
			 while (true) {
		            System.out.println("\nWelcome To Hotel Management System");
		            System.out.println("1. Add Room");
		            System.out.println("2. View All Rooms");
		            System.out.println("3. Update Room");
		            System.out.println("4. Delete Room");
		            System.out.println("5. Search Room");
		            System.out.println("6. Exit");
		            System.out.print("Enter choice: ");
		            
		            int choice = scanner.nextInt();
		            switch (choice) {
		                case 1 -> hotel.addRoom(connection);
		                case 2 -> hotel.viewroom(connection);
		                case 3 -> hotel.updateRoom(connection);
		                case 4 -> hotel.deleteRoom(connection);
		                case 5 -> hotel.searchRooms(connection);
		                case 6 -> {
		                    System.out.println("Exiting The System...");
		                    System.exit(0);
		                }
		                default -> System.out.println("Invalid choice!");
		            }
		        }
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void deleteRoom(Connection connection) {
		PreparedStatement ps =null;
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Enter the Room Id you want to delete : ");
		int RoomId = scanner.nextInt();
		String sql = "Delete from rooms where room_id = ?";
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1,RoomId);
			
			int rowUpdated =ps.executeUpdate();
			
			System.out.println(rowUpdated>0 ?"Room Deleted Succesfully" :"Room Not Found");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void addRoom(Connection connection) {
		PreparedStatement ps = null;

		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter The Room type you want(Single/Double/Suite) : ");
		String roomType = scanner.nextLine();
		System.out.print("Enter the Room Status you want(Bookd/Unbooked/Maintence : ");
		String roomStatus =scanner.nextLine();
		System.out.print("Enter the price of the room : ");
		double price = scanner.nextDouble();
		System.out.print("Enter the capacity of person in a room : ");
		int capacity = scanner.nextInt();
		
		String Sql = "INSERT INTO rooms (room_type, room_status, price_per_night, capacity) values (?,?,?,?)";
		try {
			
			ps = connection.prepareStatement(Sql);
			ps.setString(1,roomType);
			ps.setString(2, roomStatus);
			ps.setDouble(3,price);
			ps.setInt(4, capacity);
			
			ps.executeUpdate();
            System.out.println("Room added successfully!");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
				
	}
	public static void updateRoom(Connection connection) {
		PreparedStatement ps = null;
		Scanner scanner  = new Scanner(System.in);
		System.out.print("Enter the room Id you want to update : ");
		int roomId =scanner.nextInt();
		
		scanner.nextLine();
		System.out.print("Enter the field you want to update (room_type/room_status/price_per_night/capacity): ");
		String field = scanner.nextLine();
		System.out.print("Enter the new value :");
		String newValue = scanner.nextLine();
		
		String sql = "Update rooms set "+ field +" = ? where room_id = ?";
		try {
			ps=connection.prepareStatement(sql);
			if(field.equals("price_per_night")) {
				ps.setDouble(1,Double.parseDouble(newValue));
			}else if(field.equals("capacity")) {
				ps.setInt(1,Integer.parseInt(newValue));
			}else {
				ps.setString(1, newValue);
			}
			ps.setInt(2, roomId);
			
			int rowUpdated = ps.executeUpdate();
			System.out.println(rowUpdated>0 ? "Rooms Updated Succesfully ":"Room not found");
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
	}
	public static void searchRooms(Connection connection) {
		PreparedStatement ps=null;
		ResultSet rs = null;
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the field you want to search (room_type/room_status/price_per_night/capacity): ");
		String field =scanner.nextLine();
		  System.out.print("Enter value to search: ");
          String value = scanner.nextLine();
        String sql = "SELECT * FROM rooms WHERE " + field + " LIKE ?";
		try {
			  ps=connection.prepareStatement(sql);
			  ps.setString(1,"%"+value+"%");
			
			  rs=ps.executeQuery();
			  System.out.println("\nSearch Results:");
              System.out.println("--------------------------------------------------");
              System.out.printf("%-5s %-10s %-15s %-10s %-8s%n", "ID", "Type", "Status", "Price", "Capacity");
              System.out.println("--------------------------------------------------");

              while (rs.next()) {
                  System.out.printf("%-5d %-10s %-15s %-10.2f %-8d%n",
                          rs.getInt("room_id"),
                          rs.getString("room_type"),
                          rs.getString("room_status"),
                          rs.getDouble("price_per_night"),
                          rs.getInt("capacity"));
              }
              System.out.println("--------------------------------------------------");
          }
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void viewroom(Connection connection) {
		String sql ="Select * from rooms";
		PreparedStatement ps = null;
		ResultSet rs=null;
		try {
			ps=connection.prepareStatement(sql); 
			
			rs = ps.executeQuery();
			 System.out.println("\nRooms in the Hotel:");
	            System.out.println("--------------------------------------------------");
	            System.out.printf("%-5s %-10s %-15s %-10s %-8s%n", "ID", "Type", "Status", "Price", "Capacity");
	            System.out.println("--------------------------------------------------");

	            while (rs.next()) {
	                System.out.printf("%-5d %-10s %-15s %-10.2f %-8d%n",
	                        rs.getInt("room_id"),
	                        rs.getString("room_type"),
	                        rs.getString("room_status"),
	                        rs.getDouble("price_per_night"),
	                        rs.getInt("capacity"));
	            }
	            System.out.println("--------------------------------------------------");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
