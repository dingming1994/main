package ui.debug;

import main.MainController;

//@author A0113011L
public class UIDisplay {
    private UserInputReader userInputReader;
    private UserOutputWriter userOutputWriter;
    
    private final MainController mainController;
    
    private final static String MESSAGE_WELCOME = "Welcome to Taskline." +
            System.lineSeparator();
    
    public UIDisplay(MainController mainController) {
        this.mainController = mainController;
        
        userInputReader = new UserInputReader();
        userOutputWriter = new UserOutputWriter();
    }
    
    /**
     * Called from main
     */
    public void commandLoopIteration() {
        userOutputWriter.printOutput(MESSAGE_WELCOME);
        while (!isReadyToExit()) {
            String input = userInputReader.readInput();
            String output = mainController.runCommand(input);
            userOutputWriter.printOutput(output);
        }
    }
    
    public boolean isReadyToExit() {
        return mainController.isReadyToExit();
    }
}
