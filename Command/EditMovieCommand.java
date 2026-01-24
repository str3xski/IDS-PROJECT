package Command;
import DTO.MovieDTO;
import pojo.Movie;
import service.MovieService;

/*

Questa classe serve a gestire la modifica dei dati di un film, 
memorizzando sia le nuove informazioni che quelle vecchie. 
Una volta implementata, permetterà al sistema di aggiornare i dettagli di un film nell'archivio e, 
se necessario, di ripristinare istantaneamente lo stato originale in caso di annullamento.

*/



public class EditMovieCommand implements Command {

    private final MovieService movieService;
    private final MovieDTO movieDTO;

    private final Movie originalMovie;
    private Movie updatedMovie;

    public EditMovieCommand(MovieService movieService, Movie originalMovie, MovieDTO movieDTO) {
        this.movieService = movieService;
        this.originalMovie = originalMovie;
        this.movieDTO = movieDTO;
    }

    @Override
    public void execute() throws Exception {
        this.updatedMovie = movieService.editMovie(originalMovie.getId(), movieDTO);
    }

    @Override
    public void undo() throws Exception {
        movieService.updateMovie(this.originalMovie);
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    public Movie getUpdatedMovie() {
        return updatedMovie;
    }

    public Movie getOriginalMovie() {
        return originalMovie;
    }
}