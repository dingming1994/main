package main.formatting;

import main.message.EnumMessage;

public class EnumFormatter {
    
    private final static String MESSAGE_CANNOT_EXECUTE = 
            "Sorry, Taskline could not execute your command." +
            System.lineSeparator();
    private final static String MESSAGE_ADD_FAILED =
            "Add failed. Please try again." +
            System.lineSeparator();
    private final static String MESSAGE_EDIT_FAILED =
            "Edit failed. Please try again." +
            System.lineSeparator();
    private final static String MESSAGE_DELETE_FAILED =
            "Delete failed. Please try again." +
            System.lineSeparator();
    private final static String MESSAGE_EMPTY = "";
    
    public String format(EnumMessage message) {
        String formattedResult;
        switch(message.getMessageType()) {
            case CANNOT_EXECUTE_COMMAND :
                formattedResult = MESSAGE_CANNOT_EXECUTE;
            case EMPTY_STRING :
                formattedResult = MESSAGE_EMPTY;
            case ADD_FAILED :
                formattedResult = MESSAGE_ADD_FAILED;
            case EDIT_FAILED :
                formattedResult = MESSAGE_EDIT_FAILED;
            case DELETE_FAILED :
                formattedResult = MESSAGE_DELETE_FAILED;
            default :
                formattedResult = "";
        }
        return formattedResult;
    }
}
