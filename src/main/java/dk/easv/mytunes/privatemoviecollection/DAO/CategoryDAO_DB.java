package dk.easv.mytunes.privatemoviecollection.DAO;

import dk.easv.mytunes.privatemoviecollection.BE.Category;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CategoryDAO_DB implements ICategoryDataAccess {
    private DBConnector dbConnector;

    public CategoryDAO_DB() throws IOException {
        dbConnector = new DBConnector();
    }

    @Override
    public Category createCategory(Category category) throws Exception {
        String sql_insert = "INSERT INTO Category (CategoryName) VALUES (?)";

        try(Connection conn = dbConnector.getConnection(); PreparedStatement ps_insert = conn.prepareStatement(sql_insert, Statement.RETURN_GENERATED_KEYS)){
            ps_insert.setString(1, category.getCategoryName());

            ps_insert.executeUpdate();

            ResultSet rs = ps_insert.getGeneratedKeys();
            rs.next();
            int categoryID = rs.getInt(1);
            return new Category(categoryID, category.getCategoryName(), category.getMovies());
        }

        catch(Exception e){
            throw new Exception("Unable to create the category " + category.getCategoryName().trim(), e);
        }
    }
}
