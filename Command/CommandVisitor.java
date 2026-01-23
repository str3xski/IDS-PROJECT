package Command;


public interface CommandVisitor {
    void visit(AddMovieCommand cmd);
    void visit(EditMovieCommand cmd);
    void visit(DeleteMovieCommand cmd);
}
