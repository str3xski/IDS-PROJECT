package view;

import DTO.MovieDTO;
import pojo.Movie;
import javax.swing.JOptionPane;


/*
 * Stato del modulo durante la fase di editing.
 * Si occupa di caricare i dati del film selezionato nel form e 
 * gestire la conferma dell'aggiornamento.
 */




public class EditModeState implements FormState {

    private final Movie filmCorrente;

    public EditModeState(Movie filmSelezionato) {
        this.filmCorrente = filmSelezionato;
    }

    @Override
    public FormState handleSubmit(MovieView context, MovieDTO dati) {
        // Richiesta di aggiornamento inviata al controller
        Movie filmAggiornato = context.getController().editMovieRequest(this.filmCorrente, dati);

        if (filmAggiornato != null) {
            // Se va a buon fine, evidenziamo il film e torniamo in modalità inserimento
            context.selectRowById(filmAggiornato.getId());
            return new AddModeState();
        } else {
            // Messaggio in caso di errore durante il salvataggio
            JOptionPane.showMessageDialog(context, 
                    "Errore: aggiornamento fallito.", 
                    "Messaggio di Errore", 
                    JOptionPane.ERROR_MESSAGE);
            return this;
        }
    }

    @Override
    public void enterState(MovieView context) {
        // Recupero del pannello di input per configurarlo in modalità EDIT
        MovieInputPanel inputPanel = context.getInputPanel();
        
        // Caricamento dei dati esistenti nei campi del form
        inputPanel.populateForm(this.filmCorrente);
        
        // Aggiornamento dell'estetica dei pulsanti
        inputPanel.getSubmitButton().setText("SALVA MODIFICHE");
        inputPanel.getCancelButton().setVisible(true);
        
        // Posizionamento del cursore sul primo campo utile
        inputPanel.focusTitleField();
    }
}