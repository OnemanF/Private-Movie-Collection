package dk.easv.mytunes.privatemoviecollection.GUI.Model;

import dk.easv.mytunes.privatemoviecollection.BE.Category;
import dk.easv.mytunes.privatemoviecollection.BE.Movie;
import dk.easv.mytunes.privatemoviecollection.BLL.CategoryManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CategoryModel {
    private CategoryManager categoryManager = new CategoryManager();
    private ObservableList<Movie> movies = FXCollections.observableArrayList();

    private ObservableList<Category> categories = FXCollections.observableArrayList();

    public CategoryModel() throws IOException {
    }

    public ObservableList<Category> getCategories() throws IOException, SQLException {
        categories.setAll(categoryManager.getCategories());
        return categories;
    }

    public List<Movie> getMoviesByCategory(int categoryId) throws SQLException, IOException {
        return categoryManager.getMoviesByCategory(categoryId);
    }
}
