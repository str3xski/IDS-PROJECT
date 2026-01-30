package test;

import DTO.MovieDTO;
import Repository.MovieRepositoryJsonImpl;
import pojo.*;
import service.*;
import java.io.File;
import java.util.List;


/*

Questa classe verifica che il "cervello" dell'applicazione funzioni: filtri, ordinamenti e salvataggio dati.

*/


public class TestServiceLayer {
    
    private static final String DB_TEST = "test_service.json";

    public static void main(String[] args) {
        System.out.println("=== AVVIO TEST SERVICE LAYER ===");
        
        // Setup pulito
        new File(DB_TEST).delete();
        MovieRepositoryJsonImpl repo = new MovieRepositoryJsonImpl(DB_TEST);
        MovieServiceImpl service = new MovieServiceImpl(repo);

        try {
            // 1. Test Inserimento
            System.out.print("Test Aggiunta Film... ");
            service.addMovie(new MovieDTO("Dune", "Villeneuve", "2021", Category.FANTASCIENZA, Status.VISTO, 5));
            service.addMovie(new MovieDTO("Titanic", "Cameron", "1997", Category.DRAMMATICO, Status.VISTO, 4));
            assertCondition(service.getAllMovies().size() == 2);

            // 2. Test Filtri
            System.out.print("Test Filtro Categoria (Fantascienza)... ");
            service.setFilterCategory(Category.FANTASCIENZA);
            List<Movie> filtrati = service.getAllMovies();
            assertCondition(filtrati.size() == 1 && filtrati.get(0).getTitle().equals("Dune"));
            
            // Reset filtri
            service.resetFiltersAndSort();

            // 3. Test Ordinamento (Strategy)
            System.out.print("Test Ordinamento per Anno... ");
            service.setSortStrategy(SortStrategyType.Anno);
            service.setSortDirection(SortDirection.ASCENDING);
            List<Movie> ordinati = service.getAllMovies();
            // Titanic (1997) deve essere prima di Dune (2021)
            assertCondition(ordinati.get(0).getTitle().equals("Titanic"));

        } catch (Exception e) {
            System.err.println("\nECCEZIONE: " + e.getMessage());
            e.printStackTrace();
        } finally {
            new File(DB_TEST).delete(); // Pulizia finale
            System.out.println("=== TEST SERVICE COMPLETATI ===\n");
        }
    }

    private static void assertCondition(boolean check) {
        if (check) System.out.println("[OK]");
        else System.out.println("[FAIL]");
    }
}