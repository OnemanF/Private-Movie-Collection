package dk.easv.mytunes.privatemoviecollection.GUI.Controller;

import dk.easv.mytunes.privatemoviecollection.BE.Category;
import dk.easv.mytunes.privatemoviecollection.BE.Movie;
import dk.easv.mytunes.privatemoviecollection.GUI.Model.CategoryModel;
import dk.easv.mytunes.privatemoviecollection.GUI.Model.MovieModel;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;


public class MainController implements Initializable {
    @FXML
    private TableView<Movie> movieTableView;

    @FXML
    private ListView<Category> categoryListView;

    @FXML
    private Button addMovieButton;

    @FXML
    private TableColumn<Movie, String> colTitle, colGenre, colIMBDRating, colPersonalRating;

    @FXML
    private TextField txtMovieSearch;

    @FXML
    private void AddMovie() {
        showAddMovieDialog();
    }

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

            for (Movie movie : movieModel.getMovies()) {
                LocalDate date = LocalDate.parse(movie.getLastView());
                if (ChronoUnit.DAYS.between(date, LocalDate.now()) > (365 * 2)) {
                    if (movie.getPersonalRating() <= 6) {
                        warnUser(movie);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void warnUser(Movie movie) {
        System.out.println(movie.getTitle() + " 2 years since last view and personal rating <= 6");
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(movie.getTitle() + " 2 years since last view");
        alert.showAndWait();
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

        categoryListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
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

        movieTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Movie selectedMovie = movieTableView.getSelectionModel().getSelectedItem();
                if (selectedMovie != null) {
                    try {
                        playMovie(selectedMovie);
                    } catch (Exception e) {
                        System.err.println("Error playing movie: " + e.getMessage());
                    }
                }
            }
        });

        movieTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Selected Movie: " + newValue);
            }
        });
    }

    public void searchMovie(String newValue) throws Exception {
        List<Movie> movies = movieModel.searchMovies(newValue, selectedCategory);
        movieTableView.setItems((ObservableList<Movie>) movies);
        movieTableView.refresh();
    }

    public void playMovie(Movie movie) throws Exception {
        if (movie == null) {
            System.err.println("Error: Attempted to play a null movie.");
            return;
        }
        String moviePath = movie.getFilePath();
        File file = new File(moviePath);

        if (!file.exists()) {
            System.err.println("Error: The file " + moviePath + " doesn't exist.");
            Alert alert = new Alert(Alert.AlertType.ERROR, "The file doesn't exist: " + moviePath, ButtonType.OK);
            alert.showAndWait();
            return;
        }

        movieModel.setLastPlayed(movie);
        Desktop.getDesktop().open(file);
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

        TextField genreField = new TextField();
        genreField.setPromptText("Genre");

        ListView<Category> categoryListView = new ListView<>();
        categoryListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        try {
            categoryListView.setItems(categoryModel.getCategories());
        } catch (Exception e) {
            displayError("Error loading categories: " + e.getMessage());
            return;
        }

        // Dynamic Label for Selected Categories
        Label selectedCategoriesLabel = new Label("Selected Categories: None");

        ObservableList<Category> selectedItems = categoryListView.getSelectionModel().getSelectedItems();
        selectedItems.addListener((ListChangeListener<Category>) change -> {
            StringBuilder selected = new StringBuilder();
            for (Category category : selectedItems) {
                selected.append(category.getCategoryName()).append(", ");
            }
            selectedCategoriesLabel.setText("Selected Categories: " + (selected.length() > 0 ? selected.substring(0, selected.length() - 2) : "None"));
        });


        TextField fileField = new TextField();
        fileField.setEditable(false);
        Button browseButton = new Button("Browse...");
        FileChooser fileChooser = new FileChooser();
        browseButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(dialog.getDialogPane().getScene().getWindow());
            if (selectedFile != null) {
                String absolutePath = selectedFile.getAbsolutePath();
                String relativePath = absolutePath.replace(System.getProperty("user.dir") + "\\", "");
                fileField.setText(relativePath);
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("IMDB Rating:"), 0, 1);
        grid.add(imdbRatingField, 1, 1);
        grid.add(new Label("Personal Rating:"), 0, 2);
        grid.add(personalRatingField, 1, 2);
        grid.add(new Label("Genre:"), 0, 3);
        grid.add(genreField, 1, 3);
        grid.add(new Label("Categories:"), 0, 4);
        grid.add(categoryListView, 1, 4);
        grid.add(selectedCategoriesLabel, 1, 5);
        grid.add(new Label("File:"), 0, 6);
        grid.add(fileField, 1, 6);
        grid.add(browseButton, 2, 6);

        dialog.getDialogPane().setContent(grid);

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                return handleDialogResult(titleField, imdbRatingField, personalRatingField, genreField, categoryListView, fileField);
            }
            return null;
        });

        Optional<Movie> result = dialog.showAndWait();
        result.ifPresent(movie -> handleMovieAddition(movie, categoryListView.getSelectionModel().getSelectedItems()));
    }

    private Movie handleDialogResult(TextField titleField, TextField imdbRatingField, TextField personalRatingField, TextField genreField, ListView<Category> categoryListView, TextField fileField) {
        try {
            String title = titleField.getText().trim();
            int imdbRating = Integer.parseInt(imdbRatingField.getText().trim());
            int personalRating = Integer.parseInt(personalRatingField.getText().trim());
            String genre = genreField.getText().trim();
            ObservableList<Category> selectedCategories = categoryListView.getSelectionModel().getSelectedItems();
            String lastViewed = java.time.LocalDate.now().toString();
            String filePath = fileField.getText().trim();

            validateInputs(title, imdbRating, personalRating, genre, selectedCategories, filePath);

            return new Movie(title, genre, imdbRating, personalRating, lastViewed, filePath);

        } catch (NumberFormatException e) {
            displayError("IMDB and Personal Rating must be valid numbers.");
        } catch (IllegalArgumentException e) {
            displayError("Validation Error: " + e.getMessage());
        }
        return null;
    }

    private void handleMovieAddition(Movie movie, ObservableList<Category> categories) {
        if (movie == null || categories == null || categories.isEmpty()) {
            return;
        }

        try {
            movieModel.addMovie(movie, categories);
            movieTableView.refresh();
            searchMovie(txtMovieSearch.getText());
            System.out.println("Movie added successfully: " + movie);
        } catch (Exception e) {
            displayError("Failed to add movie: " + e.getMessage());
        }
    }

    private void validateInputs(String title, int imdbRating, int personalRating, String genre, ObservableList<Category> selectedCategories, String filePath) {
        if (title.isEmpty()) throw new IllegalArgumentException("Title cannot be empty.");
        if (genre.isEmpty()) throw new IllegalArgumentException("Genre cannot be empty.");
        if (selectedCategories == null || selectedCategories.isEmpty()) throw new IllegalArgumentException("You must select at least one category.");
        if (filePath.isEmpty()) throw new IllegalArgumentException("You must select a file.");
        if (imdbRating < 0 || imdbRating > 10) throw new IllegalArgumentException("IMDB Rating must be between 0 and 10.");
        if (personalRating < 0 || personalRating > 10) throw new IllegalArgumentException("Personal Rating must be between 0 and 10.");
    }

    private void displayError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Validation Error");
        alert.setContentText(message);
        alert.showAndWait();
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
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR, "An error occurred while removing the movie.", ButtonType.OK);
                    errorAlert.showAndWait();
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

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(categoryName -> {
            if (!categoryName.trim().isEmpty()) {
                try {
                    CategoryModel categoryModel = new CategoryModel();
                    categoryModel.addCategory(categoryName.trim());
                    categoryListView.setItems(categoryModel.getCategories());
                } catch (Exception e) {
                    e.printStackTrace();
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