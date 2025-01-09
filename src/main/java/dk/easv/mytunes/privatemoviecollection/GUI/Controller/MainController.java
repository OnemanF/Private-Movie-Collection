package dk.easv.mytunes.privatemoviecollection.GUI.Controller;

import dk.easv.mytunes.privatemoviecollection.BE.Category;
import dk.easv.mytunes.privatemoviecollection.BE.Movie;
import dk.easv.mytunes.privatemoviecollection.GUI.Model.CategoryModel;
import dk.easv.mytunes.privatemoviecollection.GUI.Model.MovieModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
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

    private String folder = "movies\\";

    private final ObservableList<Movie> CatMovieList = FXCollections.observableArrayList();

    private CategoryModel categoryModel;

    private MovieModel movieModel;

    @FXML
    private ComboBox<String> categoryComboBox;

    private ObservableList<String> categories;


    public MainController() throws IOException {
        categoryModel = new CategoryModel();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            movieModel = new MovieModel();
            SetupTableViews();
        } catch (Exception e) {
            e.printStackTrace();
        }

        categories = FXCollections.observableArrayList("Action", "Comedy", "Drama", "Horror");

        // Bind the categories to the ComboBox
        categoryComboBox.setItems(categories);

        // Set up a listener for selection
        categoryComboBox.setOnAction(event -> {
            String selectedCategory = categoryComboBox.getSelectionModel().getSelectedItem();
            System.out.println("Selected Category: " + selectedCategory);

            // Handle filtering or logic based on selected category
            filterMoviesByCategory(selectedCategory);
        });

    }

    private void filterMoviesByCategory(String category) {
        // Placeholder logic for filtering movies
        System.out.println("Filtering movies by category: " + category);
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

        movieTableView.setItems(movieModel.getMovies());

        //CategoryTableView
        colMovies.setCellValueFactory(new PropertyValueFactory<>("Movies"));
        categoryNameColumn.setCellValueFactory(new PropertyValueFactory<>("CategoryName"));
        try {
            categoryTableView.setItems(categoryModel.getCategories());
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
        categoryTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    CatMovieList.setAll(categoryModel.getMoviesByCategory(newValue.getCategoryID()));
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        txtMovieSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                movieModel.searchMovies(newValue); // Use instance method
                movieTableView.refresh(); // Refresh the table to show filtered results
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    }

