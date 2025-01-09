package dk.easv.mytunes.privatemoviecollection.BLL;

import dk.easv.mytunes.privatemoviecollection.BE.Movie;
import dk.easv.mytunes.privatemoviecollection.DAO.MovieDAO_DB;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MovieManager {
    private final MovieDAO_DB movieDB;
    private final Search movieSearch;


    public MovieManager() throws IOException {
        this.movieDB = new MovieDAO_DB(); // Initialize database access object
        this.movieSearch = new Search(); // Initialize search helper
    }


    public List<Movie> getAllMovies() throws SQLException, IOException {
        return movieDB.getAllMovies();
    }


    public List<Movie> searchMovies(String query) throws Exception {
        List<Movie> allMovies = getAllMovies(); // Fetch all movies from DB
        return movieSearch.search(allMovies, query); // Search for matching movies
    }
}
