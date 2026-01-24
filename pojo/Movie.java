package pojo;
import java.util.Objects;
import java.util.UUID;


/*
Questa classe rappresenta il modello principale del film e serve a contenere tutte le sue informazioni 
in modo sicuro. Una volta implementata, permetterà di creare oggetti che non possono essere modificati 
accidentalmente (immutabili), garantendo che ogni film abbia un codice identificativo unico per essere 
riconosciuto correttamente dal sistema.
 */


public class Movie {
    private final String id;
    private final String title;
    private final String director;
    private final int year;
    private final int rating;
    private final Category category;
    private final Status status;

    // Costruttore privato che riceve i dati dal Builder
    public Movie(Builder b) {
        this.id = b.id;
        this.title = b.title;
        this.director = b.director;
        this.year = b.year;
        this.category = b.category;
        this.status = b.status;
        this.rating = b.rating;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    /*
     * Inner class statica per la costruzione fluida dell'oggetto Movie.
     */
    public static class Builder {
        private final String id;
        private String title;
        private String director;
        private int year;
        private int rating;
        private Category category;
        private Status status;

        // Inizializzazione per un nuovo film con generazione ID
        public Builder(String t, String d) {
            this.id = UUID.randomUUID().toString();
            this.title = t;
            this.director = d;
        }

        // Inizializzazione a partire da un oggetto esistente (per modifiche)
        private Builder(Movie m) {
            this.id = m.id;
            this.title = m.title;
            this.director = m.director;
            this.year = m.year;
            this.category = m.category;
            this.status = m.status;
            this.rating = m.rating;
        }

        public Builder title(String val) {
            this.title = val;
            return this;
        }

        public Builder director(String val) {
            this.director = val;
            return this;
        }

        public Builder year(int val) { 
            this.year = val; 
            return this; 
        }

        public Builder category(Category c) { 
            this.category = c; 
            return this; 
        }

        public Builder status(Status s) { 
            this.status = s; 
            return this; 
        }

        public Builder rating(int r) { 
            this.rating = r; 
            return this; 
        }

        public Movie build() {
            return new Movie(this);
        }
    }

    // --- Metodi Getter ---

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDirector() { return director; }
    public int getYear() { return year; }
    public int getRating() { return rating; }
    public Category getCategory() { return category; }
    public Status getStatus() { return status; }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Movie movie = (Movie) other;
        return Objects.equals(this.id, movie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Movie Details [");
        sb.append("Title: ").append(title);
        sb.append(", Director: ").append(director);
        sb.append(", Year: ").append(year);
        sb.append(", Rating: ").append(rating);
        sb.append("]");
        return sb.toString();
    }
}