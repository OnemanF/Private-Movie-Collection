package dk.easv.mytunes.privatemoviecollection.GUI.Model;

import dk.easv.mytunes.privatemoviecollection.BE.Category;
import dk.easv.mytunes.privatemoviecollection.BE.Movie;
import dk.easv.mytunes.privatemoviecollection.BLL.CatMovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CatMoviesModel {
    private CatMovieManager catMovieManager = new CatMovieManager();
    private ObservableList<Movie> movies = FXCollections.observableArrayList();
    private ObservableList<Category> categories = FXCollections.observableArrayList();

    public CatMoviesModel() throws IOException {
        categories.setAll(catMovieManager.getCategories());

    }

    public ObservableList<Category> getCategories() throws IOException {
        categories.setAll(catMovieManager.getCategories());
        return categories;
    }

    public List<Movie> getMoviesByCategory(int categoryId) throws SQLException, IOException {
        return catMovieManager.getMoviesByCategory(categoryId);
    }
}
