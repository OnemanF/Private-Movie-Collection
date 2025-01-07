package dk.easv.mytunes.privatemoviecollection.DAO;

import dk.easv.mytunes.privatemoviecollection.BE.Category;

public interface ICategoryDataAccess {
    Category createCategory(Category category) throws Exception;
    void deleteCategory(Category category) throws Exception;
}
