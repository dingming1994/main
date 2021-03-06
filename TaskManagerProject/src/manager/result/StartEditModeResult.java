package manager.result;

import main.command.TaskIdSet;

//@author A0119432L
public class StartEditModeResult implements Result {

    private final TaskIdSet taskIdSet;

    public StartEditModeResult(TaskIdSet taskIdSet){
        this.taskIdSet = taskIdSet;
    }
    
    @Override
    public Type getType() {
        return Type.EDIT_MODE_START;
    }

    public TaskIdSet getTaskIdSet(){
        return taskIdSet;
    }
}
