package dk.easv.mytunes.privatemoviecollection.BLL;

import dk.easv.mytunes.privatemoviecollection.BE.Category;
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
            //movie.setGenre(getGenre(movie));
        }

        return movies;
    }

    public List<Movie> searchMovies(String query) throws Exception {
        List<Movie> allMovies = movieModel.getAllMovies();
        return movieSearch.search(allMovies, query);
    }

    public Movie addMovie(Movie movie, List<Category> categories) throws Exception {
        try {
            Movie movieToReturn = movieDB.createMovie(movie, categories);
            for (Category category : categories) {
                catMovieDB.addMovieToCategory(movieToReturn, category);
            }
            return movieToReturn;
        } catch (Exception e) {
            throw new Exception("Error adding movie to the database: " + e.getMessage(), e);
        }
    }

    public void removeMovie(Movie movie) throws Exception {
        movieDB.deleteMovie(movie);
    }

}
