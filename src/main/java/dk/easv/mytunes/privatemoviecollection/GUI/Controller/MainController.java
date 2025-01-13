package dk.easv.mytunes.privatemoviecollection.GUI.Controller;

import dk.easv.mytunes.privatemoviecollection.BE.Category;
import dk.easv.mytunes.privatemoviecollection.BE.Movie;
import dk.easv.mytunes.privatemoviecollection.DAO.TrailerDAO_DB;
import dk.easv.mytunes.privatemoviecollection.GUI.Model.CategoryModel;
import dk.easv.mytunes.privatemoviecollection.GUI.Model.MovieModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

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

    @FXML
    private Button addMovieButton;

    //coloumns Category, Movie & Catmovie
    @FXML
    private TableColumn<Category, String> categoryNameColumn, colMovies;

    @FXML
    private TableColumn<Movie, String> colTitle, colGenre, colIMBDRating, colPersonalRating;

    @FXML
    private TableColumn<Movie, String> colCatMovieTitle, colCatMovieGenre;

    @FXML
    private TextField txtMovieSearch;

    @FXML
    private void AddMovie() {
        showAddMovieDialog();
    }

    private String folder = "movies\\";
    private final ObservableList<Movie> CatMovieList = FXCollections.observableArrayList();
    private final CategoryModel categoryModel;
    private MovieModel movieModel;
    private ObservableList<String> categories;
    private Category selectedCategory = null;

    public MainController() throws IOException {
        categoryModel = new CategoryModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            movieModel = new MovieModel();
            SetupTableViews();
            addMovieButton.setOnAction(event -> showAddMovieDialog());

            movieTableView.setItems(movieModel.getMovies());
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

        movieTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
           if (newValue != null) {
               try {
                   playMovie(newValue);
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
        });

        categoryListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    // Fetch movies for the selected category
                    CatMovieList.setAll(categoryModel.getMoviesByCategory(newValue.getCategoryID()));
                    movieTableView.setItems(CatMovieList);
                    selectedCategory = newValue;
                    searchMovie(txtMovieSearch.getText());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });


        txtMovieSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
               searchMovie(newValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void searchMovie(String newValue) throws Exception {
        List<Movie> movies = movieModel.searchMovies(newValue, selectedCategory);
        movieTableView.setItems((ObservableList<Movie>) movies);
        movieTableView.refresh();
    }

    public void playMovie(Movie movie) throws IOException {
        File file = new File("src/main/java/dk/easv/mytunes/privatemoviecollection/movies/" + movie.getTitle() + ".mp4");
        Desktop.getDesktop().open(file);
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

    private void showAddMovieDialog() {
        Dialog<Movie> dialog = new Dialog<>();
        dialog.setTitle("Add Movie");

        TextField titleField = new TextField();
        titleField.setPromptText("Movie Title");

        TextField imdbRatingField = new TextField();
        imdbRatingField.setPromptText("IMDB Rating");

        TextField personalRatingField = new TextField();
        personalRatingField.setPromptText("Personal Rating");

        ComboBox<Category> categoryDropdown = new ComboBox<>();
        try {
            categoryDropdown.setItems(categoryModel.getCategories());
            categoryDropdown.setPromptText("Select Category");
        } catch (Exception e) {
            System.out.println("Error loading categories: " + e.getMessage());
            return;
        }


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("IMDB Rating:"), 0, 1);
        grid.add(imdbRatingField, 1, 1);
        grid.add(new Label("Personal Rating:"), 0, 2);
        grid.add(personalRatingField, 1, 2);
        grid.add(new Label("Category:"), 0, 3);
        grid.add(categoryDropdown, 1, 3);

        dialog.getDialogPane().setContent(grid);


        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);
        List<Category> categorys = new ArrayList<>();
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                try {
                    String title = titleField.getText().trim();
                    int imdbRating = Integer.parseInt(imdbRatingField.getText().trim());
                    int personalRating = Integer.parseInt(personalRatingField.getText().trim());
                    Category selectedCategory = categoryDropdown.getValue();


                    if (title.isEmpty()) {
                        throw new IllegalArgumentException("Title cannot be empty.");
                    }
                    if (selectedCategory == null) {
                        throw new IllegalArgumentException("You must select a category.");
                    }


                    categorys.add(selectedCategory);


                    return new Movie(title, imdbRating, personalRating, 0);
                } catch (NumberFormatException e) {
                    System.out.println("IMDB and Personal Rating must be valid numbers.");
                } catch (IllegalArgumentException e) {
                    System.out.println("Validation Error: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Unexpected Error: " + e.getMessage());
                }
            }
            return null;
        });


        Optional<Movie> result = dialog.showAndWait();
        result.ifPresent(movie -> {
            try {
                Movie createdMovie = movieModel.addMovie(movie, categorys);
                movieTableView.refresh();
                System.out.println("Movie added successfully: " + createdMovie);
                searchMovie(txtMovieSearch.getText());
            } catch (Exception e) {
                System.out.println("Failed to add movie: " + e.getMessage());
            }
        });
    }


    @FXML
    private void RemoveMovie(ActionEvent actionEvent) {
        Movie selectedMovie = movieTableView.getSelectionModel().getSelectedItem();

        if (selectedMovie != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to remove this movie?\nMovie: " + selectedMovie.getTitle(),
                    ButtonType.OK, ButtonType.CANCEL);

            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    movieModel.removeMovie(selectedMovie);
                    movieTableView.refresh();
                    searchMovie(txtMovieSearch.getText());
                } catch (Exception e) {
                    System.err.println("Error removing movie: " + e.getMessage());
                }
            }
        }
    }
    @FXML
    private void AddCategory(ActionEvent actionevent){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Category");
        dialog.setHeaderText("Enter a new category name:");
        dialog.setContentText("Category:");

        // Show the dialog and get user input
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(categoryName -> {
            if (!categoryName.trim().isEmpty()) {
                try {
                    CategoryModel categoryModel = new CategoryModel();
                    categoryModel.addCategory(categoryName.trim());

                    // Refresh the ListView
                    categoryListView.setItems(categoryModel.getCategories());
                } catch (Exception e) {
                    e.printStackTrace(); // Replace with proper error handling/logging
                }
            }
        });
    }

    @FXML
    private void RemoveCategory (ActionEvent actionEvent) {
        Category selectedCategory = categoryListView.getSelectionModel().getSelectedItem();

        if (selectedCategory != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Remove Category");
            confirmationAlert.setHeaderText("Are you sure you want to delete this category?");
            confirmationAlert.setContentText(selectedCategory.getCategoryName());

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {

                    CategoryModel categoryModel = new CategoryModel();
                    categoryModel.removeCategory(selectedCategory);


                    categoryListView.setItems(categoryModel.getCategories());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }
    }

}





