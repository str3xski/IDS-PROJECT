package Repository;
import pojo.Movie;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/*
Questa classe si occupa di salvare e leggere i film fisicamente sul computer utilizzando un file in formato JSON. 
Una volta implementata, trasformerà la lista dei film in testo leggibile per memorizzarla sul disco, 
assicurandosi che i dati non vadano persi quando si chiude il programma. 

 */



public class MovieRepositoryJsonImpl implements MovieRepository {

    private final Path storagePath;
    private final Gson jsonParser;
    private static final Type COLLECTION_TYPE = new TypeToken<List<Movie>>(){}.getType();

    public MovieRepositoryJsonImpl(String path) {
        // Inizializzazione dei componenti core per la gestione file
        this.storagePath = Paths.get(path);
        this.jsonParser = new GsonBuilder().setPrettyPrinting().create();
        setupPersistenceLayer();
    }

    /*
     * Verifica la presenza dello storage e prepara l'ambiente di persistenza.
     * Se il file non è presente, viene generato un array JSON vuoto predefinito.
     */
    private void setupPersistenceLayer() {
        try {
            // Controllo ed eventuale creazione delle directory genitrici
            Path parentDir = storagePath.getParent();
            if (parentDir != null) {
                Files.createDirectories(parentDir);
            }
            
            // Generazione del file con struttura base se mancante
            if (Files.notExists(storagePath)) {
                Files.writeString(storagePath, "[]", StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new RuntimeException("Inizializzazione del file JSON fallita: " + storagePath, e);
        }
    }

    @Override
    public List<Movie> findAll() {
        try {
            // Recupero del contenuto testuale dal file
            String content = Files.readString(this.storagePath, StandardCharsets.UTF_8);
            
            // Deserializzazione dell'oggetto lista
            List<Movie> data = jsonParser.fromJson(content, COLLECTION_TYPE);
            
            // Restituisce una lista vuota se il file era privo di oggetti validi
            return (data != null) ? data : new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException("Lettura dati JSON non riuscita: " + storagePath, e);
        }
    }

    @Override
    public void saveAll(List<Movie> moviesListCache) {
        try {
            // Conversione della cache in stringa formattata JSON
            String serializedData = this.jsonParser.toJson(moviesListCache);
            
            // Scrittura atomica sul file con sovrascrittura integrale
            Files.writeString(this.storagePath, serializedData, 
                StandardCharsets.UTF_8, 
                StandardOpenOption.CREATE, 
                StandardOpenOption.TRUNCATE_EXISTING, 
                StandardOpenOption.WRITE);
                
            // Messaggio informativo di avvenuta operazione
            System.out.println("Sincronizzazione completata: " + storagePath.toAbsolutePath());
        } catch (IOException err) {
            // Logging dell'errore per facilitare il debugging
            err.printStackTrace(); 
            throw new RuntimeException("Fallimento critico durante l'aggiornamento del database JSON", err);
        }
    }
}