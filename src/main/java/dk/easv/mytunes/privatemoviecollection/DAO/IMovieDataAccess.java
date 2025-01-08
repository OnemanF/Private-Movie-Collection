package dk.easv.mytunes.privatemoviecollection.DAO;

import dk.easv.mytunes.privatemoviecollection.BE.Movie;

import java.sql.SQLException;

public interface IMovieDataAccess {
    Movie createMovie(Movie movie) throws Exception;
    void deleteMovie(Movie movie) throws Exception;
    Movie getMovieById(int id) throws Exception;
}
