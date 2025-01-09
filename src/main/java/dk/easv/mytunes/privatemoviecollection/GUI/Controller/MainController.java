package dk.easv.mytunes.privatemoviecollection.GUI.Controller;

import dk.easv.mytunes.privatemoviecollection.BE.CatMovie;
import dk.easv.mytunes.privatemoviecollection.BE.Category;
import dk.easv.mytunes.privatemoviecollection.BE.Movie;
import dk.easv.mytunes.privatemoviecollection.GUI.Model.CatMoviesModel;
import dk.easv.mytunes.privatemoviecollection.GUI.Model.MovieModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    //Tableviews
    @FXML
    private TableView<Movie> movieTableView;
    @FXML
    private TableView<Category> categoryTableView;
    @FXML
    private TableView<Movie> catMovieTableView;

    //coloumns Category, Movie & Catmovie
    @FXML
    private TableColumn<Category, String> categoryNameColumn, colMovies;

    @FXML
    private TableColumn<Movie, String> colTitle, colGenre, colIMBDRating, colPersonalRating;

    @FXML
    private TableColumn<Movie, String> colCatMovieTitle, colCatMovieGenre;

    @FXML
    private TextField txtMovieSearch;


    private final ObservableList<Movie> CatMovieList = FXCollections.observableArrayList();

    private CatMoviesModel catMoviesModel;

    public MainController() throws IOException {
        catMoviesModel = new CatMoviesModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SetupTableViews();

    }

    private void SetupTableViews(){

        colCatMovieTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        colCatMovieGenre.setCellValueFactory(new PropertyValueFactory<>("Genre"));

        // Binding songs to catMovieTableView
        catMovieTableView.setItems(CatMovieList);

        //SongTableView
        colTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("Genre"));
        colIMBDRating.setCellValueFactory(new PropertyValueFactory<>("IMBDRating"));
        colPersonalRating.setCellValueFactory(new PropertyValueFactory<>("PersonalRating"));

        //CategoryTableView
        colMovies.setCellValueFactory(new PropertyValueFactory<>("Movies"));
        categoryNameColumn.setCellValueFactory(new PropertyValueFactory<>("CategoryName"));
        try {
            categoryTableView.setItems(catMoviesModel.getCategories());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        categoryTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    CatMovieList.setAll(catMoviesModel.getMoviesByCategory(newValue.getCategoryID()));
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        //binding Movies to movie table
        movieTableView.setItems(MovieModel.getMovies());


        txtMovieSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                MovieModel.SearchMovie(newValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }





}
