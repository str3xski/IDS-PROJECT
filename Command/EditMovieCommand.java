package Command;


/*

Questa classe serve a gestire la modifica dei dati di un film, 
memorizzando sia le nuove informazioni che quelle vecchie. 
Una volta implementata, permetterà al sistema di aggiornare i dettagli di un film nell'archivio e, 
se necessario, di ripristinare istantaneamente lo stato originale in caso di annullamento.

*/

public class EditMovieCommand implements Command {

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