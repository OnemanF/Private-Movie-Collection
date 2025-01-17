package dk.easv.mytunes.privatemoviecollection.BE;

public class Genre {
    private int genreID;
    private String genreName;

    public Genre(int genreID, String genreName) {
        this.genreID = genreID;
        this.genreName = genreName;
    }

    public Genre(String genreName) {
        this.genreName = genreName;
    }

    public int getGenreID() {
        return genreID;
    }

    public String getGenreName() {
        return genreName;
    }

    @Override
    public String toString() {
        return genreName;
    }
}
