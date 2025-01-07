package dk.easv.mytunes.privatemoviecollection.DAO;

import dk.easv.mytunes.privatemoviecollection.BE.CatMovie;
import dk.easv.mytunes.privatemoviecollection.BE.Category;
import dk.easv.mytunes.privatemoviecollection.BE.Movie;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class MovieDAO_DB implements IMovieDataAccess {
    private DBConnector dbConnector;

    public MovieDAO_DB() throws IOException {
        dbConnector = new DBConnector();
    }

    @Override
    public Movie createMovie(Movie movie) throws Exception {
        String sql_insert = "INSERT INTO Movie (title, IMBDRating, personalRating) VALUES (?, ?, ?)";

        try(Connection conn = dbConnector.getConnection(); PreparedStatement ps_insert = conn.prepareStatement(sql_insert, Statement.RETURN_GENERATED_KEYS)){
            ps_insert.setString(1, movie.getTitle());
            ps_insert.setInt(2, movie.getIMBDRating());
            ps_insert.setInt(3, movie.getPersonalRating());
            ps_insert.executeUpdate();

            ResultSet rs = ps_insert.getGeneratedKeys();
            rs.next();
            int ID = rs.getInt(1);
            return new Movie(ID, movie.getTitle(), movie.getIMBDRating(), movie.getPersonalRating(), movie.getLastView());
        }

        catch(Exception e){
            throw new Exception("Unable to create the movie " + movie.getTitle().trim(), e);
        }
    }

    @Override
    public void deleteMovie(Movie movie) {

    }
}
