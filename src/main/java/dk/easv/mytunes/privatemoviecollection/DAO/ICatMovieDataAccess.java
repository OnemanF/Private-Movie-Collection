package dk.easv.mytunes.privatemoviecollection.DAO;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.mytunes.privatemoviecollection.BE.CatMovie;
import dk.easv.mytunes.privatemoviecollection.BE.Category;
import dk.easv.mytunes.privatemoviecollection.BE.Movie;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ICatMovieDataAccess {
    public CatMovie addMovieToCategory(Movie movie, Category category) throws Exception;

    List<String> getCategoriesByMovie(int categoryId) throws IOException, SQLException;

    List<Movie> getMoviesByCategory(int categoryId) throws IOException, SQLException;
}
