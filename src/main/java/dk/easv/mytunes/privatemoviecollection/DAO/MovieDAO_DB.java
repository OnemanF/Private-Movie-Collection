package dk.easv.mytunes.privatemoviecollection.DAO;
import dk.easv.mytunes.privatemoviecollection.BE.Category;
import dk.easv.mytunes.privatemoviecollection.BE.Genre;
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
                String lastView = rs.getString("lastView");
                String genre = rs.getString("genre");
                String filePath = rs.getString("filePath");

                movies.add(new Movie(id, title, imdbRating, personalRating, lastView, genre, filePath));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving movies", e);
        }

        return movies;
    }


    @Override
    public Movie createMovie(Movie movie, List<Genre> genres) throws Exception {
        String sql_insert = "INSERT INTO Movie (title, IMBDRating, personalRating, lastview, genre, filePath) VALUES (?, ?, ?, ?, ?, ?)";

        List<String> genreNames = new ArrayList<>();
        for (Genre genre : genres) {
            genreNames.add(genre.getGenreName());
        }
        movie.setGenre(String.join(",", genreNames));

        try (Connection conn = dbConnector.getConnection();
             PreparedStatement ps_insert = conn.prepareStatement(sql_insert, Statement.RETURN_GENERATED_KEYS)) {

            ps_insert.setString(1, movie.getTitle());
            ps_insert.setInt(2, movie.getIMBDRating());
            ps_insert.setInt(3, movie.getPersonalRating());
            ps_insert.setString(4, movie.getLastView());
            ps_insert.setString(5, movie.getGenre());
            ps_insert.setString(6, movie.getFilePath());

            ps_insert.executeUpdate();

            try (ResultSet rs = ps_insert.getGeneratedKeys()) {
                if (rs.next()) {
                    int ID = rs.getInt(1);
                    return new Movie(ID, movie.getTitle(), movie.getIMBDRating(), movie.getPersonalRating(),
                            movie.getLastView(), movie.getGenre(), movie.getFilePath());
                } else {
                    throw new SQLException("Failed to retrieve the generated movie ID.");
                }
            }
        } catch (SQLException e) {
            throw new Exception("Unable to create the movie: " + movie.getTitle().trim() + ". Error: " + e.getMessage(), e);
        }
    }

    @Override
    public void setLastPlayed(Movie movie) throws Exception {
        String sql_update = "UPDATE Movie set lastView = ? WHERE MovieID = ?";

        try (Connection conn = dbConnector.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement sql_update_connection = conn.prepareStatement(sql_update)) {
                sql_update_connection.setString(1, movie.getLastView());
                sql_update_connection.setInt(2, movie.getMovieID());
                sql_update_connection.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                System.out.println(e);
                throw new Exception("Unable to delete the movie from CatMovie " + movie.getTitle().trim(), e);
            }
        }
    }

    @Override
    public void deleteMovie(Movie movie) throws Exception {
        String delete_movie_genre_SQL = "DELETE FROM MovieGenre WHERE MovieID = ?";
        String delete_movie_sql = "DELETE FROM Movie WHERE MovieID = ?";
        String delete_cat_movie_sql = "DELETE FROM CatMovie WHERE MovieID = ?";

        try (Connection conn = dbConnector.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps_delete_movie_genre = conn.prepareStatement(delete_movie_genre_SQL)) {
                ps_delete_movie_genre.setInt(1, movie.getMovieID());
                ps_delete_movie_genre.executeUpdate();
            }

            try (PreparedStatement ps_delete_cat_movie = conn.prepareStatement(delete_cat_movie_sql)) {
                ps_delete_cat_movie.setInt(1, movie.getMovieID());
                ps_delete_cat_movie.executeUpdate();
            }

            try (PreparedStatement ps_delete_movie = conn.prepareStatement(delete_movie_sql)) {
                ps_delete_movie.setInt(1, movie.getMovieID());
                ps_delete_movie.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            try (Connection conn = dbConnector.getConnection()) {
                conn.rollback();
            }
            throw new Exception("Failed to delete movie " + movie.getTitle() + " due to: " + e.getMessage(), e);
        }
    }

    @Override
    public void addMovieToGenre(Movie movie, Genre genre) throws SQLException {
        String sql = "INSERT INTO MovieGenre (movieID, genreID) VALUES (?, ?)";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, movie.getMovieID());
            ps.setInt(2, genre.getGenreID());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error adding movie to genre: " + e.getMessage(), e);
        }
    }

    @Override
    public void addMovieToCategory(Movie movie, Category category) throws Exception {
        String sql = "INSERT INTO CatMovie (MovieID, CategoryID) VALUES (?, ?)";

        try (Connection connection = DBConnector.getConnection();  // Get the connection from DBConnector
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, movie.getMovieID());  // MovieID from the Movie object
            statement.setInt(2, category.getCategoryID());  // CategoryID from the Category object

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new Exception("Error adding movie to category: " + e.getMessage(), e);
        }
    }
}
