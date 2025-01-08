package dk.easv.mytunes.privatemoviecollection.GUI.Controller;

import dk.easv.mytunes.privatemoviecollection.BE.Category;
import dk.easv.mytunes.privatemoviecollection.BE.Movie;
import dk.easv.mytunes.privatemoviecollection.GUI.Model.CatMoviesModel;
import dk.easv.mytunes.privatemoviecollection.GUI.Model.MovieModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CatMovieController implements Initializable {
    @FXML
    private TableView<Movie> movieTableView;

    @FXML
    private TableColumn<Category, String> categoryNameColumn;

    @FXML
    private TableView<Category> categoryTableView;

    private CatMoviesModel catMoviesModel;

    public CatMovieController() throws IOException {
        catMoviesModel = new CatMoviesModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categoryNameColumn.setCellValueFactory(new PropertyValueFactory<>("CategoryName"));
        try {
            categoryTableView.setItems(catMoviesModel.getCategories());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        categoryTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    catMoviesModel.showMovies(newValue.getCategoryID());
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
