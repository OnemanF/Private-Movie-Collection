package dk.easv.mytunes.privatemoviecollection.DAO;

import dk.easv.mytunes.privatemoviecollection.BE.Movie;

public interface IMovieDataAccess {
    Movie createMovie(Movie movie) throws Exception;
    void deleteMovie(Movie movie);
}
