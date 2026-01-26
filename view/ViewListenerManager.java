package view;

import Command.Command;
import Command.CommandVisitor;
import controller.MovieController;
import DTO.MovieDTO;
import pojo.Category;
import pojo.Movie;
import pojo.Status;
import service.SortDirection;
import service.SortStrategyType;
import javax.swing.*;
import java.awt.Point;

/*
Questa classe è il "cervello operativo" dell'interfaccia: il suo compito è collegare i pulsanti 
e i menu della finestra alle funzioni logiche del controller. Senza di essa, 
i componenti grafici sarebbero solo icone inerti; grazie ai suoi listener, ogni azione dell'utente (un clic, una ricerca o un tasto premuto) scatena una risposta precisa nel programma.
 */


public class ViewListenerManager {

    private final MovieView movieView;
    private final MovieController movieController;
    private final CommandVisitor visitorUndo;
    private final CommandVisitor visitorRedo;

    public ViewListenerManager(MovieView view, MovieController controller) {
        this.movieView = view;
        this.movieController = controller;
        this.visitorUndo = new UndoViewVisitor(view);
        this.visitorRedo = new RedoViewVisitor(view);
    }

    public void bindListeners() {
        attivaListenerForm();
        attivaListenerAzioni();
        attivaListenerFiltri();
        attivaListenerRicerca();
        attivaListenerUndoRedo();
    }

    private void attivaListenerForm() {
        movieView.getInputPanel().getSubmitButton().addActionListener(e -> {
            if (movieController == null) return;

            MovieDTO dati = new MovieDTO(
                    movieView.getInputPanel().getTitleText(),
                    movieView.getInputPanel().getDirectorText(),
                    movieView.getInputPanel().getYearText(),
                    movieView.getInputPanel().getSelectedCategory(),
                    movieView.getInputPanel().getSelectedStatus(),
                    movieView.getInputPanel().getSelectedRating()
            );

            FormState prossimoStato = movieView.getCurrentState().handleSubmit(movieView, dati);

            if (prossimoStato != movieView.getCurrentState()) {
                movieView.setCurrentState(prossimoStato);
                movieView.getCurrentState().enterState(movieView);
            }
        });

        movieView.getInputPanel().getCancelButton().addActionListener(e -> {
            FormState addState = new AddModeState();
            movieView.setCurrentState(addState);
            addState.enterState(movieView);
        });
    }

    private void attivaListenerAzioni() {
        movieView.getActionPanel().getEditButton().addActionListener(e -> {
            Movie film = prendiFilmSelezionato();
            if (film != null) vaiInModalitaEdit(film);
        });

        movieView.getMovieTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                if (me.getClickCount() == 2) {
                    Movie film = prendiFilmDaClick(me.getPoint());
                    if (film != null) vaiInModalitaEdit(film);
                }
            }
        });

        movieView.getActionPanel().getDeleteButton().addActionListener(e -> {
            Movie daCancellare = prendiFilmSelezionato();
            if (daCancellare == null) return;

            if (confermaRimozione()) {
                try {
                    movieController.deleteMovieRequest(daCancellare);
                } catch (Exception ex) {
                    mostraPopupErrore("Errore cancellamento", "Impossibile eliminare: " + ex.getMessage());
                }
            }
        });
    }

    private void attivaListenerUndoRedo() {
        movieView.getToolbarPanel().getUndoButton().addActionListener(e -> {
            if (movieController != null) {
                try {
                    Command cmd = movieController.undoRequest();
                    if (cmd != null) cmd.accept(visitorUndo);
                } catch (Exception ex) {
                    mostraPopupErrore("Errore Undo", "Annullamento fallito: " + ex.getMessage());
                }
            }
        });

        movieView.getToolbarPanel().getRedoButton().addActionListener(e -> {
            if (movieController != null) {
                try {
                    Command cmd = movieController.redoRequest();
                    if (cmd != null) cmd.accept(visitorRedo);
                } catch (Exception ex) {
                    mostraPopupErrore("Errore Redo", "Ripristino fallito: " + ex.getMessage());
                }
            }
        });
    }

    private void attivaListenerFiltri() {
        movieView.getToolbarPanel().getSortComboBox().addActionListener(e -> {
            if (movieController != null) {
                movieController.changeSortStrategy((SortStrategyType) movieView.getToolbarPanel().getSortComboBox().getSelectedItem());
            }
        });

        movieView.getToolbarPanel().getSortDirectionButton().addActionListener(e -> {
            if (movieController != null) {
                JToggleButton btn = movieView.getToolbarPanel().getSortDirectionButton();
                if (btn.isSelected()) {
                    btn.setText("Decrescente ⬇");
                    movieController.changeSortDirection(SortDirection.DESCENDING);
                } else {
                    btn.setText("Crescente ⬆");
                    movieController.changeSortDirection(SortDirection.ASCENDING);
                }
            }
        });

        movieView.getToolbarPanel().getCategoryFilterComboBox().addActionListener(e -> {
            if (movieController != null) movieController.setFilterCategory((Category) movieView.getToolbarPanel().getCategoryFilterComboBox().getSelectedItem());
        });
        movieView.getToolbarPanel().getStatusFilterComboBox().addActionListener(e -> {
            if (movieController != null) movieController.setFilterStatus((Status) movieView.getToolbarPanel().getStatusFilterComboBox().getSelectedItem());
        });
        movieView.getToolbarPanel().getRatingFilterComboBox().addActionListener(e -> {
            if (movieController != null) movieController.setFilterRating((Integer) movieView.getToolbarPanel().getRatingFilterComboBox().getSelectedItem());
        });

        movieView.getToolbarPanel().getResetButton().addActionListener(e -> {
            if (movieController != null) {
                movieController.resetFiltersAndSort();
                movieView.getToolbarPanel().resetFilterControls();
                movieView.getToolbarPanel().resetSortControls();

                FormState resetState = new AddModeState();
                movieView.setCurrentState(resetState);
                resetState.enterState(movieView);
            }
        });
    }

    private void attivaListenerRicerca() {
        Runnable azioneCerca = () -> {
            if (movieController != null) movieController.searchMovies(movieView.getToolbarPanel().getSearchQuery());
        };
        movieView.getToolbarPanel().getSearchButton().addActionListener(e -> azioneCerca.run());
        movieView.getToolbarPanel().getSearchField().addActionListener(e -> azioneCerca.run());
    }

    private void vaiInModalitaEdit(Movie m) {
        FormState editState = new EditModeState(m);
        movieView.setCurrentState(editState);
        editState.enterState(movieView);
    }

    private Movie prendiFilmSelezionato() {
        int riga = movieView.getMovieTable().getSelectedRow();
        if (riga == -1) {
            mostraPopupErrore("Nessuna Selezione", "Per favore, seleziona un film dalla tabella.");
            return null;
        }
    
        return movieView.getCurrentMoviesList().get(riga);
    }

    private Movie prendiFilmDaClick(Point p) {
        int riga = movieView.getMovieTable().rowAtPoint(p);
        if (riga >= 0) {

            return movieView.getCurrentMoviesList().get(riga);
        }
        return null;
    }

    private boolean confermaRimozione() {
        int risp = JOptionPane.showConfirmDialog(movieView,
                "Sei sicuro di voler eliminare il film selezionato?",
                "Conferma", JOptionPane.YES_NO_OPTION);
        return risp == JOptionPane.YES_OPTION;
    }

    private void mostraPopupErrore(String tit, String msg) {
        JOptionPane.showMessageDialog(movieView, msg, tit, JOptionPane.ERROR_MESSAGE);
    }
}