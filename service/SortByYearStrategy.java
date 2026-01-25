package service;

import pojo.Movie;
import java.util.List;
import java.util.Comparator;


/*

Questa classe ha il compito di ordinare i film cronologicamente in base all'anno di uscita, 
permettendo di consultare la collezione dai più vecchi ai più recenti.


*/

public class SortByYearStrategy implements SortingStrategy {
    @Override
    public void sort(List<Movie> movies, SortDirection direction) {
        Comparator<Movie> comparator = Comparator.comparingInt(Movie::getYear);

        if (direction == SortDirection.DESCENDING)
            comparator = comparator.reversed();

        movies.sort(comparator);
    }
}
