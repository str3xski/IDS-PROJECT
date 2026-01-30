package test;

/*

Questo è fondamentale per l'interfaccia grafica. 
Verifica che la View venga davvero avvisata quando succede qualcosa.
Poiché non possiamo usare la vera MovieView (che aprirebbe una finestra), 
creiamo una classe "finta" (Mock) che implementa Observer solo per vedere se viene chiamata.

*/


import DTO.MovieDTO;
import Repository.MovieRepositoryJsonImpl;
import pojo.*;
import service.*;
import java.io.File;

public class TestObserverMechanism {

    private static final String DB_OBSERVER = "test_observer.json";

    // Classe interna "Mock" che finge di essere la View
    static class MockView implements Observer {
        public boolean notificaRicevuta = false;
        public int contatoreNotifiche = 0;

        @Override
        public void update() {
            this.notificaRicevuta = true;
            this.contatoreNotifiche++;
            System.out.println("   -> [MockView] Ho ricevuto una notifica dal Service!");
        }
        
        public void reset() {
            this.notificaRicevuta = false;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== AVVIO TEST OBSERVER PATTERN ===");
        
        new File(DB_OBSERVER).delete();
        MovieServiceImpl service = new MovieServiceImpl(new MovieRepositoryJsonImpl(DB_OBSERVER));
        
        // 1. Creazione e registrazione dell'osservatore
        MockView fintaView = new MockView();
        service.addObserver(fintaView);

        try {
            // Test A: Notifica su Aggiunta
            System.out.println("Test A: Aggiunta Film");
            fintaView.reset();
            service.addMovie(new MovieDTO("Test", "Test", "2024", Category.ALTRO, Status.DA_VEDERE, 1));
            
            assertCondition(fintaView.notificaRicevuta);

            // Test B: Notifica su Cambio Filtro
            System.out.println("Test B: Cambio Filtro");
            fintaView.reset();
            service.setFilterCategory(Category.AZIONE); // Deve scatenare notifyObservers()
            
            assertCondition(fintaView.notificaRicevuta);

            // Test C: Notifica su Ordinamento
            System.out.println("Test C: Cambio Ordinamento");
            fintaView.reset();
            service.setSortStrategy(SortStrategyType.Punteggio); // Deve scatenare notifyObservers()
            
            assertCondition(fintaView.notificaRicevuta);

            // Verifica Totale
            System.out.println("Totale notifiche ricevute: " + fintaView.contatoreNotifiche);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            new File(DB_OBSERVER).delete();
            System.out.println("=== TEST OBSERVER COMPLETATI ===\n");
        }
    }

    private static void assertCondition(boolean check) {
        if (check) System.out.println("   RISULTATO: [OK]");
        else System.out.println("   RISULTATO: [FAIL] L'observer non è stato notificato.");
    }
}