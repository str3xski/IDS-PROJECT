package Command;

/*Il ruolo principale del Pattern Command qui è trasformare ogni azione dell'utente (aggiunta, modifica o eliminazione di un film) 
in un oggetto autonomo che contiene tutte le informazioni necessarie per essere eseguito o annullato. */


public interface Command {
    void execute() throws Exception;
    void undo() throws Exception;

    void accept(CommandVisitor visitor);
}
