package service;

import pojo.Movie;
import java.util.List;
import java.util.Comparator;


/*Questa classe si occupa di ordinare alfabeticamente i film in base al titolo, 
ignorando la differenza tra maiuscole e minuscole. */




public class SortByTitleStrategy implements SortingStrategy {
    @Override
    public void sort(List<Movie> movies, SortDirection direction) {
        Comparator<Movie> comparator = Comparator.comparing(Movie::getTitle, String.CASE_INSENSITIVE_ORDER);

        if (direction == SortDirection.DESCENDING)
            comparator = comparator.reversed();

        movies.sort(comparator);
    }
}
