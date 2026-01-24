package Command;
import java.util.Stack;

/*

Questa classe serve a gestire la cronologia delle azioni dell'utente, 
permettendo di annullare o ripetere gli inserimenti e le modifiche ai film. 
Il suo compito è garantire che ogni operazione sia reversibile, 
offrendo una navigazione sicura tra i cambiamenti fatti nell'archivio.

*/


public class CommandManager {

    // Stack per la memorizzazione delle azioni annullabili e ripristinabili
    private final Stack<Command> undoStack;
    private final Stack<Command> redoStack;

    public CommandManager() {
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    /**
     * Esegue un nuovo comando e aggiorna la cronologia.
     * Ogni nuova operazione comporta lo svuotamento della coda di Redo.
     */
    public void execute(Command command) throws Exception {
        // Esecuzione dell'azione richiesta
        command.execute();
        
        // Inserimento del comando nello stack di annullamento
        this.undoStack.push(command);
        
        // Reset della cronologia di Redo (nuova ramificazione delle azioni)
        this.redoStack.clear();
    }

    public Command undo() throws Exception {
        // Controllo della disponibilità di operazioni da annullare
        if (undoStack.isEmpty()) {
            return null;
        }

        // Estrazione dell'ultimo comando eseguito
        Command cmd = this.undoStack.pop();
        
        // Invocazione della logica di ripristino
        cmd.undo();
        
        // Spostamento del comando nello stack opposto
        this.redoStack.push(cmd);
        
        return cmd;
    }

    public Command redo() throws Exception {
        // Verifica la presenza di azioni precedentemente annullate
        if (redoStack.isEmpty()) {
            return null;
        }

        // Recupero dell'azione dalla cronologia di redo
        Command cmd = this.redoStack.pop();
        
        // Nuova esecuzione del comando
        cmd.execute();
        
        // Reinserimento nell'elenco di undo
        this.undoStack.push(cmd);
        
        return cmd;
    }
}