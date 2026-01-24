package Command;


import DTO.MovieDTO;
import pojo.Movie;
import service.MovieService;

/*
Questa classe serve a gestire l'aggiunta di un nuovo film alla collezione, 
tenendo traccia dell'oggetto creato per poterlo rimuovere in caso di annullamento. 
Una volta implementata, permetterà al sistema di inserire il film tramite il Service e di "tornare indietro" 
eliminandolo automaticamente se l'utente preme il tasto Undo.


*/

public class AddMovieCommand implements Command {

    private final MovieService movieService;
    private final MovieDTO movieDTO;
    
    // Riferimento al film creato, necessario per la procedura di undo
    private Movie createdMovie;

    public AddMovieCommand(MovieService movieService, MovieDTO movieDTO) {
        this.movieService = movieService;
        this.movieDTO = movieDTO;
    }

    @Override
    public void execute() throws Exception {
        // Esegue l'aggiunta e salva l'oggetto risultante
        this.createdMovie = this.movieService.addMovie(this.movieDTO);
    }

    @Override
    public void undo() throws Exception {
        // Verifichiamo che l'operazione di creazione sia avvenuta con successo
        if (this.createdMovie == null) {
            throw new Exception("Errore rollback: l'istanza del film non è presente.");
        }

        // Rimozione del film utilizzando l'identificativo memorizzato
        this.movieService.deleteMovie(this.createdMovie.getId());
    }

    @Override
    public void accept(CommandVisitor visitor) {
        // Supporto per il double dispatch
        visitor.visit(this);
    }

    public Movie getCreatedMovie() {
        return this.createdMovie;
    }
}