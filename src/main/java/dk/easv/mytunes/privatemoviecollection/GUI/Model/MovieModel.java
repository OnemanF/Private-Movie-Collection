package dk.easv.mytunes.privatemoviecollection.GUI.Model;

import dk.easv.mytunes.privatemoviecollection.BE.Movie;
import dk.easv.mytunes.privatemoviecollection.BLL.MovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class MovieModel {
    private final ObservableList<Movie> moviesList;
    private final MovieManager movieManager;

    public MovieModel() throws Exception {
        this.movieManager = new MovieManager();
        this.moviesList = FXCollections.observableArrayList();
        refreshMoviesList();
    }

    private void refreshMoviesList() throws Exception {
        moviesList.clear();
        moviesList.addAll(movieManager.getAllMovies());
    }

    public ObservableList<Movie> getMovies() {
        return moviesList;
    }

    public void searchMovies(String query) throws Exception {
        List<Movie> searchResult = movieManager.searchMovies(query); // Use instance method
        moviesList.clear();
        moviesList.addAll(searchResult);
    }
}
