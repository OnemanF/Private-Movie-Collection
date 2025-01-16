package dk.easv.mytunes.privatemoviecollection.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO implements IGenreDAO {
    public List<dk.easv.mytunes.privatemoviecollection.BE.Genre> getAllGenres() throws SQLException {
        String sql = "SELECT * FROM Genre";
        List<dk.easv.mytunes.privatemoviecollection.BE.Genre> genres = new ArrayList<>();

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                genres.add(new dk.easv.mytunes.privatemoviecollection.BE.Genre(rs.getInt("GenreID"), rs.getString("GenreName")));
            }
        }
        return genres;
    }
}
