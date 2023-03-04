package commandProcessing;

/**
 * @author urlta
 * 
 * This class implements the execution of the end command
 *
 */
public class ProcessEndCommand implements Processing {

    private final String[] command;

    /**
     * @param command the full line of text entered in the console split after each
     *                space
     */
    public ProcessEndCommand(String[] command) {
        this.command = command;
    }

    /**
     * executeCommand can only be successfully executed, if isCommandCorrect returns
     * true for the same input
     *
     */
    public void executeCommand() {
        /*
         * this command changes nothing but changing the isTurnEnded variable in
         * CommandProcessing to true. The calculations after the turn ended will be done
         * in the main method to be sure, that they will always be executed. if
         * something should ever be changed in a way, that only triggers if end turn is
         * entered it will be processed here
         * 
         */
    }

    /**
     * isCommandCorrect checks the entered command for semantical and logical
     * Correctness. returns true if the command was entered correctly and works
     * logically
     * 
     * @return returns true if execute command can be executed without error
     */
    public boolean isCommandCorrect() {
        if (command.length > 2) {
            System.out.println(
                    "Error: too many arguments only enter argument turn after end-command to end yout turn and please seperate with only 1 space");
            return false;
        }
        if (command.length < 2) {
            System.out.println("Error: please enter argument turn after end command");
            return false;
        }
        if (!command[1].equals("turn")) {
            System.out.println("Error: only the argument turn is allowed after the end command");
            return false;
        }
        return true;
    }
}
