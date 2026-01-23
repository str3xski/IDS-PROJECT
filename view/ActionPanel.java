package view;

import javax.swing.*;


/*
 * Pannello laterale dedicato ai comandi rapidi di gestione.
 * Fornisce un'area visiva distinta per le operazioni di modifica 
 * e rimozione degli elementi selezionati nella lista.
 */
public class ActionPanel extends JPanel {

    private final JButton btnModifica;
    private final JButton btnElimina;

    public ActionPanel() {

        // Creazione dei pulsanti con icone testuali
        btnModifica = new JButton("Modifica Film");
        btnElimina = new JButton("Elimina Film");
 
    }

    // Metodi di accesso ai componenti della GUI
    public JButton getEditButton() { 
        return this.btnModifica; 
    }

    public JButton getDeleteButton() { 
        return this.btnElimina; 
    }
}