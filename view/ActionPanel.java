package view;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;


/*
 * Pannello laterale dedicato ai comandi rapidi di gestione.
 * Fornisce un'area visiva distinta per le operazioni di modifica 
 * e rimozione degli elementi selezionati nella lista.
 */


public class ActionPanel extends JPanel {

    private final JButton btnModifica;
    private final JButton btnElimina;

    public ActionPanel() {
        // Definizione del bordo con titolo e spaziatura interna
        TitledBorder bordoTitolo = BorderFactory.createTitledBorder(
                new EmptyBorder(10, 10, 10, 10),
                "Operazioni",
                TitledBorder.LEFT,
                TitledBorder.TOP
        );
        this.setBorder(bordoTitolo);

        // Allineamento verticale dei componenti
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Creazione dei pulsanti con icone testuali
        btnModifica = new JButton("Modifica Film");
        btnElimina = new JButton("Elimina Film");

        // Dimensionamento uniforme per una migliore resa estetica
        Dimension areaPulsante = new Dimension(200, 40);
        btnModifica.setPreferredSize(areaPulsante);
        btnElimina.setPreferredSize(areaPulsante);
        
        // Centratura orizzontale dei bottoni nel pannello
        btnModifica.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnElimina.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Composizione del layout con spaziatori dinamici
        this.add(Box.createVerticalGlue()); 
        this.add(btnModifica);
        this.add(Box.createRigidArea(new Dimension(0, 12))); // Spazio leggermente variato
        this.add(btnElimina);
        this.add(Box.createVerticalGlue()); 
    }

    // Metodi di accesso ai componenti della GUI
    public JButton getEditButton() { 
        return this.btnModifica; 
    }

    public JButton getDeleteButton() { 
        return this.btnElimina; 
    }
}