package dk.easv.mytunes.privatemoviecollection.BE;

public class Category {
    private int CategoryID;
    private String CategoryName;
    private int movies;

    public Category(int CategoryID, String CategoryName, int movies) {
        this.CategoryID = CategoryID;
        this.CategoryName = CategoryName;
        this.movies = movies;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public int getMovies() {
        return movies;
    }

    public void setMovies(int movies) {
        this.movies = movies;
    }
}
