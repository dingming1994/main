package taskline;

import io.AliasFileInputOutput;
import io.FileInputOutput;
import io.IFileInputOutput;

import java.io.IOException;

import main.MainController;
import main.command.alias.AliasStorage;
import manager.ManagerHolder;

import org.fusesource.jansi.AnsiConsole;

import ui.UIDisplay;
import data.AutoCompleteDictionary;
import data.TaskData;

/**
 * Does the main initialization of the program structure.
 *
 */
//@author A0065475X
public class Taskline {

    public static void main(String[] args) throws IOException {
        AnsiConsole.systemInstall();
        TasklineLogger.setupLogger();

        String fileName = "tasks.txt";
        String aliasFileName = "alias.txt";

        AutoCompleteDictionary autoCompleteDictionary =
                new AutoCompleteDictionary();
        
        AliasStorage aliasStorage = new AliasStorage();
        IFileInputOutput aliasFileInputOutput = new AliasFileInputOutput(
                aliasStorage, aliasFileName, autoCompleteDictionary);

        TaskData taskData = new TaskData();
        IFileInputOutput fileInputOutput =
                new FileInputOutput(taskData, fileName);
        
        ManagerHolder managerHolder = new ManagerHolder(taskData,
                fileInputOutput, aliasStorage, aliasFileInputOutput);
        MainController mainController = new MainController(managerHolder,
                aliasStorage, aliasFileInputOutput);
        
        UIDisplay uiDisplay = new UIDisplay(mainController);

        startCommandLoop(uiDisplay);
        TasklineLogger.closeLoggerFileHandler();
    }

    private static void startCommandLoop(UIDisplay uiDisplay) {
        while (!uiDisplay.isReadyToExit()) {
            uiDisplay.commandLoopIteration();
        }
    }
}
