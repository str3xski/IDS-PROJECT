package view;

import DTO.MovieDTO;


/*
 * Stato del modulo durante la fase di editing.
 * Si occupa di caricare i dati del film selezionato nel form e 
 * gestire la conferma dell'aggiornamento.
 */
public class EditModeState implements FormState {
    @Override
    public FormState handleSubmit(MovieView context, MovieDTO dati) {
            return this;
        }


    @Override
    public void enterState(MovieView context) {
    
    }
}