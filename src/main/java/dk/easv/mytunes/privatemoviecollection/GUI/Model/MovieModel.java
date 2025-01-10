package dk.easv.mytunes.privatemoviecollection.GUI.Model;

import dk.easv.mytunes.privatemoviecollection.BE.Movie;
import dk.easv.mytunes.privatemoviecollection.BLL.MovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class MovieModel {
    private final ObservableList<Movie> moviesList;
    private final MovieManager movieManager;
    private final ObservableList<Movie> allMovies = FXCollections.observableArrayList();


    public MovieModel() throws Exception {
        this.movieManager = new MovieManager(this);
        this.moviesList = FXCollections.observableArrayList();
        allMovies.setAll(movieManager.getAllMovies());
        refreshMoviesList();
    }

    private void refreshMoviesList() throws Exception {
        moviesList.clear();
        moviesList.addAll(movieManager.getAllMovies());
    }

    public ObservableList<Movie> getMovies() {
        return moviesList;
    }
    public ObservableList<Movie> getAllMovies() { return allMovies; }

    public void searchMovies(String query) throws Exception {
        List<Movie> searchResult = movieManager.searchMovies(query); // Use instance method
        moviesList.clear();
        moviesList.addAll(searchResult);
    }

    public void addMovie(Movie movie) throws Exception {
        try {
            Movie createdMovie = movieManager.addMovie(movie);
            allMovies.add(createdMovie);
            refreshMoviesList();
        } catch (Exception e) {
            throw new Exception("Failed to add movie: " + e.getMessage(), e);
        }
    }
}
