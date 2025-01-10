package dk.easv.mytunes.privatemoviecollection.BLL;

import dk.easv.mytunes.privatemoviecollection.BE.Movie;

import java.util.ArrayList;
import java.util.List;

public class Search {


    public List<Movie> search(List<Movie> searchBase, String query) {
        if (query == null || query.trim().isEmpty()) {
            return searchBase;
        }

        List<Movie> searchResult = new ArrayList<>();

        for (Movie movie : searchBase) {
            if (compareToMovieTitle(query, movie) ||
                    compareToMovieGenre(query, movie) ||
                    compareToMovieIMBDRating(query, movie) ||
                    compareToMoviePersonalRating(query, movie)) {
                searchResult.add(movie);
            }
        }

        return searchResult;
    }

    public boolean compareToMovieGenre(String query, Movie movie) {
        String genre = movie.getGenre();
        if (genre == null) {
            return false;
        }
        return genre.toLowerCase().contains(query.toLowerCase());
    }

    private boolean compareToMovieTitle(String query, Movie movie) {
        return movie.getTitle().toLowerCase().contains(query.toLowerCase());
    }

    private boolean compareToMovieIMBDRating(String query, Movie movie) {
        try {
            int queryRating = Integer.parseInt(query);
            return movie.getIMBDRating() == queryRating;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean compareToMoviePersonalRating(String query, Movie movie) {
        try {
            int queryRating = Integer.parseInt(query);
            return movie.getPersonalRating() == queryRating;
        } catch (NumberFormatException e) {
            return false;
        }

    }
}
