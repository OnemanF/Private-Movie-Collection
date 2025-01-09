package dk.easv.mytunes.privatemoviecollection.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class TrailerDAO_DB {
    public void createTrailerTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Trailers (" +
                "id INT PRIMARY KEY IDENTITY(1,1), " +
                "name VARCHAR(255) NOT NULL, " +
                "path VARCHAR(500) NOT NULL)";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
            System.out.println("Trailers-tabel oprettet (hvis den ikke allerede eksisterede).");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addTrailer(String name, String path) {
        String sql = "INSERT INTO Trailers (name, path) VALUES (?, ?)";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, path);
            statement.executeUpdate();
            System.out.println("Trailer tilføjet: " + name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
