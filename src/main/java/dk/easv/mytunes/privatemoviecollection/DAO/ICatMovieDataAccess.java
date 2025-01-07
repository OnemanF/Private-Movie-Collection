package dk.easv.mytunes.privatemoviecollection.DAO;

import dk.easv.mytunes.privatemoviecollection.BE.CatMovie;
import dk.easv.mytunes.privatemoviecollection.BE.Category;
import dk.easv.mytunes.privatemoviecollection.BE.Movie;

public interface ICatMovieDataAccess {
    public CatMovie addMovieToCategory(Movie movie, Category category) throws Exception;
}
