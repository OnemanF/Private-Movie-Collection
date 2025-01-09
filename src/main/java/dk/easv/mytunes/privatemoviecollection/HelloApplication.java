package dk.easv.mytunes.privatemoviecollection;

import dk.easv.mytunes.privatemoviecollection.BE.CatMovie;
import dk.easv.mytunes.privatemoviecollection.BE.Category;
import dk.easv.mytunes.privatemoviecollection.BE.Movie;
import dk.easv.mytunes.privatemoviecollection.DAO.CatMovieDAO_DB;
import dk.easv.mytunes.privatemoviecollection.DAO.CategoryDAO_DB;
import dk.easv.mytunes.privatemoviecollection.DAO.MovieDAO_DB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        // testing
        /*
        CategoryDAO_DB categoryDAO = new CategoryDAO_DB();
        CatMovieDAO_DB catMovieDAO = new CatMovieDAO_DB();
        MovieDAO_DB movieDAO = new MovieDAO_DB();

        Category category = new Category("category test", 1);
        Category category1 = categoryDAO.createCategory(category);

        Movie movie = movieDAO.createMovie(new Movie("title", 5, 2, 0));
        CatMovie catMovie = catMovieDAO.addMovieToCategory(movie, category1);
        System.out.println(catMovie.toString());
        // dao.deleteCategory(new Category(4, "d",1));

         */
    }

    public static void main(String[] args) {launch();
    }
}