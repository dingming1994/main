package main.command;

import main.message.EnumMessage;
import main.modeinfo.EmptyModeInfo;
import main.response.Response;
import manager.ManagerHolder;
import manager.StateManager;
import manager.datamanager.UndoManager;
import manager.result.Result;

public class UndoCommand implements Command {

    private final UndoManager undoManager;
    private final StateManager stateManager;

    public UndoCommand(ManagerHolder managerHolder) {
        undoManager = managerHolder.getUndoManager();
        stateManager = managerHolder.getStateManager();
    }

    @Override
    public Response execute() {
        if (stateManager.canUndo()) {
            stateManager.beforeCommandExecutionUpdate();

            Result result = undoManager.undo();
            Response response = stateManager.update(result);
            return response;
        } else {
            EnumMessage message = EnumMessage.cannotExecuteCommand();
            EmptyModeInfo modeInfo = new EmptyModeInfo();
            return new Response(message, modeInfo);
        }
    }

}
