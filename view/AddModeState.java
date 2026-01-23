package view;

import DTO.MovieDTO;


/*
Questa classe gestisce il comportamento dell'interfaccia quando l'utente vuole aggiungere un nuovo film alla lista, seguendo il Pattern State.
 */
public class AddModeState implements FormState {

    @Override
    public FormState handleSubmit(MovieView context, MovieDTO dto) {
        return this;
    }

    @Override
    public void enterState(MovieView context) {

    }
}