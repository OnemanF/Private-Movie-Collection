package dk.easv.mytunes.privatemoviecollection.BE;

public class CatMovie {
    private int ID;
    private int CategoryID;
    private int MovieID;

    public CatMovie(int ID, int CategoryID, int MovieID) {
        this.ID = ID;
        this.CategoryID = CategoryID;
        this.MovieID = MovieID;
    }

    public CatMovie(int CategoryID, int MovieID) {
        this.CategoryID = CategoryID;
        this.MovieID = MovieID;
    }
}
