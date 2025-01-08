package dk.easv.mytunes.privatemoviecollection.BLL;

import dk.easv.mytunes.privatemoviecollection.BE.Category;
import dk.easv.mytunes.privatemoviecollection.BE.Movie;
import dk.easv.mytunes.privatemoviecollection.DAO.CatMovieDAO_DB;
import dk.easv.mytunes.privatemoviecollection.DAO.CategoryDAO_DB;
import dk.easv.mytunes.privatemoviecollection.DAO.MovieDAO_DB;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CatMovieManager {
    private CatMovieDAO_DB catMovieDAO;
    private CategoryDAO_DB categoryDAO;
    private MovieDAO_DB movieDAO;

    public CatMovieManager() throws IOException {
        catMovieDAO = new CatMovieDAO_DB();
        categoryDAO = new CategoryDAO_DB();
        movieDAO = new MovieDAO_DB();
    }

    public List<Movie> getMoviesByCategory(int categoryId) throws SQLException, IOException {
        return catMovieDAO.getMoviesByCategory(categoryId);
    }

    public List<Category> getCategories() throws IOException {
        return categoryDAO.getCategories();
    }


    public Movie getMovieById(int ID) throws IOException {
        return movieDAO.getMovieById(ID);
    }
}
