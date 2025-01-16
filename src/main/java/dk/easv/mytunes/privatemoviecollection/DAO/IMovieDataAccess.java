package dk.easv.mytunes.privatemoviecollection.DAO;

import dk.easv.mytunes.privatemoviecollection.BE.Category;
import dk.easv.mytunes.privatemoviecollection.BE.Genre;
import dk.easv.mytunes.privatemoviecollection.BE.Movie;
import java.sql.SQLException;
import java.util.List;

public interface IMovieDataAccess {
    List<Movie> getAllMovies() throws Exception;

    void deleteMovie(Movie movie) throws Exception;

    Movie createMovie(Movie movie, List<Genre> genres) throws Exception;

    void setLastPlayed(Movie movie) throws Exception;

    void addMovieToGenre(Movie movie, Genre genre) throws SQLException;

    void addMovieToCategory(Movie movie, Category category) throws Exception;
}
