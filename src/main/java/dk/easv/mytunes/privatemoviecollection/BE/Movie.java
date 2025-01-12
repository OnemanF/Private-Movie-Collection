package dk.easv.mytunes.privatemoviecollection.BE;

public class Movie {
        private int MovieID;
        private String title;
        private String genre;
        private int IMBDRating;
        private int personalRating;
        private int lastView;

    public Movie(int MovieID, String title, int IMBDRating, int personalRating, int lastView) {
        this.MovieID = MovieID;
        this.title = title;
        this.IMBDRating = IMBDRating;
        this.personalRating = personalRating;
        this.lastView = lastView;
    }

    public Movie(String title, int IMBDRating, int personalRating, int lastView) {
        this.title = title;
        this.IMBDRating = IMBDRating;
        this.personalRating = personalRating;
        this.lastView = lastView;
    }

    public Movie(int MovieID, String title, int IMBDRating, int personalRating, int lastView, String genre) {
        this.MovieID = MovieID;
        this.title = title;
        this.genre = genre;
        this.IMBDRating = IMBDRating;
        this.personalRating = personalRating;
        this.lastView = lastView;
    }


    public int getMovieID() {
        return MovieID;
    }

    public void setMovieID(int movieID) {
        MovieID = movieID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public void setIMBDRating(int IMBDRating) {
        this.IMBDRating = IMBDRating;
    }

    public int getPersonalRating() {
        return personalRating;
    }

    public void setPersonalRating(int personalRating) {
        this.personalRating = personalRating;
    }

    public int getLastView() {
        return lastView;
    }

    public void setLastView(int lastView) {
        this.lastView = lastView;
    }

    @Override
    public String toString() {
        return this.getTitle() + " " + this.getGenre() + " " + this.getIMBDRating() + " " + this.getPersonalRating() + " " + this.getLastView();
    }


}
