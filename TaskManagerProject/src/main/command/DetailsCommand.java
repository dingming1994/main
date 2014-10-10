package main.command;

import manager.ManagerHolder;
import manager.StateManager;
import manager.datamanager.SearchManager;
import manager.result.Result;
import data.TaskId;

public class DetailsCommand extends Command {
    private final StateManager stateManager;
    private final SearchManager searchManager;
    
    private final TaskId taskId;

    public DetailsCommand(String args, ManagerHolder managerHolder) {
        super(managerHolder);
        stateManager = managerHolder.getStateManager();
        searchManager = managerHolder.getSearchManager();
        
        taskId = parse(args);
    }

    private TaskId parse(String args) {
        return parseTaskId(args);
    }

    @Override
    protected boolean isValidArguments() {
        return taskId != null;
    }

    @Override
    protected boolean isCommandAllowed() {
        return stateManager.canSearch();
    }

    @Override
    protected Result executeAction() {
        Result result = searchManager.details(taskId);
        return result;
    }

}
