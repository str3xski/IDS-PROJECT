package Command;
import pojo.Movie;
import service.MovieService;



public class DeleteMovieCommand implements Command {

    private final MovieService movieService;

    private final Movie movieToDelete;

    public DeleteMovieCommand(MovieService movieService, Movie movieToDelete) {
        this.movieService = movieService;
        this.movieToDelete = movieToDelete;
    }

    @Override
    public void execute() throws Exception {
        movieService.deleteMovie(this.movieToDelete.getId());
    }

    @Override
    public void undo() throws Exception {
        movieService.addMovieObject(this.movieToDelete);
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    public Movie getMovieToDelete() {
        return movieToDelete;
    }
}