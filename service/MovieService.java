package service;

import DTO.MovieDTO;
import pojo.Category;
import pojo.Movie;
import pojo.Status;
import java.util.List;

/*
Questa interfaccia definisce tutte le operazioni logiche che si possono fare sulla collezione dei film, 
come aggiungerli, modificarli o cercarli.
 */
public interface MovieService {

    // Recupera la collezione completa dei film elaborati
    List<Movie> getAllMovies();

    // Operazioni di persistenza e modifica
    Movie addMovie(MovieDTO dati) throws Exception;
    Movie editMovie(String idFilm, MovieDTO dati) throws Exception;
    void deleteMovie(String idFilm);
    void updateMovie(Movie film);
    void addMovieObject(Movie film);

    // Gestione dell'ordinamento (Strategy Pattern)
    void setSortStrategy(SortStrategyType tipoStrategia);
    void setSortDirection(SortDirection direzione);

    // Criteri di filtraggio della vista
    void setFilterCategory(Category cat);
    void setFilterStatus(Status stat);
    void setFilterRating(Integer voto);

    // Gestione dello stato della ricerca e reset
    void setSearchQuery(String testo);
    void resetFiltersAndSort();
}