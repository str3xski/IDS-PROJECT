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
    
    private final JTextField txtTitolo = new JTextField();
    private final JTextField txtRegista = new JTextField();
    private final JTextField txtAnno = new JTextField();

    private final JComboBox<Category> comboCategoria;
    private final JComboBox<Status> comboStato;
    private final JComboBox<Integer> comboVoto;

    private final JButton btnConferma = new JButton("Aggiungi");
    private final JButton btnAnnulla = new JButton("Annulla");

    public MovieInputPanel() {
        this.setLayout(new GridLayout(2, 7, 10, 5));

        this.comboVoto = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        this.comboCategoria = new JComboBox<>(Category.values());
        this.comboStato = new JComboBox<>(Status.values());

        this.add(new JLabel("Titolo:")); 
        this.add(new JLabel("Regista:")); 
        this.add(new JLabel("Anno:")); 
        this.add(new JLabel("Categoria:")); 
        this.add(new JLabel("Stato:")); 
        this.add(new JLabel("Valutazione:")); 
        this.add(new JLabel("")); 

        this.add(txtTitolo);
        this.add(txtRegista);
        this.add(txtAnno);
        this.add(comboCategoria);
        this.add(comboStato);
        this.add(comboVoto);

        JPanel boxBottoni = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        boxBottoni.add(btnConferma);
        boxBottoni.add(btnAnnulla);
        this.add(boxBottoni);

        btnAnnulla.setVisible(false);

        ActionListener invioRapido = e -> btnConferma.doClick();
        txtTitolo.addActionListener(invioRapido);
        txtRegista.addActionListener(invioRapido);
        txtAnno.addActionListener(invioRapido);
    }

    public String getTitleText() { return txtTitolo.getText(); }
    public String getDirectorText() { return txtRegista.getText(); }
    public String getYearText() { return txtAnno.getText(); }
    public Category getSelectedCategory() { return (Category) comboCategoria.getSelectedItem(); }
    public Status getSelectedStatus() { return (Status) comboStato.getSelectedItem(); }
    public Integer getSelectedRating() { return (Integer) comboVoto.getSelectedItem(); }
    public JButton getSubmitButton() { return btnConferma; }
    public JButton getCancelButton() { return btnAnnulla; }

    /** Ripristina i campi del modulo allo stato vuoto */
    public void clearFields() {
        txtTitolo.setText("");
        txtRegista.setText("");
        txtAnno.setText("");
      
    }

    /** Carica i dati di un film esistente nei campi del pannello */
    public void populateForm(Movie m) {
        txtTitolo.setText(m.getTitle());
        txtRegista.setText(m.getDirector());
        txtAnno.setText(String.valueOf(m.getYear()));
        
    }

    public void focusTitleField() {
        txtTitolo.requestFocusInWindow();
    }
}