package service;

import DTO.MovieDTO;
import pojo.*;
import Repository.MovieRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
Questa classe gestisce la logica centrale dell'applicazione, 
filtrando e ordinando i film in tempo reale e salvando automaticamente ogni modifica sul file JSON
 */

public class MovieServiceImpl implements MovieService, Subject {
    
    private final MovieRepository repository;
    private final List<Observer> listObservers;
    private final List<Movie> cache;

    // Stato attuale di ordinamento e filtraggio
    private SortingStrategy strategy;
    private SortDirection direction;
    private Category filterCategory = null;
    private Status filterStatus = null;
    private Integer filterRating = null;
    private String searchQuery = null;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.repository = movieRepository;
        this.listObservers = new ArrayList<>();
        
        // Caricamento iniziale dei dati dal repository
        this.cache = movieRepository.findAll();

        // Configurazione di default
        this.strategy = new SortByTitleStrategy();
        this.direction = SortDirection.ASCENDING;
    }

    @Override
    public List<Movie> getAllMovies() {
        // Fase 1: Ricerca testuale
        List<Movie> step1 = applySearch(this.cache);

        // Fase 2: Filtraggio per attributi
        List<Movie> step2 = step1.stream()
                .filter(m -> (filterCategory == null || m.getCategory().equals(filterCategory)))
                .filter(m -> (filterStatus == null || m.getStatus().equals(filterStatus)))
                .filter(m -> (filterRating == null || m.getRating() == filterRating.intValue()))
                .collect(Collectors.toList());

        // Fase 3: Applicazione ordinamento
        if (this.strategy != null) {
            this.strategy.sort(step2, this.direction);
        }

        return step2;
    }

    private List<Movie> applySearch(List<Movie> baseList) {
        if (searchQuery == null || searchQuery.isEmpty()) {
            return new ArrayList<>(baseList);
        }
        
        String term = searchQuery.toLowerCase();
        return baseList.stream()
                .filter(m -> m.getTitle().toLowerCase().contains(term) || 
                             m.getDirector().toLowerCase().contains(term))
                .collect(Collectors.toList());
    }

    @Override
    public Movie addMovie(MovieDTO dati) throws Exception {
        // Validazione minima integrata
        if (dati.title == null || dati.title.trim().isEmpty()) {
            throw new Exception("Attenzione: il titolo è obbligatorio.");
        }

        int anno = Integer.parseInt(dati.yearStr);

        // Costruzione oggetto tramite Builder
        Movie nuovoFilm = new Movie.Builder(dati.title, dati.director)
                .year(anno)
                .category(dati.category)
                .status(dati.status)
                .rating(dati.rating)
                .build();

        this.cache.add(nuovoFilm);
        sincronizzaESegnala();
        
        return nuovoFilm;
    }

    @Override
    public Movie editMovie(String id, MovieDTO dati) throws Exception {
        Movie target = cache.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new Exception("Film non trovato con ID: " + id));

        int anno = Integer.parseInt(dati.yearStr);

        Movie aggiornato = target.toBuilder()
                .title(dati.title)
                .director(dati.director)
                .year(anno)
                .category(dati.category)
                .status(dati.status)
                .rating(dati.rating)
                .build();

        updateMovie(aggiornato);
        return aggiornato;
    }

    @Override
    public void updateMovie(Movie film) {
        int pos = cache.indexOf(film);
        if (pos != -1) {
            cache.set(pos, film);
            sincronizzaESegnala();
        }
    }

    @Override
    public void deleteMovie(String id) {
        if (cache.removeIf(m -> m.getId().equals(id))) {
            sincronizzaESegnala();
        }
    }

    @Override
    public void addMovieObject(Movie film) {
        this.cache.add(film);
        sincronizzaESegnala();
    }

    // --- Gestione Observer ---

    @Override
    public void addObserver(Observer o) {
        this.listObservers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        this.listObservers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer obs : listObservers) {
            obs.update();
        }
    }

    // --- Helper interno per persistenza ---
    private void sincronizzaESegnala() {
        this.repository.saveAll(this.cache);
        notifyObservers();
    }

    // --- Filtri e Ordinamento ---

    @Override
    public void setSortStrategy(SortStrategyType tipo) {
        SortStrategyType scelta = (tipo == null) ? SortStrategyType.Titolo : tipo;

        switch (scelta) {
            case Anno:   this.strategy = new SortByYearStrategy(); break;
            case Punteggio: this.strategy = new SortByRatingStrategy(); break;
            default:     this.strategy = new SortByTitleStrategy(); break;
        }
        notifyObservers();
    }

    @Override
    public void setSortDirection(SortDirection dir) {
        this.direction = dir;
        notifyObservers();
    }

    @Override
    public void setFilterCategory(Category c) {
        this.filterCategory = c;
        notifyObservers();
    }

    @Override
    public void setFilterStatus(Status s) {
        this.filterStatus = s;
        notifyObservers();
    }

    @Override
    public void setFilterRating(Integer r) {
        this.filterRating = r;
        notifyObservers();
    }

    @Override
    public void setSearchQuery(String q) {
        this.searchQuery = (q == null || q.isBlank()) ? null : q.trim();
        notifyObservers();
    }

    @Override
    public void resetFiltersAndSort() {
        this.filterCategory = null;
        this.filterStatus = null;
        this.filterRating = null;
        this.searchQuery = null;
        this.strategy = new SortByTitleStrategy();
        this.direction = SortDirection.ASCENDING;
        notifyObservers();
    }
}