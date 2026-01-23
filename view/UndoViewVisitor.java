package view;

import Command.*;

/*
 * Implementazione del Visitor dedicata alla gestione dell'interfaccia dopo un Undo.
 * Si occupa di ripristinare il focus visivo sulla tabella quando un comando 
 * viene annullato, selezionando l'elemento originale o ripristinato.
 */
public class UndoViewVisitor implements CommandVisitor {


    @Override
    public void visit(AddMovieCommand cmd) {

    }

    @Override
    public void visit(EditMovieCommand cmd) {
  
    }

    @Override
    public void visit(DeleteMovieCommand cmd) {

    }
}