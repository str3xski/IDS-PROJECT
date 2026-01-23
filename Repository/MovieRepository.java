package Repository;
import pojo.Movie;

/* 

Questa interfaccia stabilisce le regole per il salvataggio dei dati, 
definendo come il sistema deve leggere e scrivere la lista dei film su un supporto esterno. 

*/


import java.util.List;

public interface MovieRepository {
    List<Movie> findAll();
    void saveAll(List<Movie> moviesListCache);
}
