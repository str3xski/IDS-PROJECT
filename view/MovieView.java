package view;

import controller.MovieController;
import pojo.Movie;
import service.Observer;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/*
Questa classe è il cuore visivo del progetto e rappresenta la finestra principale dell'applicazione. 
Seguendo il pattern Model-View-Controller (MVC), si occupa di assemblare tutti i pezzi dell'interfaccia
 */


public class MovieView extends JFrame implements Observer {

    private MovieController controller;
    private JTable tabellaFilm;
    private DefaultTableModel modelloTabella;
    
    private MovieInputPanel pannelloInput;
    private ToolbarPanel pannelloToolbar;
    private ActionPanel pannelloAzioni;
    
    private List<Movie> listaFilmAttuale;
    private FormState statoCorrente;

    public MovieView() {
        // Configurazione parametri della finestra
        this.setTitle("Catalogo Film");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(1300, 600));

        configuraComponenti();

        this.pack();
        this.setLocationRelativeTo(null); 
    }

    /* Inizializzazione e posizionamento dei widget grafici */
    private void configuraComponenti() {
        // Definizione colonne della tabella
        String[] intestazioni = {"Titolo", "Regista", "Anno", "Categoria", "Stato", "Voto"};
        
        modelloTabella = new DefaultTableModel(intestazioni, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { 
                return false; 
            }
        };

        tabellaFilm = new JTable(modelloTabella);
        tabellaFilm.getTableHeader().setReorderingAllowed(false);
        
        // Impostazione larghezza colonne personalizzata
        impostaLarghezzaColonne();

        // Istanza dei pannelli specializzati
        pannelloInput = new MovieInputPanel();
        pannelloToolbar = new ToolbarPanel();
        pannelloAzioni = new ActionPanel();
        
        pannelloInput.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Assemblaggio layout principale
        this.setLayout(new BorderLayout());
        this.add(pannelloToolbar, BorderLayout.NORTH);
        this.add(new JScrollPane(tabellaFilm), BorderLayout.CENTER);
        this.add(pannelloInput, BorderLayout.SOUTH);
        this.add(pannelloAzioni, BorderLayout.EAST);
    }

    private void impostaLarghezzaColonne() {
        tabellaFilm.getColumnModel().getColumn(0).setPreferredWidth(250);
        tabellaFilm.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabellaFilm.getColumnModel().getColumn(2).setPreferredWidth(70);
        tabellaFilm.getColumnModel().getColumn(3).setPreferredWidth(120);
        tabellaFilm.getColumnModel().getColumn(4).setPreferredWidth(120);
        tabellaFilm.getColumnModel().getColumn(5).setPreferredWidth(50);
    }

    @Override
    public void update() {
        // Sincronizzazione con il controller per ottenere i dati aggiornati
        if (this.controller != null) {
            this.listaFilmAttuale = controller.getAllMovies();
            aggiornaContenutoTabella(this.listaFilmAttuale);
        }
    }

    public void aggiornaContenutoTabella(List<Movie> film) {
        // Aggiornamento del modello tabella in modo thread-safe
        SwingUtilities.invokeLater(() -> {
            modelloTabella.setRowCount(0); 
            if (film != null) {
                for (Movie m : film) {
                    Object[] riga = {
                        m.getTitle(),
                        m.getDirector(),
                        m.getYear(),
                        m.getCategory(),
                        m.getStatus(),
                        m.getRating()
                    };
                    modelloTabella.addRow(riga);
                }
            }
            modelloTabella.fireTableDataChanged();
        });
    }

    public void selectRowById(String id) {
        int indiceTrovato = -1;
        for (int i = 0; i < listaFilmAttuale.size(); i++) {
            if (listaFilmAttuale.get(i).getId().equals(id)) {
                indiceTrovato = i;
                break;
            }
        }
        
        if (indiceTrovato != -1) {
            int rigaVista = tabellaFilm.convertRowIndexToView(indiceTrovato);
            if (rigaVista != -1) {
                tabellaFilm.setRowSelectionInterval(rigaVista, rigaVista);
                tabellaFilm.scrollRectToVisible(tabellaFilm.getCellRect(rigaVista, 0, true));
            }
        }
    }

    // --- Metodi Accessori (Getters e Setters) ---

    public void setController(MovieController controller) {
        this.controller = controller;
        this.update(); 
    }

    public MovieController getController() { return controller; }
    public MovieInputPanel getInputPanel() { return pannelloInput; }
    public ToolbarPanel getToolbarPanel() { return pannelloToolbar; }
    public ActionPanel getActionPanel() { return pannelloAzioni; }
    public JTable getMovieTable() { return tabellaFilm; }
    public List<Movie> getCurrentMoviesList() { return listaFilmAttuale; }
    public FormState getCurrentState() { return statoCorrente; }
    public void setCurrentState(FormState state) { this.statoCorrente = state; }
}