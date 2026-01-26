package main;
import Command.CommandManager;
import controller.MovieController;
import Repository.MovieRepository;
import Repository.MovieRepositoryJsonImpl;
import service.MovieServiceImpl;
import view.MovieView;
import view.ViewListenerManager;
import javax.swing.*;

/*
 * Questa classe funge da Facade per l'avvio dell'intero sistema.
 * Si occupa di istanziare i componenti principali, iniettare le dipendenze
 * necessarie e far partire l'interfaccia grafica.
 */



public class Application {
    
    private final String PATH_DATABASE = "movies.json";

    public void start() {
        SwingUtilities.invokeLater(() -> {
            
            // 1. Creazione dei componenti del Model e del Repository
            MovieRepository repository = new MovieRepositoryJsonImpl(PATH_DATABASE);
            MovieServiceImpl service = new MovieServiceImpl(repository);
            
            // 2. Creazione della View e del CommandManager
            MovieView vista = new MovieView();
            CommandManager gestoreComandi = new CommandManager();
            
            // 3. Creazione del Controller
            MovieController controller = new MovieController(service, gestoreComandi);

            // 4. Configurazione della View
            vista.setController(controller);
            
            /* * ERRORE: Qui ho dimenticato di settare AddModeState.
             * Senza vista.setCurrentState(new AddModeState()), 
             * l'attributo statoCorrente rimane null.
             */

            // 5. Registrazione della View come Observer
            service.addObserver(vista);

            // 6. Collegamento dei listener
            ViewListenerManager gestoreEventi = new ViewListenerManager(vista, controller);
            gestoreEventi.bindListeners();

            // 7. Caricamento iniziale dei dati
            vista.aggiornaContenutoTabella(controller.getAllMovies());

            // 8. Visualizzazione
            vista.setVisible(true);
        });
    }
}