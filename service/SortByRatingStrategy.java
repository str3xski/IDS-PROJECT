package service;

import pojo.Movie;
import java.util.List;


/*

Questa classe implementa la logica per ordinare i film in base al punteggio, 
permettendo di visualizzare la collezione dai titoli più votati a quelli meno votati (o viceversa). 
Una volta implementata, servirà a riorganizzare la tabella ogni volta che l'utente seleziona il criterio "Punteggio", 
garantendo che l'ordine sia coerente con la direzione scelta.

*/

public class SortByRatingStrategy implements SortingStrategy {
    @Override
    public void sort(List<Movie> movies, SortDirection direction) {
      
    }
}
