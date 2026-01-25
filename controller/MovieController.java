package controller;
import Command.*;
import DTO.MovieDTO;
import pojo.*;
import service.*;
import java.util.List;

/*
 * Classe Controller che funge da intermediario tra l'interfaccia utente e il service.
 * Gestisce le richieste dell'utente coordinando l'esecuzione dei comandi,
 * la validazione dei dati e le operazioni di undo/redo.
 */
public class MovieController {

    private final MovieService movieService;
    private final CommandManager commandManager;

    public MovieController(MovieService movieService, CommandManager commandManager) {
        this.movieService = movieService;
        this.commandManager = commandManager;
    }

    public Movie addMovieRequest(MovieDTO dto) {
        try {
            // Controllo preliminare dei dati in ingresso
            checkRequiredFields(dto);

            AddMovieCommand addCmd = new AddMovieCommand(this.movieService, dto);
            this.commandManager.execute(addCmd);

            return addCmd.getCreatedMovie();
        } catch (Exception ex) {
            System.err.println("Errore inserimento film: " + ex.getMessage());
            return null;
        }
    }

    public Movie editMovieRequest(Movie movieToEdit, MovieDTO dto) {
        try {
            // Verifica integrità dati prima della modifica
            checkRequiredFields(dto);

            EditMovieCommand editCmd = new EditMovieCommand(this.movieService, movieToEdit, dto);
            this.commandManager.execute(editCmd);

            return editCmd.getUpdatedMovie();
        } catch (Exception ex) {
            System.err.println("Aggiornamento fallito: " + ex.getMessage());
            return null;
        }
    }

    public void deleteMovieRequest(Movie movieToDelete) throws Exception {
        // Creazione ed esecuzione del comando di rimozione
        Command deleteCmd = new DeleteMovieCommand(this.movieService, movieToDelete);
        this.commandManager.execute(deleteCmd);
    }

    private void checkRequiredFields(MovieDTO dto) throws Exception {
        // Validazione stringhe vuote
        if (dto.title.isEmpty() || dto.director.isEmpty() || dto.yearStr.isEmpty()) {
            throw new Exception("I campi Titolo, Regista e Anno sono obbligatori!");
        }

        try {
            // Controllo formato numerico dell'anno
            Integer.parseInt(dto.yearStr);
        } catch (NumberFormatException nfe) {
            throw new Exception("L'anno inserito non è un numero valido!");
        }
    }

    public List<Movie> getAllMovies() {
        return this.movieService.getAllMovies();
    }

    public void changeSortStrategy(SortStrategyType strategyType) {
        this.movieService.setSortStrategy(strategyType);
    }

    public void changeSortDirection(SortDirection direction) {
        this.movieService.setSortDirection(direction);
    }

    public void setFilterCategory(Category category) {
        this.movieService.setFilterCategory(category);
    }

    public void setFilterStatus(Status status) {
        this.movieService.setFilterStatus(status);
    }

    public void setFilterRating(Integer rating) {
        this.movieService.setFilterRating(rating);
    }

    public void resetFiltersAndSort() {
        this.movieService.resetFiltersAndSort();
    }

    public void searchMovies(String query) {
        this.movieService.setSearchQuery(query);
    }

    public Command undoRequest() throws Exception {
        return this.commandManager.undo();
    }

    public Command redoRequest() throws Exception {
        return this.commandManager.redo();
    }
}