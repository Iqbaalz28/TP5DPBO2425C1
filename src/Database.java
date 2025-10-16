import java.sql.*;

public class Database {
    private Connection connection;
    private Statement statement;

    // Constructor
    public Database() {
        try {
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/db_product",
                "root",
                "");
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Uji Koneksi 
    public static void main(String[] args) {
        try {
            Database db = new Database();
            System.out.println("✅ Koneksi ke database berhasil!");
        } catch (Exception e) {
            System.out.println("❌ Koneksi gagal: " + e.getMessage());
        }
    }
        
    // Digunakan untuk SELECT
    public ResultSet selectQuery(String sql) {
        try {
            statement.executeQuery(sql);
            return statement.getResultSet();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Digunakan untuk UPDATE, INSERT, DELETE
    public int insertUpdateDeleteQuery(String sql) {
        try {
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Getter
    public Statement getStatement() {
        return statement;
    }
}