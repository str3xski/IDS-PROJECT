package DTO;

import pojo.Category;
import pojo.Status;

/*
 * Oggetto di Trasferimento Dati (DTO) per le informazioni dei film.
 * Viene impiegato per veicolare i dati grezzi dall'interfaccia utente
 * verso i livelli logici del sistema, semplificando il passaggio dei parametri.
 */
public class MovieDTO {
    
    // Campi pubblici finali per garantire l'immutabilità del pacchetto dati
    public final String title;
    public final String director;
    public final String yearStr;
    public final Category category;
    public final Status status;
    public final Integer rating;

    public MovieDTO(String t, String d, String y, Category cat, Status st, Integer r) {
        // Assegnazione diretta dei valori provenienti dal form della View
        this.title = t;
        this.director = d;
        this.yearStr = y;
        this.category = cat;
        this.status = st;
        this.rating = r;
    }
}