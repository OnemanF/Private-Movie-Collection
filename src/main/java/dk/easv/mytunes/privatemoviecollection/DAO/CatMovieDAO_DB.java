package dk.easv.mytunes.privatemoviecollection.DAO;

import dk.easv.mytunes.privatemoviecollection.BE.CatMovie;
import dk.easv.mytunes.privatemoviecollection.BE.Category;
import dk.easv.mytunes.privatemoviecollection.BE.Movie;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CatMovieDAO_DB implements ICatMovieDataAccess {
    private DBConnector dbConnector;

    public CatMovieDAO_DB() throws IOException {
        dbConnector = new DBConnector();
    }

    @Override
    public CatMovie addMovieToCategory(Movie movie, Category category) throws Exception {
        String sql_insert = "INSERT INTO CatMovie (CategoryID, MovieID) VALUES (?, ?)";

        try(Connection conn = dbConnector.getConnection(); PreparedStatement ps_insert = conn.prepareStatement(sql_insert, Statement.RETURN_GENERATED_KEYS)){
            ps_insert.setInt(1, category.getCategoryID());
            ps_insert.setInt(2, movie.getMovieID());
            ps_insert.executeUpdate();

            ResultSet rs = ps_insert.getGeneratedKeys();
            rs.next();
            int ID = rs.getInt(1);
            return new CatMovie(ID, category.getCategoryID(), movie.getMovieID());
        }

        catch(Exception e){
            throw new Exception("fejl", e);
        }
    }
}
