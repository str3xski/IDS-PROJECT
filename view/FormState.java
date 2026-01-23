package view;

import DTO.MovieDTO;

/*
Questa interfaccia è il pilastro del Pattern State applicato alla nostra interfaccia grafica. 
Il suo compito è definire un comportamento standard per gestire i due momenti critici del modulo di inserimento:
*/


public interface FormState {
    FormState handleSubmit(MovieView context, MovieDTO dto);
    void enterState(MovieView context);
}
