package dk.easv.mytunes.privatemoviecollection.DAO;

import dk.easv.mytunes.privatemoviecollection.BE.Category;
import dk.easv.mytunes.privatemoviecollection.BE.Movie;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO_DB implements IMovieDataAccess {
    private DBConnector dbConnector;

    public MovieDAO_DB() throws IOException {
        dbConnector = new DBConnector();
    }

    public List<Movie> getAllMovies() throws SQLException, IOException {
        String sql = "SELECT * FROM Movie";
        List<Movie> movies = new ArrayList<>();

        try (Connection conn = new DBConnector().getConnection();
             PreparedStatement ps_select = conn.prepareStatement(sql);
             ResultSet rs = ps_select.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("MovieID");
                String title = rs.getString("title");
                int imdbRating = rs.getInt("IMBDRating");
                int personalRating = rs.getInt("personalRating");
                int lastView = rs.getInt("lastView");

                movies.add(new Movie(id, title, imdbRating, personalRating, lastView));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving movies", e);
        }

        return movies;
    }

    @Override
    public Movie createMovie(Movie movie, List<Category> categories) throws Exception {
        return null;
    }

    @Override
    public Movie getMovieById(int ID) throws IOException {
        String sql = "SELECT * FROM Movie WHERE MovieID = ?";
        try (Connection conn = new DBConnector().getConnection()) {
            try (PreparedStatement ps_select = conn.prepareStatement(sql)) {
                ps_select.setInt(1, ID);
                ResultSet rs = ps_select.executeQuery();
                if (rs.next()) {
                    return new Movie(ID, rs.getString("title"), rs.getInt("imdbRating"), rs.getInt("personalRating"), rs.getInt("lastView"));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Movie createMovie(Movie movie) throws Exception {
        String sql_insert = "INSERT INTO Movie (title, imdbRating, personalRating, lastview) VALUES (?, ?, ?, ?)";

        try(Connection conn = dbConnector.getConnection(); PreparedStatement ps_insert = conn.prepareStatement(sql_insert, Statement.RETURN_GENERATED_KEYS)){
            ps_insert.setString(1, movie.getTitle());
            ps_insert.setInt(2, movie.getIMBDRating());
            ps_insert.setInt(3, movie.getPersonalRating());
            ps_insert.setInt(4, movie.getLastView());
            ps_insert.executeUpdate();
            ResultSet rs = ps_insert.getGeneratedKeys();
            rs.next();
            int ID = rs.getInt(1);
            return new Movie(ID, movie.getTitle(), movie.getIMBDRating(), movie.getPersonalRating(), movie.getLastView());
        }

        catch(Exception e){
            throw new Exception("Unable to create the movie " + movie.getTitle().trim(), e);
        }
    }

    @Override
    public void deleteMovie(Movie movie) throws Exception {
        String delete_categoriesSQL = "DELETE FROM Movie WHERE MovieID = ?";
        String delete_connectionSQL = "DELETE FROM CatMovie WHERE MovieID = ?";

        try (Connection conn = dbConnector.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps_delete_connection = conn.prepareStatement(delete_connectionSQL)) {
                ps_delete_connection.setInt(1, movie.getMovieID());
                ps_delete_connection.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                System.out.println(e);
                throw new Exception("Unable to delete the movie from CatMovie " + movie.getTitle().trim(), e);
            }


            try (PreparedStatement ps_delete_category = conn.prepareStatement(delete_categoriesSQL)) {
                ps_delete_category.setInt(1, movie.getMovieID());
                ps_delete_category.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                System.out.println(e);
                throw new Exception("Unable to delete the movie from Movie " + movie.getTitle().trim(), e);
            }
        }
    }

}
