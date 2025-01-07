package dk.easv.mytunes.privatemoviecollection;

import dk.easv.mytunes.privatemoviecollection.BE.Category;
import dk.easv.mytunes.privatemoviecollection.DAO.CategoryDAO_DB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 730, 500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        // testing
        CategoryDAO_DB dao = new CategoryDAO_DB();
        dao.createCategory(new Category("category test", 1));
    }

    public static void main(String[] args) {
        launch();
    }
}