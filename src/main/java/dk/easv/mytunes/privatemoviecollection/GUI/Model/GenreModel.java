package dk.easv.mytunes.privatemoviecollection.GUI.Model;

import dk.easv.mytunes.privatemoviecollection.BE.Genre;
import dk.easv.mytunes.privatemoviecollection.DAO.GenreDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class GenreModel {
    private GenreDAO genreDAO;

    public GenreModel() {
        this.genreDAO = new GenreDAO();
    }

    public ObservableList<Genre> getGenres() throws Exception {
        List<Genre> genreList = genreDAO.getAllGenres();
        return FXCollections.observableArrayList(genreList);
    }
}
