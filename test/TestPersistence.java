package test;

/*
Questo test simula lo scenario "chiudo e riapro l'app". 
Verifica che i dati salvati su disco sopravvivano effettivamente al riavvio dell'applicazione 
e che il formato JSON sia corretto.
*/


import Repository.MovieRepositoryJsonImpl;
import pojo.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestPersistence {

    private static final String DB_PERSISTENCE = "test_persistence.json";

    public static void main(String[] args) {
        System.out.println("=== AVVIO TEST PERSISTENZA E FILE SYSTEM ===");
        
        // Pulizia preventiva
        new File(DB_PERSISTENCE).delete();

        try {
            // FASE 1: Scrittura dati (Simulazione sessione 1)
            System.out.print("Fase 1: Scrittura su disco... ");
            MovieRepositoryJsonImpl repo1 = new MovieRepositoryJsonImpl(DB_PERSISTENCE);
            
            // Creiamo manualmente una lista da salvare
            List<Movie> listaOriginale = new ArrayList<>();
            Movie m1 = new Movie.Builder("The Matrix", "Wachowski").year(1999).build();
            Movie m2 = new Movie.Builder("Joker", "Phillips").year(2019).build();
            
            listaOriginale.add(m1);
            listaOriginale.add(m2);
            
            repo1.saveAll(listaOriginale);
            System.out.println("[OK]");

            // FASE 2: Riavvio (Simulazione sessione 2)
            System.out.print("Fase 2: Ricaricamento dati (nuova istanza)... ");
            
            // Creiamo una NUOVA istanza del repository sullo stesso file
            MovieRepositoryJsonImpl repo2 = new MovieRepositoryJsonImpl(DB_PERSISTENCE);
            List<Movie> listaCaricata = repo2.findAll();
            
            // Verifiche
            boolean checkNumero = listaCaricata.size() == 2;
            boolean checkTitolo = listaCaricata.get(0).getTitle().equals("The Matrix");
            boolean checkID = listaCaricata.get(1).getId().equals(m2.getId()); // ID deve restare uguale!

            if (checkNumero && checkTitolo && checkID) {
                System.out.println("[OK]");
            } else {
                System.out.println("[FAIL] Dati non corrispondenti.");
            }

            // FASE 3: Controllo esistenza file fisico
            System.out.print("Fase 3: Verifica file fisico... ");
            File f = new File(DB_PERSISTENCE);
            if (f.exists() && f.length() > 0) {
                System.out.println("[OK] File trovato: " + f.getAbsolutePath());
            } else {
                System.out.println("[FAIL] File non creato.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Pulizia finale
            new File(DB_PERSISTENCE).delete();
            System.out.println("=== TEST PERSISTENZA COMPLETATI ===\n");
        }
    }
}