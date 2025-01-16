package dk.easv.mytunes.privatemoviecollection.BE;

public class Genre {
    private int genreID;
    private String genreName;

    // Constructor with all fields
    public Genre(int genreID, String genreName) {
        this.genreID = genreID;
        this.genreName = genreName;
    }

    // Constructor without ID (for new genres before saving to the database)
    public Genre(String genreName) {
        this.genreName = genreName;
    }

    // Getters and Setters
    public int getGenreID() {
        return genreID;
    }

    public void setGenreID(int genreID) {
        this.genreID = genreID;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    // toString method for UI representation
    @Override
    public String toString() {
        return genreName;
    }
}
