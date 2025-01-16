package dk.easv.mytunes.privatemoviecollection.BLL;

import dk.easv.mytunes.privatemoviecollection.BE.Category;
import dk.easv.mytunes.privatemoviecollection.BE.Movie;
import dk.easv.mytunes.privatemoviecollection.DAO.CatMovieDAO_DB;
import dk.easv.mytunes.privatemoviecollection.DAO.CategoryDAO_DB;
import dk.easv.mytunes.privatemoviecollection.DAO.DBConnector;
import dk.easv.mytunes.privatemoviecollection.DAO.ICategoryDataAccess;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CategoryManager {
    private final ICategoryDataAccess categoryDAO_DB;
    private final CatMovieDAO_DB catMovieDAO_DB;

    public CategoryManager() throws IOException {
        categoryDAO_DB = new CategoryDAO_DB();
        catMovieDAO_DB = new CatMovieDAO_DB();
    }

    public List<Category> getCategories() throws IOException, SQLException {
        List<Category> categories = categoryDAO_DB.getCategories();
        for (Category category : categories) {
            category.setMovies(getMoviesByCategory(category.getCategoryID()).size());
        }
        return categories;
    }

    public List<Movie> getMoviesByCategory(int categoryId) throws SQLException, IOException {
        return catMovieDAO_DB.getMoviesByCategory(categoryId);
    }

    public void addCategory(String categoryName) throws SQLException {
        categoryDAO_DB.addCategory(categoryName);
    }
    public void deleteCategory(Category category) throws Exception {
        categoryDAO_DB.deleteCategory(category);
    }
}
