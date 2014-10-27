package main.formatting;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import main.message.FreeDaySearchMessage;

public class FreeDaySearchFormatter {
    private final static String LINE_HEADER = 
            "You do not have any planned tasks on these days:" + 
            System.lineSeparator();
    private final static String LINE_NOTASK = 
            "You do not have any planned tasks between %1$s and %2$s." + 
            System.lineSeparator();
    private final static String LINE_DATE = 
            "- %1$s" + System.lineSeparator();
    
    private final static String FORMAT_SHORTDATE = 
            "d MMMM y";
    private final static String FORMAT_DATE = 
            "E, d MMMM y";
    
    private String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DATE);
        return formatter.format(date);
    }
    
    private String formatShortDate(LocalDate date) {
        DateTimeFormatter formatter = 
                DateTimeFormatter.ofPattern(FORMAT_SHORTDATE);
        return formatter.format(date);
    }
    
    private String getAllFree(LocalDate startDate, LocalDate endDate) {
        return String.format(LINE_NOTASK, 
                formatShortDate(startDate), formatShortDate(endDate));
    }
    
    public String format(FreeDaySearchMessage message) {
        StringBuilder result = new StringBuilder();
        
        if (message.getFirstBusyDate() == null) {
            return getAllFree(message.getSearchStartDate(), message.getSearchEndDate());
        } else {
            ArrayList<LocalDate> freeDates = message.getFreeDateList();

            result.append(LINE_HEADER);
            LocalDate current = message.getSearchStartDate();
            do {
                if (current.compareTo(message.getFirstBusyDate()) < 0) {
                    result.append(String.format(LINE_DATE, formatDate(current)));
                } else if (current.compareTo(message.getLastBusyDate()) > 0) {
                    result.append(String.format(LINE_DATE, formatDate(current)));
                } else if (freeDates.contains(current)) {
                    result.append(String.format(LINE_DATE, formatDate(current)));
                }
            } while (!current.equals(message.getSearchEndDate()));
            
            return result.toString();
        }
    }
}