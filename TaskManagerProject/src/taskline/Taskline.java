package taskline;

import io.FileInputOutput;
import main.MainController;
import manager.ManagerHolder;
import ui.UIDisplay;
import data.TaskData;

/**
 * Does the main initialization of the program structure.
 *
 * @author Oh
 */
public class Taskline {

    public static void main(String[] args) {
        //setupLogger();
        
        String fileName = "tasks.txt";

        TaskData taskData = new TaskData();
        FileInputOutput fileInputOutput = new FileInputOutput(taskData, fileName);
        ManagerHolder managerHolder = new ManagerHolder(taskData, fileInputOutput);
        MainController mainController = new MainController(managerHolder);
        UIDisplay uiDisplay = new UIDisplay(mainController);

        startCommandLoop(uiDisplay);
    }

    private static void startCommandLoop(UIDisplay uiDisplay) {
        while (!uiDisplay.isReadyToExit()) {
            uiDisplay.commandLoopIteration();
        }
    }
    
    /*public static void setupLogger() {
        Logger log = Logger.getLogger("ASDDSAD");  
        log.setUseParentHandlers(false);
        FileHandler fileHandler;  

        try {  
            fileHandler = new FileHandler("taskline.log", 1000000, 1, true);
            SimpleFormatter formatter = new SimpleFormatter();  
            fileHandler.setFormatter(formatter);  
            
            log.addHandler(fileHandler);
            
        } catch (SecurityException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  

        log.info("Test log.");  

        log.log(Level.ALL, "GIGIGI LOGGING");
    }*/
}
