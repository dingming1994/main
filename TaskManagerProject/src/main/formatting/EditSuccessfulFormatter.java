package main.formatting;

import java.util.ArrayList;

import main.formatting.utility.DetailsUtility;
import main.message.EditSuccessfulMessage;
import main.message.EditSuccessfulMessage.Field;
import data.TaskId;
import data.taskinfo.TaskInfo;

//@author A0113011L
/**
 * <pre>
 * Formatter for the EditSuccessfulMessage.
 * Format example:
 * Task name changed.
 * 
 * Task [a9f]
 *    Name: eat apples for dinner
 *    Time: 17:30 (PM)
 *    Date: Tuesday, 17 Feb 2015
 *    Tags: taskline, food
 *    Priority: High
 *    Description: I will die if I don't eat my apples!
 * </pre>
 */
public class EditSuccessfulFormatter {
    private final static String CHANGED_NAME = "Task name changed.";
    private final static String CHANGED_TIME = "Time changed.";
    private final static String CHANGED_PRIORITY = "Priority changed.";
    private final static String ADDED_TAG = "Tag added.";
    private final static String DELETED_TAG = "Tag deleted.";
    private final static String CHANGED_DETAILS = "Details changed.";
    private final static String CHANGED_STATUS = "Status changed.";
    
    private String getChangedFieldString(
            EditSuccessfulMessage.Field changedField) {
        String changedFieldString = "";
        switch(changedField) {
            case NAME :
                changedFieldString = CHANGED_NAME;
                break;
            case PRIORITY :
                changedFieldString = CHANGED_PRIORITY;
                break;
            case TIME :
                changedFieldString = CHANGED_TIME;
                break;
            case TAGS_ADD :
                changedFieldString = ADDED_TAG;
                break;
            case TAGS_DELETE :
                changedFieldString = DELETED_TAG;
                break;
            case DETAILS :
                changedFieldString = CHANGED_DETAILS;
            case STATUS : 
                changedFieldString = CHANGED_STATUS;
        }
        return changedFieldString;
    }
    
    
    private ArrayList<String> formatToArray(EditSuccessfulMessage message) {
        ArrayList<String> result = new ArrayList<String>();
        
        for (Field field : message.getChangedField()) {
            result.add(getChangedFieldString(field));
        }
        
        TaskInfo[] tasks = message.getTask();
        TaskId[] taskIds = message.getTaskId();
        
        DetailsUtility detailsUtility = new DetailsUtility();
        
        for (int i = 0; i < tasks.length; i++) {
            ArrayList<String> detailsLines =
                    detailsUtility.formatToArray(tasks[i], taskIds[i]);
            
            result.add("");
            result.addAll(detailsLines);
        }
        
        result.add("");
        return result;
    }
    
    private String arrayListToString(ArrayList<String> lines) {
        StringBuilder builder = new StringBuilder("");
        for (String line : lines) {
            builder.append(line);
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }
    
    public String format(EditSuccessfulMessage message) {
        return arrayListToString(formatToArray(message));
    }
}
