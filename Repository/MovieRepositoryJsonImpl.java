package Repository;
import pojo.Movie;

import java.util.Collections;
import java.util.List;

/*
Questa classe si occupa di salvare e leggere i film fisicamente sul computer utilizzando un file in formato JSON. 
Una volta implementata, trasformerà la lista dei film in testo leggibile per memorizzarla sul disco, 
assicurandosi che i dati non vadano persi quando si chiude il programma. 

 */
public class MovieRepositoryJsonImpl implements MovieRepository {


    @Override
    public List<Movie> findAll() {
        return Collections.emptyList();
    }

    @Override
    public void saveAll(List<Movie> moviesListCache) {
       
    }
}