package dk.easv.mytunes.privatemoviecollection.GUI.Model;

import dk.easv.mytunes.privatemoviecollection.BE.Movie;
import dk.easv.mytunes.privatemoviecollection.BLL.MovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class MovieModel {
        private static ObservableList<Movie> moviesList;
        private MovieManager movieManager;

    public MovieModel() throws Exception  {
        movieManager = new MovieManager();
        moviesList = FXCollections.observableArrayList();
        moviesList.addAll(MovieManager.getAllMovies());
    }

        public static ObservableList<Movie> getMovies() {
        return moviesList;
    }

        public static void SearchMovie(String query) throws Exception {
            List<Movie> searchResult = MovieManager.MovieSearch(query);
            moviesList.clear();
            moviesList.addAll(searchResult);
        }

    }
