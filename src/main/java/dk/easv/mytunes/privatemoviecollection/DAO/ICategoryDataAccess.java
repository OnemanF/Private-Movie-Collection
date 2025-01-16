package dk.easv.mytunes.privatemoviecollection.DAO;

import dk.easv.mytunes.privatemoviecollection.BE.Category;

import java.io.IOException;
import java.util.List;

public interface ICategoryDataAccess {

    Category createCategory(Category category) throws Exception;

    void deleteCategory(Category category) throws Exception;

    List<Category> getCategories() throws IOException;
}
