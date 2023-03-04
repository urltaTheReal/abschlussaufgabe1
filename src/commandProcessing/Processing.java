package commandProcessing;

/**
 * @author urlta
 * 
 *         The structure of processing is so designed, that the executeCommand
 *         shall only be called, if isCommandCorrect returned true with the same
 *         input
 */
public interface Processing {

    public void executeCommand();

    /**
     * isCommandCorrect is to be implemented, that it checks the entered command for
     * semantical and logical Correctness. returns true if the command was entered
     * correctly and works logically
     * 
     * @return returns true if execute command can be executed without error
     */
    public boolean isCommandCorrect();
}
