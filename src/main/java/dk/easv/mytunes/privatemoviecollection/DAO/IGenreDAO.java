package dk.easv.mytunes.privatemoviecollection.DAO;

import dk.easv.mytunes.privatemoviecollection.BE.Genre;

import java.util.List;

public interface IGenreDAO {
    List<Genre> getAllGenres() throws Exception;
}
