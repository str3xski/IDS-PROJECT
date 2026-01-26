package main;

import Command.CommandManager;
import controller.MovieController;
import Repository.MovieRepository;
import Repository.MovieRepositoryJsonImpl;
import service.MovieServiceImpl;
import view.AddModeState;
import view.MovieView;
import view.ViewListenerManager;

import javax.swing.*;

/*
 * Questa classe funge da Facade per l'avvio dell'intero sistema.
 * Si occupa di istanziare i componenti principali, iniettare le dipendenze
 * necessarie e far partire l'interfaccia grafica.
 */
public class Application {
    
    // Percorso del database locale in formato JSON
    private final String PATH_DATABASE = "movies.json";

    public void start() {
        // L'avvio avviene nel thread sicuro di Swing (EDT)
        SwingUtilities.invokeLater(() -> {
            
            // 1. Creazione dei componenti del Model e del Repository
            MovieRepository repository = new MovieRepositoryJsonImpl(PATH_DATABASE);
            MovieServiceImpl service = new MovieServiceImpl(repository);
            
            // 2. Creazione della View e del CommandManager per Undo/Redo
            MovieView vista = new MovieView();
            CommandManager gestoreComandi = new CommandManager();
            
            // 3. Creazione del Controller con iniezione delle dipendenze
            MovieController controller = new MovieController(service, gestoreComandi);

            // 4. Configurazione iniziale della View
            vista.setController(controller);
            
            // Impostiamo lo stato iniziale del modulo (Inserimento)
            vista.setCurrentState(new AddModeState());
            vista.getCurrentState().enterState(vista);

            // 5. Registrazione della View come Observer per ricevere aggiornamenti
            service.addObserver(vista);

            // 6. Collegamento di tutti i listener della GUI
            ViewListenerManager gestoreEventi = new ViewListenerManager(vista, controller);
            gestoreEventi.bindListeners();

            // 7. Caricamento iniziale dei dati nella tabella
            vista.aggiornaContenutoTabella(controller.getAllMovies());

            // 8. Visualizzazione della finestra principale
            vista.setVisible(true);
        });
    }
}