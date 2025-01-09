package dk.easv.mytunes.privatemoviecollection.GUI.Controller;

import dk.easv.mytunes.privatemoviecollection.BE.Category;
import dk.easv.mytunes.privatemoviecollection.BE.Movie;
import dk.easv.mytunes.privatemoviecollection.DAO.TrailerDAO_DB;
import dk.easv.mytunes.privatemoviecollection.GUI.Model.CategoryModel;
import dk.easv.mytunes.privatemoviecollection.GUI.Model.MovieModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
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

    @FXML
    private ListView<Category> categoryListView;

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
    private final CategoryModel categoryModel;
    private MovieModel movieModel;
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

        // TODO Hent fra database
        categories = FXCollections.observableArrayList("Action", "Comedy", "Drama", "Horror");
    }

    private void filterMoviesByCategory(String category) {
        // Placeholder logic for filtering movies
        System.out.println("Filtering movies by category: " + category);
    }

    private void SetupTableViews(){

        //SongTableView
        colTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("Genre"));
        colIMBDRating.setCellValueFactory(new PropertyValueFactory<>("IMBDRating"));
        colPersonalRating.setCellValueFactory(new PropertyValueFactory<>("PersonalRating"));

        movieTableView.setItems(movieModel.getMovies());

        try {
            categoryListView.setItems(categoryModel.getCategories());
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }

        // Event listener for selecting a category in the ListView
        categoryListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    // Fetch movies for the selected category
                    CatMovieList.setAll(categoryModel.getMoviesByCategory(newValue.getCategoryID()));
                    movieTableView.setItems(CatMovieList);
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

    public void initializeDatabase() {
        TrailerDAO_DB trailerDAO = new TrailerDAO_DB();
        trailerDAO.createTrailerTable(); // Opret tabellen
    }

    public static void importTrailers() {
        TrailerDAO_DB trailerDAO = new TrailerDAO_DB();

        // Sti til din movies-mappe
        File folder = new File("src/movies");
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".mp4")) {
                    String name = file.getName(); // Filnavnet
                    String path = file.getAbsolutePath(); // Stien til filen

                    // Indsæt data i databasen
                    trailerDAO.addTrailer(name, path);
                }
            }
        }
    }
    public static void main(String[] args) {
        TrailerDAO_DB trailerDAO = new TrailerDAO_DB();

        // Opret trailertabel, hvis den ikke findes
        trailerDAO.createTrailerTable();

        // Importér trailers fra mappen
        importTrailers();
    }



}

