package test;


/*
Questa classe simula il comportamento dell'interfaccia utente (senza grafica). 
Testa il MovieController per vedere se blocca i dati invalidi e se coordina correttamente Service e Comandi.
*/

import Command.CommandManager;
import DTO.MovieDTO;
import Repository.MovieRepositoryJsonImpl;
import controller.MovieController;
import pojo.*;
import service.MovieServiceImpl;
import java.io.File;

public class TestControllerFlow {

    private static final String DB_CTRL = "test_controller.json";

    public static void main(String[] args) {
        System.out.println("=== AVVIO TEST CONTROLLER & VALIDATION ===");
        
        new File(DB_CTRL).delete();
        MovieServiceImpl service = new MovieServiceImpl(new MovieRepositoryJsonImpl(DB_CTRL));
        CommandManager manager = new CommandManager();
        MovieController controller = new MovieController(service, manager);

        try {
            // 1. Test Validazione Campi Obbligatori
            System.out.print("Test Validazione (Titolo vuoto)... ");
            MovieDTO dtoVuoto = new MovieDTO("", "Regista", "2022", Category.ALTRO, Status.DA_VEDERE, 1);
            Movie result = controller.addMovieRequest(dtoVuoto);
            
            // Il controller deve restituire null e stampare errore, NON aggiungere il film
            assertCondition(result == null && service.getAllMovies().isEmpty());

            // 2. Test Validazione Anno
            System.out.print("Test Validazione (Anno non numerico)... ");
            MovieDTO dtoAnnoErrato = new MovieDTO("Film", "Regista", "Duemila", Category.ALTRO, Status.DA_VEDERE, 1);
            Movie result2 = controller.addMovieRequest(dtoAnnoErrato);
            assertCondition(result2 == null);

            // 3. Test Flusso Corretto
            System.out.print("Test Flusso Completo (Inserimento Valido)... ");
            MovieDTO dtoValido = new MovieDTO("Pulp Fiction", "Tarantino", "1994", Category.AZIONE, Status.VISTO, 5);
            Movie filmCreato = controller.addMovieRequest(dtoValido);
            
            boolean verificaService = service.getAllMovies().size() == 1;
            boolean verificaReturn = filmCreato != null && filmCreato.getTitle().equals("Pulp Fiction");
            
            assertCondition(verificaService && verificaReturn);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            new File(DB_CTRL).delete();
            System.out.println("=== TEST CONTROLLER COMPLETATI ===\n");
        }
    }

    private static void assertCondition(boolean check) {
        if (check) System.out.println("[OK]");
        else System.out.println("[FAIL]");
    }
}