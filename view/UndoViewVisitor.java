package view;

import Command.*;

/*
 * Implementazione del Visitor dedicata alla gestione dell'interfaccia dopo un Undo.
 * Si occupa di ripristinare il focus visivo sulla tabella quando un comando 
 * viene annullato, selezionando l'elemento originale o ripristinato.
 */


public class UndoViewVisitor implements CommandVisitor {

    private final MovieView movieView;

    public UndoViewVisitor(MovieView view) {
        this.movieView = view;
    }

    @Override
    public void visit(AddMovieCommand cmd) {
        // Dopo l'annullamento di un inserimento, il film sparisce: nessuna selezione.
    }

    @Override
    public void visit(EditMovieCommand cmd) {
        /* * Annullando una modifica, torniamo allo stato precedente.
         * Selezioniamo il film originale per mostrare il ripristino dei dati.
         */
        this.movieView.selectRowById(cmd.getOriginalMovie().getId());
    }

    @Override
    public void visit(DeleteMovieCommand cmd) {
        /* * Annullando una cancellazione, il film ricompare in tabella.
         * Lo selezioniamo immediatamente per dare feedback all'utente.
         */
        this.movieView.selectRowById(cmd.getMovieToDelete().getId());
    }
}