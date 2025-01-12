package dk.easv.mytunes.privatemoviecollection.GUI.Model;

import dk.easv.mytunes.privatemoviecollection.BE.Category;
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

    public Movie addMovie(Movie movie, List<Category> categories) throws Exception {
        try {
            Movie createdMovie = movieManager.addMovie(movie, categories);
            System.out.println(createdMovie);
            moviesList.add(createdMovie);
            allMovies.add(createdMovie);
            return createdMovie;
        } catch (Exception e) {
            throw new Exception("Failed to add movie: " + e.getMessage(), e);
        }
    }

    public void removeMovie(Movie movie) throws Exception {
        try {
            movieManager.removeMovie(movie);


            moviesList.remove(movie);
            allMovies.remove(movie);
        } catch (Exception e) {
            throw new Exception("Failed to remove movie: " + e.getMessage(), e);
        }
    }

}
