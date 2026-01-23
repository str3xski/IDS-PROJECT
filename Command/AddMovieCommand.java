package Command;

/*
Questa classe serve a gestire l'aggiunta di un nuovo film alla collezione, 
tenendo traccia dell'oggetto creato per poterlo rimuovere in caso di annullamento. 
Una volta implementata, permetterà al sistema di inserire il film tramite il Service e di "tornare indietro" 
eliminandolo automaticamente se l'utente preme il tasto Undo.


*/

public class AddMovieCommand implements Command {


    @Override
    public void execute() throws Exception {
    }

    @Override
    public void undo() throws Exception {
        }


    @Override
    public void accept(CommandVisitor visitor) {
    }

}