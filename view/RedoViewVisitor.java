package view;
import Command.*;

/*

Questa classe implementa il Pattern Visitor per gestire l'aggiornamento dell'interfaccia 
grafica specificamente durante le operazioni di "Ripristina" (Redo). Il suo compito è quello di "visitare" 
i diversi tipi di comandi che sono stati appena rieseguiti e decidere come la vista debba reagire visivamente.

*/



public class RedoViewVisitor implements CommandVisitor {

    private final MovieView view;

    public RedoViewVisitor(MovieView view) {
        this.view = view;
    }

    @Override
    public void visit(AddMovieCommand cmd) {

        view.selectRowById(cmd.getCreatedMovie().getId());
    }

    @Override
    public void visit(EditMovieCommand cmd) {

        view.selectRowById(cmd.getUpdatedMovie().getId());
    }

    @Override
    public void visit(DeleteMovieCommand cmd) {

    }
}

