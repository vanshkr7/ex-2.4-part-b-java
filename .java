import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/shopdb";
        String user = "root";
        String password = "your_password";
        Scanner sc = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            con.setAutoCommit(false); // Enable transaction management

            while (true) {
                System.out.println("\n===== PRODUCT MENU =====");
                System.out.println("1. Add Product");
                System.out.println("2. View All Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int ch = sc.nextInt();

                try {
                    switch (ch) {
                        case 1:
                            System.out.print("Enter Product Name: ");
                            String pname = sc.next();
                            System.out.print("Enter Price: ");
                            double price = sc.nextDouble();
                            System.out.print("Enter Quantity: ");
                            int qty = sc.nextInt();

                            String insert = "INSERT INTO Product (ProductName, Price, Quantity) VALUES (?, ?, ?)";
                            PreparedStatement ps1 = con.prepareStatement(insert);
                            ps1.setString(1, pname);
                            ps1.setDouble(2, price);
                            ps1.setInt(3, qty);
                            ps1.executeUpdate();
                            con.commit();
                            System.out.println("✅ Product added successfully!");
                            break;

                        case 2:
                            Statement stmt = con.createStatement();
                            ResultSet rs = stmt.executeQuery("SELECT * FROM Product");
                            System.out.println("ID\tName\t\tPrice\tQuantity");
                            System.out.println("---------------------------------------");
                            while (rs.next()) {
                                System.out.println(rs.getInt("ProductID") + "\t" +
                                        rs.getString("ProductName") + "\t\t" +
                                        rs.getDouble("Price") + "\t" +
                                        rs.getInt("Quantity"));
                            }
                            break;

                        case 3:
                            System.out.print("Enter Product ID to update: ");
                            int uid = sc.nextInt();
                            System.out.print("Enter new price: ");
                            double newPrice = sc.nextDouble();
                            String update = "UPDATE Product SET Price=? WHERE ProductID=?";
                            PreparedStatement ps2 = con.prepareStatement(update);
                            ps2.setDouble(1, newPrice);
                            ps2.setInt(2, uid);
                            ps2.executeUpdate();
                            con.commit();
                            System.out.println("✅ Product updated successfully!");
                            break;

                        case 4:
                            System.out.print("Enter Product ID to delete: ");
                            int did = sc.nextInt();
                            String delete = "DELETE FROM Product WHERE ProductID=?";
                            PreparedStatement ps3 = con.prepareStatement(delete);
                            ps3.setInt(1, did);
                            ps3.executeUpdate();
                            con.commit();
                            System.out.println("✅ Product deleted successfully!");
                            break;

                        case 5:
                            System.out.println("Exiting...");
                            sc.close();
                            con.close();
                            System.exit(0);

                        default:
                            System.out.println("❌ Invalid choice! Try again.");
                    }
                } catch (Exception e) {
                    System.out.println("⚠️ Error occurred, rolling back transaction.");
                    con.rollback(); // rollback changes on error
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
