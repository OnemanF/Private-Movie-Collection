package dk.easv.mytunes.privatemoviecollection.DAO;

import dk.easv.mytunes.privatemoviecollection.BE.Category;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO_DB implements ICategoryDataAccess {
    private DBConnector dbConnector;
    public CategoryDAO_DB() throws IOException {
        dbConnector = new DBConnector();
    }

    @Override
    public List<Category> getCategories() throws IOException {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM Category";
        try (Connection conn = new DBConnector().getConnection()) {
            try (PreparedStatement ps_select = conn.prepareStatement(sql)) {
                ResultSet rs = ps_select.executeQuery();
                while (rs.next()) {
                    categories.add(new Category(
                            rs.getInt("CategoryID"),
                            rs.getString("CategoryName"),
                            rs.getInt("movies")
                    ));
                }
            }

            return categories;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Category createCategory(Category category) throws Exception {
        String sql_insert = "INSERT INTO Category (CategoryName, movies) VALUES (?, ?)";

        try(Connection conn = dbConnector.getConnection(); PreparedStatement ps_insert = conn.prepareStatement(sql_insert, Statement.RETURN_GENERATED_KEYS)){
            ps_insert.setString(1, category.getCategoryName());
            ps_insert.setInt(2, category.getMovies());
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

    public void addCategory(String categoryName) throws SQLException {
        String sql = "INSERT INTO Category (CategoryName) VALUES (?)";

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, categoryName);
            preparedStatement.executeUpdate();
        }

}

    @Override
    public void deleteCategory(Category category) throws Exception {
        String delete_categoriesSQL = "DELETE FROM Category WHERE CategoryID = ?";
        String delete_connectionSQL = "DELETE FROM CatMovie WHERE CategoryID = ?";

        try (Connection conn = dbConnector.getConnection()) {
            conn.setAutoCommit(false);
            try (
                    PreparedStatement ps_delete_connection = conn.prepareStatement(delete_connectionSQL);
                    PreparedStatement ps_delete_category = conn.prepareStatement(delete_categoriesSQL)
            ) {
                ps_delete_connection.setInt(1, category.getCategoryID());
                ps_delete_connection.executeUpdate();


                ps_delete_category.setInt(1, category.getCategoryID());
                ps_delete_category.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new Exception("Unable to delete the category " + category.getCategoryName().trim() + ". Transaction rolled back.", e);
            }
        }
    }
}
