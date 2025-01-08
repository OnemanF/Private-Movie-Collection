package dk.easv.mytunes.privatemoviecollection.BLL;

import dk.easv.mytunes.privatemoviecollection.BE.Movie;

import java.util.ArrayList;
import java.util.List;

public class Search {

    public List<Movie> search(List<Movie> searchBase, String query) {
        List<Movie> searchResult = new ArrayList<>();

        for (Movie movie : searchBase) {
            if (compareToMovieTitle(query, movie) || compareToMovieGenre(query, movie) || compareToMovieIMBDRating(query, movie) || compareToMoviePersonalRating(query, movie)) {
                searchResult.add(movie);
            }
        }

        return searchResult;
    }

    private boolean compareToMovieGenre(String query, Movie movie) {
        return movie.getGenre().toLowerCase().contains(query.toLowerCase());
    }

    private boolean compareToMovieTitle(String query, Movie movie) {
        return movie.getTitle().toLowerCase().contains(query.toLowerCase());
    }

    private boolean compareToMovieIMBDRating(String query, Movie movie) {
        try {
            int queryRating = Integer.parseInt(query); // Convert query to integer
            return movie.getIMBDRating() == queryRating;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean compareToMoviePersonalRating(String query, Movie movie) {
        try {
            int queryRating = Integer.parseInt(query); // Convert query to integer
            return movie.getPersonalRating() == queryRating;
        } catch (NumberFormatException e) {
            return false;
        }

    }
}
