package manager.datamanager;

import data.TaskData;
import io.FileInputOutput;

public abstract class AbstractManager {
    private final FileInputOutput fileInputOutput;
    protected final TaskData taskData;
    
    public AbstractManager(FileInputOutput fileInputOutput, TaskData taskData) {
        this.fileInputOutput = fileInputOutput;
        this.taskData = taskData;
    }
    
    protected boolean readFromFile() {
        return fileInputOutput.read();
    }

    protected boolean writeToFile() {
        return fileInputOutput.write();
    }
}