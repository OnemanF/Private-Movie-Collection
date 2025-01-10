package dk.easv.mytunes.privatemoviecollection.BLL;

import dk.easv.mytunes.privatemoviecollection.BE.Movie;
import dk.easv.mytunes.privatemoviecollection.DAO.CatMovieDAO_DB;
import dk.easv.mytunes.privatemoviecollection.DAO.MovieDAO_DB;
import dk.easv.mytunes.privatemoviecollection.GUI.Model.MovieModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MovieManager {
    private final MovieDAO_DB movieDB;
    private final CatMovieDAO_DB catMovieDB;
    private final Search movieSearch;
    private final MovieModel movieModel;

    public MovieManager(MovieModel movieModel) throws IOException {
        this.movieDB = new MovieDAO_DB();
        this.catMovieDB = new CatMovieDAO_DB();
        this.movieSearch = new Search();
        this.movieModel = movieModel;
    }


    public List<Movie> getAllMovies() throws SQLException, IOException {
        List<Movie> movies = movieDB.getAllMovies();
        for (Movie movie : movies) {
            movie.setGenre(updateGenre(movie));
        }

        return movies;
    }

    public String updateGenre(Movie movie) throws SQLException, IOException {
        List<String> genreNames = catMovieDB.getCategoriesByMovie(movie.getMovieID());
        return String.join(",", genreNames);
    }

    public List<Movie> searchMovies(String query) throws Exception {
        List<Movie> allMovies = movieModel.getAllMovies();
        return movieSearch.search(allMovies, query);
    }

    public Movie addMovie(Movie movie) throws Exception {
        try {
            return movieDB.createMovie(movie);
        } catch (Exception e) {
            throw new Exception("Error adding movie to the database: " + e.getMessage(), e);
        }
    }
}
