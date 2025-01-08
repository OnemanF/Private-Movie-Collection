package dk.easv.mytunes.privatemoviecollection.DAO;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.mytunes.privatemoviecollection.BE.CatMovie;
import dk.easv.mytunes.privatemoviecollection.BE.Category;
import dk.easv.mytunes.privatemoviecollection.BE.Movie;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CatMovieDAO_DB implements ICatMovieDataAccess {
    private DBConnector dbConnector;
    private MovieDAO_DB movieDAO;

    public CatMovieDAO_DB() throws IOException {
        dbConnector = new DBConnector();
        movieDAO = new MovieDAO_DB();
    }

    @Override
    public CatMovie addMovieToCategory(Movie movie, Category category) throws Exception {
        String sql_insert = "INSERT INTO CatMovie (CategoryID, MovieID) VALUES (?, ?)";

        try (Connection conn = dbConnector.getConnection()) {
            try (PreparedStatement ps_insert = conn.prepareStatement(sql_insert, Statement.RETURN_GENERATED_KEYS)) {
                ps_insert.setInt(1, category.getCategoryID());
                ps_insert.setInt(2, movie.getMovieID());
                ps_insert.executeUpdate();

                ResultSet rs = ps_insert.getGeneratedKeys();
                rs.next();
                int ID = rs.getInt(1);
                return new CatMovie(ID, category.getCategoryID(), movie.getMovieID());
            } catch (SQLException e) {
                conn.rollback();
                throw new Exception("fejl", e);
            }
        }
    }

    @Override
    public List<Movie> getMoviesByCategory(int categoryId) throws IOException, SQLException {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM CatMovie WHERE CategoryID = ?";
        try (Connection conn = new DBConnector().getConnection()) {
            try (PreparedStatement ps_select = conn.prepareStatement(sql)) {
                ps_select.setInt(1, categoryId);
                ResultSet rs = ps_select.executeQuery();
                while (rs.next())
                {
                    Movie movie = movieDAO.getMovieById(rs.getInt("MovieID"));
                    System.out.println(movie.toString());
                    movies.add(movie);
                }
            }

            return movies;
        }
    }
}
