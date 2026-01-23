package Command;



public class DeleteMovieCommand implements Command {

    @Override
    public void execute() throws Exception {

    }

    @Override
    public void undo() throws Exception {
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);

    }
}