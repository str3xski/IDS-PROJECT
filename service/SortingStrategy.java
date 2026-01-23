package service;
import pojo.Movie;
import java.util.List;

/*

Questa interfaccia definisce il contratto comune per tutti gli algoritmi di ordinamento della collezione, 
seguendo il Pattern Strategy
*/


public interface SortingStrategy {
    void sort(List<Movie> movies, SortDirection direction);
}