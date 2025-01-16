package dk.easv.mytunes.privatemoviecollection.DAO;

import dk.easv.mytunes.privatemoviecollection.BE.Category;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ICategoryDataAccess {

    void deleteCategory(Category category) throws Exception;

    List<Category> getCategories() throws IOException;
    void addCategory(String categoryName) throws SQLException;
}
