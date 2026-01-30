package test;

/*
Questa classe si concentra esclusivamente sul package Command. Verifica che le azioni siano reversibili e che lo stack (pila) dei comandi funzioni correttamente.
*/

import Command.*;
import DTO.MovieDTO;
import Repository.MovieRepositoryJsonImpl;
import pojo.*;
import service.MovieServiceImpl;
import java.io.File;

public class TestCommandSystem {

    private static final String DB_CMD = "test_commands.json";

    public static void main(String[] args) {
        System.out.println("=== AVVIO TEST COMMAND SYSTEM ===");
        
        new File(DB_CMD).delete();
        MovieServiceImpl service = new MovieServiceImpl(new MovieRepositoryJsonImpl(DB_CMD));
        CommandManager cmdManager = new CommandManager();

        try {
            // Preparazione dati
            MovieDTO dto = new MovieDTO("Inception", "Nolan", "2010", Category.FANTASCIENZA, Status.VISTO, 5);
            AddMovieCommand addCmd = new AddMovieCommand(service, dto);

            // 1. Test Esecuzione (Execute)
            System.out.print("Test Execute (Aggiunta)... ");
            cmdManager.execute(addCmd);
            assertCondition(service.getAllMovies().size() == 1);

            // 2. Test Annullamento (Undo)
            System.out.print("Test Undo (Rimozione)... ");
            cmdManager.undo();
            assertCondition(service.getAllMovies().isEmpty());

            // 3. Test Ripristino (Redo)
            System.out.print("Test Redo (Reinserimento)... ");
            cmdManager.redo();
            assertCondition(service.getAllMovies().size() == 1);

            // 4. Test Modifica e Undo Modifica
            System.out.print("Test Undo su Modifica... ");
            Movie originale = service.getAllMovies().get(0);
            MovieDTO modifiche = new MovieDTO("Inception Edit", "Nolan", "2010", Category.FANTASCIENZA, Status.VISTO, 5);
            
            EditMovieCommand editCmd = new EditMovieCommand(service, originale, modifiche);
            cmdManager.execute(editCmd);
            
            // Verifica modifica
            boolean modificato = service.getAllMovies().get(0).getTitle().equals("Inception Edit");
            if(modificato) {
                cmdManager.undo(); // Torno indietro
                boolean tornatoIndietro = service.getAllMovies().get(0).getTitle().equals("Inception");
                assertCondition(tornatoIndietro);
            } else {
                System.out.println("[FAIL - Modifica non applicata]");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            new File(DB_CMD).delete();
            System.out.println("=== TEST COMMAND COMPLETATI ===\n");
        }
    }

    private static void assertCondition(boolean check) {
        if (check) System.out.println("[OK]");
        else System.out.println("[FAIL]");
    }
}