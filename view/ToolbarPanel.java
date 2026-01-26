package view;

import javax.swing.*;
import pojo.Category;
import pojo.Status;
import service.SortStrategyType;
import java.awt.*;


/*
 * Barra degli strumenti superiore dell'applicazione.
 * Fornisce i controlli necessari per l'ordinamento, il filtraggio e 
 * la ricerca dei film, oltre ai tasti rapidi per Undo e Redo.
 */


public class ToolbarPanel extends JToolBar {

    // Componenti per l'ordinamento
    private final JComboBox<SortStrategyType> comboOrdinamento;
    private final JToggleButton btnDirezione;

    // Componenti per i filtri
    private final JComboBox<Category> filtroCategoria;
    private final JComboBox<Status> filtroStato;
    private final JComboBox<Integer> filtroVoto;

    // Ricerca e Reset
    private final JTextField campoRicerca;
    private final JButton btnCerca;
    private final JButton btnReset;

    // Cronologia comandi
    private final JButton btnUndo;
    private final JButton btnRedo;

    public ToolbarPanel() {
        // --- Sezione Cronologia (Undo/Redo) ---
        btnUndo = new JButton("Annulla ↩️");
        btnRedo = new JButton("Ripristina ↪️");
        this.add(btnUndo);
        this.add(btnRedo);
        this.addSeparator();

        // --- Sezione Ordinamento ---
        this.add(new JLabel(" Ordina per: "));
        comboOrdinamento = new JComboBox<>(SortStrategyType.values());
        this.add(comboOrdinamento);

        btnDirezione = new JToggleButton("Crescente ⬆️");
        this.add(btnDirezione);
        this.addSeparator();

        // --- Sezione Filtraggio ---
        this.add(new JLabel(" Filtra per: "));
        filtroCategoria = creaComboConOpzioneNull(Category.values());
        filtroStato = creaComboConOpzioneNull(Status.values());
        filtroVoto = creaComboConOpzioneNull(new Integer[]{1, 2, 3, 4, 5});
        
        this.add(filtroCategoria);
        this.add(filtroStato);
        this.add(filtroVoto);
        this.addSeparator();

        // --- Sezione Ricerca ---
        this.add(new JLabel(" Cerca: "));
        campoRicerca = new JTextField(12);
        btnCerca = new JButton("Cerca");
        this.add(campoRicerca);
        this.add(btnCerca);
        this.addSeparator();

        // --- Azioni Globali ---
        btnReset = new JButton("Reset");
        btnReset.setPreferredSize(new Dimension(130, 28));
        this.add(btnReset);
    }

    /* Metodo helper per creare combo box che includono l'opzione "Tutti" */
    private <T> JComboBox<T> creaComboConOpzioneNull(T[] elementi) {
        JComboBox<T> combo = new JComboBox<>();
        combo.addItem(null); 
        for (T item : elementi) {
            combo.addItem(item);
        }

        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value == null) {
                    setText("Tutti");
                }
                return this;
            }
        });
        return combo;
    }

    // --- Metodi Getter ---
    public JComboBox<SortStrategyType> getSortComboBox() { return comboOrdinamento; }
    public JToggleButton getSortDirectionButton() { return btnDirezione; }
    public JComboBox<Category> getCategoryFilterComboBox() { return filtroCategoria; }
    public JComboBox<Status> getStatusFilterComboBox() { return filtroStato; }
    public JComboBox<Integer> getRatingFilterComboBox() { return filtroVoto; }
    public JButton getResetButton() { return btnReset; }
    public JButton getSearchButton() { return btnCerca; }
    public JTextField getSearchField() { return campoRicerca; }
    public String getSearchQuery() { return campoRicerca.getText(); }
    public JButton getUndoButton() { return btnUndo; }
    public JButton getRedoButton() { return btnRedo; }

    /** Ripristina i selettori dei filtri sul valore "Tutti" e pulisce la ricerca */
    public void resetFilterControls() {
        this.filtroCategoria.setSelectedItem(null);
        this.filtroStato.setSelectedItem(null);
        this.filtroVoto.setSelectedItem(null);
        this.campoRicerca.setText("");
    }

    /** Riporta i controlli di ordinamento allo stato iniziale */
    public void resetSortControls() {
        this.comboOrdinamento.setSelectedItem(SortStrategyType.Titolo);
        this.btnDirezione.setSelected(false);
        this.btnDirezione.setText("Crescente ⬆");
    }
}