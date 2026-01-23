package service;

import DTO.MovieDTO;
import pojo.*;

import java.util.Collections;
import java.util.List;


/*
Questa classe gestisce la logica centrale dell'applicazione, 
filtrando e ordinando i film in tempo reale e salvando automaticamente ogni modifica sul file JSON
 */
public class MovieServiceImpl implements MovieService, Subject {
    


    @Override
    public List<Movie> getAllMovies() {
            return Collections.emptyList();
    }

    @Override
    public Movie addMovie(MovieDTO dati) throws Exception {
        return new Movie();
        
    }

    @Override
    public Movie editMovie(String id, MovieDTO dati) throws Exception {
       return new Movie();
    }

    @Override
    public void updateMovie(Movie film) {
        
    }

    @Override
    public void deleteMovie(String id) {
      
    }

    @Override
    public void addMovieObject(Movie film) {
    
    }

    // --- Gestione Observer ---

    @Override
    public void addObserver(Observer o) {
       
    }

    @Override
    public void removeObserver(Observer o) {

    }

    @Override
    public void notifyObservers() {

    }


    // --- Filtri e Ordinamento ---

    @Override
    public void setSortStrategy(SortStrategyType tipo) {

    }

    @Override
    public void setSortDirection(SortDirection dir) {
 
    }

    @Override
    public void setFilterCategory(Category c) {

    }

    @Override
    public void setFilterStatus(Status s) {
      
    }

    @Override
    public void setFilterRating(Integer r) {

    }

    @Override
    public void setSearchQuery(String q) {
     
    }

    @Override
    public void resetFiltersAndSort() {

    }
}