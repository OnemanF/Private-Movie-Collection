package dk.easv.mytunes.privatemoviecollection.BLL;

import dk.easv.mytunes.privatemoviecollection.BE.Genre;
import dk.easv.mytunes.privatemoviecollection.DAO.GenreDAO;
import dk.easv.mytunes.privatemoviecollection.DAO.IGenreDAO;

import java.util.List;

public class GenreManager {

    private final IGenreDAO genreDAO;

    public GenreManager() {
        this.genreDAO = new GenreDAO();
    }

    public List<Genre> getGenres() throws Exception {
        return genreDAO.getAllGenres();
    }

}
