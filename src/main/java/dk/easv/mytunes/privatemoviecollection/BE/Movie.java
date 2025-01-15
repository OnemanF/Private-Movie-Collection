package dk.easv.mytunes.privatemoviecollection.BE;

public class Movie {
        private int MovieID;
        private String title;
        private String genre;
        private int IMBDRating;
        private int personalRating;
        private String lastView;
        private String filePath;

    public Movie(int MovieID, String title, int IMBDRating, int personalRating, String lastView) {
        this.MovieID = MovieID;
        this.title = title;
        this.IMBDRating = IMBDRating;
        this.personalRating = personalRating;
        this.lastView = lastView;
    }

    public Movie(String title, int IMBDRating, int personalRating, String lastView) {
        this.title = title;
        this.IMBDRating = IMBDRating;
        this.personalRating = personalRating;
        this.lastView = lastView;
    }

    public Movie(int MovieID, String title, int IMBDRating, int personalRating, String lastView, String genre, String filePath) {
        this.MovieID = MovieID;
        this.title = title;
        this.genre = genre;
        this.IMBDRating = IMBDRating;
        this.personalRating = personalRating;
        this.lastView = lastView;
        this.filePath = filePath;
    }

    public Movie(String title, String genre, int IMBDRating, int personalRating, String lastView, String filePath) {
        this.title = title;
        this.genre = genre;
        this.IMBDRating = IMBDRating;
        this.personalRating = personalRating;
        this.lastView = lastView;
        this.filePath = filePath;
    }


    public int getMovieID() {
        return MovieID;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getIMBDRating() {
        return IMBDRating;
    }

    public int getPersonalRating() {
        return personalRating;
    }

    public String getLastView() {
        return lastView;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public String toString() {
        return this.getTitle() + " " + this.getGenre() + " " + this.getIMBDRating() + " " + this.getPersonalRating() + " " + this.getLastView();
    }


}
