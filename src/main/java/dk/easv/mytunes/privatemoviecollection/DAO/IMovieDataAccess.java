package dk.easv.mytunes.privatemoviecollection.DAO;

import dk.easv.mytunes.privatemoviecollection.BE.Movie;

import java.sql.SQLException;
import java.util.List;

public interface IMovieDataAccess {
    List<Movie> getAllMovies() throws Exception;
    Movie createMovie(Movie movie) throws Exception;
    void deleteMovie(Movie movie) throws Exception;
    Movie getMovieById(int id) throws Exception;
}
