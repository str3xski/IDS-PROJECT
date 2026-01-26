package view;

import javax.swing.*;
import pojo.Category;
import pojo.Status;
import pojo.Movie;
import java.awt.*;
import java.awt.event.ActionListener;


/*
Questa classe costruisce la parte superiore dell'interfaccia, ovvero il modulo dove inseriamo fisicamente 
i dati dei film. Utilizzando un GridLayout, organizza in modo ordinato etichette, campi di testo e menu a tendina per categorie, stati e voti.
 */


public class MovieInputPanel extends JPanel {
    
    // Componenti per l'immissione di testo
    private final JTextField txtTitolo = new JTextField();
    private final JTextField txtRegista = new JTextField();
    private final JTextField txtAnno = new JTextField();

    // Componenti di selezione
    private final JComboBox<Category> comboCategoria;
    private final JComboBox<Status> comboStato;
    private final JComboBox<Integer> comboVoto;

    // Pulsanti di controllo
    private final JButton btnConferma = new JButton("Aggiungi");
    private final JButton btnAnnulla = new JButton("Annulla");

    public MovieInputPanel() {
        // Layout a griglia per mantenere i componenti allineati (2 righe, 7 colonne)
        this.setLayout(new GridLayout(2, 7, 10, 5));

        // Inizializzazione dei selettori con i valori delle Enum
        this.comboVoto = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        this.comboCategoria = new JComboBox<>(Category.values());
        this.comboStato = new JComboBox<>(Status.values());

        // Definizione della prima riga: Etichette descrittive
        this.add(new JLabel("Titolo:")); 
        this.add(new JLabel("Regista:")); 
        this.add(new JLabel("Anno:")); 
        this.add(new JLabel("Categoria:")); 
        this.add(new JLabel("Stato:")); 
        this.add(new JLabel("Valutazione:")); 
        this.add(new JLabel("")); 

        // Seconda riga: Campi di input e pulsanti
        this.add(txtTitolo);
        this.add(txtRegista);
        this.add(txtAnno);
        this.add(comboCategoria);
        this.add(comboStato);
        this.add(comboVoto);

        // Raggruppamento dei bottoni nell'ultima cella della griglia
        JPanel boxBottoni = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        boxBottoni.add(btnConferma);
        boxBottoni.add(btnAnnulla);
        this.add(boxBottoni);

        // Impostazione iniziale: il tasto annulla è nascosto (modalità ADD)
        btnAnnulla.setVisible(false);

        // Gestore per l'invio rapido tramite tasto Invio
        ActionListener invioRapido = e -> btnConferma.doClick();
        txtTitolo.addActionListener(invioRapido);
        txtRegista.addActionListener(invioRapido);
        txtAnno.addActionListener(invioRapido);
    }

    // --- Metodi di Accesso (Getter) ---
    public String getTitleText() { return txtTitolo.getText(); }
    public String getDirectorText() { return txtRegista.getText(); }
    public String getYearText() { return txtAnno.getText(); }
    public Category getSelectedCategory() { return (Category) comboCategoria.getSelectedItem(); }
    public Status getSelectedStatus() { return (Status) comboStato.getSelectedItem(); }
    public Integer getSelectedRating() { return (Integer) comboVoto.getSelectedItem(); }
    public JButton getSubmitButton() { return btnConferma; }
    public JButton getCancelButton() { return btnAnnulla; }

    // --- Metodi Helper ---

    /** Ripristina i campi del modulo allo stato vuoto */
    public void clearFields() {
        txtTitolo.setText("");
        txtRegista.setText("");
        txtAnno.setText("");
        comboCategoria.setSelectedIndex(0);
        comboStato.setSelectedIndex(0);
        comboVoto.setSelectedIndex(0);
    }

    /** Carica i dati di un film esistente nei campi del pannello */
    public void populateForm(Movie m) {
        txtTitolo.setText(m.getTitle());
        txtRegista.setText(m.getDirector());
        txtAnno.setText(String.valueOf(m.getYear()));
        comboCategoria.setSelectedItem(m.getCategory());
        comboStato.setSelectedItem(m.getStatus());
        comboVoto.setSelectedItem(m.getRating());
    }

    /** Sposta il focus della tastiera sul campo del titolo */
    public void focusTitleField() {
        txtTitolo.requestFocusInWindow();
    }
}