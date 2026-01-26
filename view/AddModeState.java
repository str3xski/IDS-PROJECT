package view;
import DTO.MovieDTO;
import pojo.Movie;
import javax.swing.JOptionPane;


/*
Questa classe gestisce il comportamento dell'interfaccia quando l'utente vuole aggiungere un nuovo film alla lista, seguendo il Pattern State.
 */

public class AddModeState implements FormState {

    @Override
    public FormState handleSubmit(MovieView context, MovieDTO dto) {
        // Invio della richiesta di creazione al controller
        Movie nuovoFilm = context.getController().addMovieRequest(dto);

        // Se l'operazione ha successo, puliamo i campi e selezioniamo il nuovo record
        if (nuovoFilm != null) {
            context.getInputPanel().clearFields();
            context.selectRowById(nuovoFilm.getId());
        } else {
            // Notifica all'utente in caso di problemi con i dati inseriti
            JOptionPane.showMessageDialog(context,
                    "Inserimento non riuscito: verificare i dati inseriti.",
                    "Errore di Validazione", 
                    JOptionPane.ERROR_MESSAGE);
        }
        
        return this;
    }

    @Override
    public void enterState(MovieView context) {
        // Configurazione estetica del pannello di input per la modalità ADD
        MovieInputPanel inputPanel = context.getInputPanel();
        
        inputPanel.clearFields();
        inputPanel.getSubmitButton().setText("AGGIUNGI");
        
        // In modalità inserimento il tasto annulla viene nascosto
        inputPanel.getCancelButton().setVisible(false);
    }
}