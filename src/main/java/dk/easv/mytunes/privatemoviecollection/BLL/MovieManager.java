package dk.easv.mytunes.privatemoviecollection.BLL;

import dk.easv.mytunes.privatemoviecollection.BE.Movie;
import dk.easv.mytunes.privatemoviecollection.DAO.MovieDAO_DB;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MovieManager {
        private static MovieDAO_DB movieDB;
        private static Search MovieSearch = new Search();

    public MovieManager() throws IOException {
        movieDB = new MovieDAO_DB();
        MovieSearch = new Search();
    }

    public static List<Movie> getAllMovies() throws SQLException, IOException{
        return movieDB.getAllMovies();
    }

    public static List<Movie> MovieSearch(String query) throws Exception {
        List<Movie> allSongs = getAllMovies();
        List<Movie> searchResult = MovieSearch.search(allSongs, query);
        return searchResult;

    }
}
