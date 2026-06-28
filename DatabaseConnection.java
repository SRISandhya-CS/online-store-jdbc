import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseConnection {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/online_store";
        String username = "postgres";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected to the online store database successfully!");
            Scanner scanner = new Scanner(System.in);
            boolean exit = false;

            while (!exit) {
                System.out.println("\n--- Online Store Menu ---");
                System.out.println("1. View all products");
                System.out.println("2. Add a new product");
                System.out.println("3. Search for a product");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        viewAllProducts(connection);
                        break;
                    case 2:
                        addNewProduct(connection, scanner);
                        break;
                    case 3:
                        searchProduct(connection, scanner);
                        break;
                    case 4:
                        exit = true;
                        System.out.println("Exiting application. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewAllProducts(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM products");
        System.out.println("\n--- Products List ---");
        while (resultSet.next()) {
            System.out.println("ID: " + resultSet.getInt("id") + 
                               ", Name: " + resultSet.getString("name") + 
                               ", Price: " + resultSet.getDouble("price") + 
                               ", Stock: " + resultSet.getInt("stock_level"));
        }
        resultSet.close();
        statement.close();
    }

    private static void addNewProduct(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter stock level: ");
        int stock = scanner.nextInt();

        String sql = "INSERT INTO products (name, price, stock_level) VALUES (?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setDouble(2, price);
        pstmt.setInt(3, stock);
        pstmt.executeUpdate();
        System.out.println("Product added successfully!");
        pstmt.close();
    }

    private static void searchProduct(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter product name to search: ");
        String searchTerm = scanner.nextLine();

        String sql = "SELECT * FROM products WHERE name ILIKE ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, "%" + searchTerm + "%");
        ResultSet resultSet = pstmt.executeQuery();

        System.out.println("\n--- Search Results ---");
        boolean found = false;
        while (resultSet.next()) {
            System.out.println("ID: " + resultSet.getInt("id") + 
                               ", Name: " + resultSet.getString("name") + 
                               ", Price: " + resultSet.getDouble("price") + 
                               ", Stock: " + resultSet.getInt("stock_level"));
            found = true;
        }
        if (!found) {
            System.out.println("No products found matching that name.");
        }
        resultSet.close();
        pstmt.close();
    }
}