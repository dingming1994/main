package test.fuzzytest;

import java.util.ArrayList;
import java.util.Random;

import test.fuzzytest.KeywordLibrary.ListType;
import static test.fuzzytest.KeywordLibrary.ListType.*;

public class KeywordLibrary {
    
    private static final int RECURSE_PROBABILITY = 4;
    private static final boolean AGAIN = true;
    
    private Random rand;

    private ListType[] allListTypes;
    private ArrayList<ArrayList<KeywordToken>> libraryLists;
    
    public enum ListType {
        NONE,
        CONNECTOR,
        DATETIMECONNECTOR,
        COMMAND,
        DATETIME,
        ITEM,
        NUMBER,
        VALIDNUMBER,
        TASKID,
        VALIDTASKID,
        RANDOM,
        SYMBOL,
        COMMA,
        EDITKEYWORD,
        EDIT,
        DELETE,
        SEARCH,
        DETAILS,
        ADD,
        RETURN,
        QUIT,
        ALL
    }
    
    public KeywordLibrary(int seed) {
        rand = new Random(seed);
        createLists();
        addContent();
    }
    
    public String getRandom(ListType listType) {
        ArrayList<KeywordToken> list = getList(listType);
        return getRandomKeywordAndProcessRecursively(list, listType);
    }
    
    public String getRandom(ListType...listTypes) {
        if (listTypes.length == 1) {
            return getRandom(listTypes[0]);
        } else {
            ArrayList<KeywordToken> list = getRandomList(listTypes);
            return getRandomKeywordAndProcessRecursively(list, listTypes);
        }
    }

    protected String getRandomKeywordAndProcessRecursively(
            ArrayList<KeywordToken> list, ListType...listTypes) {
        
        KeywordToken keyword = getRandomElement(list);
        
        int next = rand.nextInt(RECURSE_PROBABILITY);
        if (next == 0) {
            if (keyword.again) {
                return keyword.token + " " + getRandom(listTypes);
            } else if (keyword.successor != null) {
                return keyword.token + " " + getRandom(keyword.successor);
            } else {
                return keyword.token;
            }
        } else {
            return keyword.token;
        }
    }
    
    private void addContent() {
        

        addToList("", NONE, DATETIMECONNECTOR, CONNECTOR, RANDOM);
        addToList("to", DATETIMECONNECTOR, CONNECTOR, TASKID, RANDOM);
        addToList("-", DATETIMECONNECTOR, RANDOM);
        addToList("until", DATETIMECONNECTOR, CONNECTOR);
        
        addToList("at", CONNECTOR, TASKID, RANDOM);
        addToList("from", CONNECTOR, RANDOM);
        addToList("after", CONNECTOR);
        addToList("before", CONNECTOR);
        
        addToList(AGAIN, "2014", DATETIME, RANDOM);
        addToList("29 oct 2014", DATETIME, RANDOM);
        addToList(AGAIN, "2pm", DATETIME, TASKID, VALIDTASKID, RANDOM);
        addToList(AGAIN, "3am", DATETIME, TASKID, VALIDTASKID);
        addToList(AGAIN, "0pm", DATETIME, TASKID, VALIDTASKID);
        addToList(AGAIN, "12am", DATETIME, TASKID);
        addToList(AGAIN, "4 pm", DATETIME, NUMBER, RANDOM);
        addToList(AGAIN, "12 am", DATETIME);
        addToList(AGAIN, "13 am", DATETIME);
        addToList(AGAIN, "13 pm", DATETIME);
        addToList(AGAIN, "1300", DATETIME, NUMBER);
        addToList(AGAIN, "1300h", DATETIME. NUMBER);
        addToList(AGAIN, "12-am", DATETIME);
        addToList(AGAIN, "12-14pm", DATETIME);
        addToList(AGAIN, "tuesday", DATETIME);
        addToList(AGAIN, "oct", DATETIME, RANDOM);
        addToList(AGAIN, "may", DATETIME, TASKID, CONNECTOR);
        addToList(AGAIN, "mon", DATETIME, TASKID, RANDOM);
        addToList(AGAIN, "sun", DATETIME, TASKID);
        addToList(AGAIN, "sat", DATETIME, TASKID);
        addToList(AGAIN, "today", DATETIME);
        addToList(AGAIN, "tomorrow", DATETIME);
        addToList(AGAIN, "yesterday", DATETIME);
        addToList(AGAIN, "next week", DATETIME);
        addToList(AGAIN, "last month", DATETIME);
        addToList(AGAIN, "tonight", DATETIME);
        addToList(AGAIN, "day", DATETIME, TASKID);
        addToList(AGAIN, "lunchtime", DATETIME);
        addToList(AGAIN, "noon", DATETIME);
        addToList(AGAIN, "midnight", DATETIME);
        addToList(AGAIN, "evening", DATETIME);
        addToList(AGAIN, "morning", DATETIME);
        addToList(AGAIN, "this week", DATETIME);
        addToList(AGAIN, "next tuesday", DATETIME);

        addToList("+high", ITEM);
        addToList("+low", ITEM);
        addToList("+medium", ITEM);
        addToList("+med", ITEM);
        addToList("#taskline", ITEM);
        addToList("#orange", ITEM);
        addToList("#tag", ITEM);
        addToList("##", ITEM);
        addToList("done", ITEM);
        addToList("undone", ITEM);
        addToList("++high", ITEM);

        addToList("show", COMMAND, SEARCH);
        addToList("search", COMMAND, SEARCH);
        addToList("ls", COMMAND, SEARCH, RANDOM);
        addToList("set", COMMAND, EDIT, TASKID, RANDOM);
        addToList("edit", COMMAND, EDIT);
        addToList("change", COMMAND, EDIT);
        addToList("modify", COMMAND, EDIT);
        addToList("remove", COMMAND, DELETE);
        addToList("rm", COMMAND, DELETE);
        addToList("detail", COMMAND, DETAILS);
        addToList("details", COMMAND, DETAILS);
        addToList("return", COMMAND, RETURN);
        addToList("back", COMMAND, RETURN);
        addToList("quit", COMMAND, QUIT);
        addToList("exit", COMMAND, QUIT);

        addToList(AGAIN, "tag", COMMAND, EDIT, EDITKEYWORD, TASKID);
        addToList("add", COMMAND, ADD, EDITKEYWORD, TASKID, RANDOM);
        addToList("delete", COMMAND, DELETE, EDITKEYWORD, RANDOM);
        addToList("del", COMMAND, DELETE, EDITKEYWORD, TASKID);

        addToList(AGAIN, "clear", EDITKEYWORD);
        addToList("time", EDITKEYWORD);
        addToList("name", EDITKEYWORD, RANDOM);
        addToList("date", EDITKEYWORD, RANDOM);
        addToList("datetime", EDITKEYWORD);
        addToList("priority", EDITKEYWORD);
        addToList("status", EDITKEYWORD);
        addToList("edit", EDITKEYWORD);
        addToList("tag add", EDITKEYWORD);
        addToList("tag del", EDITKEYWORD);
        
        addToList(COMMA, "1", NUMBER, VALIDNUMBER);
        addToList(COMMA, "2", NUMBER, VALIDNUMBER);
        addToList(COMMA, "-1", NUMBER);
        addToList(COMMA, "-34", NUMBER);
        addToList(COMMA, "431", NUMBER);
        addToList(COMMA, "4,31", NUMBER);
        addToList(COMMA, "67", NUMBER);
        addToList(COMMA, "5-1", NUMBER);
        addToList(COMMA, "5--6", NUMBER);
        addToList(COMMA, "2-4,3", NUMBER, VALIDNUMBER);
        addToList(COMMA, "5", NUMBER, VALIDNUMBER);
        addToList(COMMA, "1,3", NUMBER, VALIDNUMBER);
        addToList(COMMA, "2-5", NUMBER, VALIDNUMBER, RANDOM);
        addToList(COMMA, "8, 3, 1", NUMBER, VALIDNUMBER);
        addToList(COMMA, "1-5, 6", NUMBER, VALIDNUMBER, RANDOM);
        addToList(COMMA, "3.534", NUMBER);
        addToList(COMMA, "123(", NUMBER);
        addToList(COMMA, "^", NUMBER, SYMBOL);
        addToList(COMMA, "#", NUMBER, SYMBOL);
        addToList(COMMA, "@2", NUMBER, RANDOM);
        addToList(COMMA, "+341", NUMBER, RANDOM);
        addToList(COMMA, "%123", NUMBER, TASKID);
        addToList(COMMA, "-84", NUMBER);
        addToList(COMMA, "-pi", NUMBER, TASKID);
        addToList(COMMA, "sqrt(2)", NUMBER);
        addToList(COMMA, "255", NUMBER);
        addToList(COMMA, "2147483647", NUMBER);
        addToList(COMMA, "2147483648", NUMBER);
        addToList(COMMA, "-2147483648", NUMBER);
        addToList(COMMA, "999999999999999", NUMBER);
        addToList(COMMA, "0", NUMBER);
        addToList(COMMA, "$4", NUMBER);
        addToList(COMMA, "i", NUMBER);
        addToList(COMMA, "", NUMBER);
        addToList(COMMA, "000", NUMBER, RANDOM);
        
        for (int i = 5; i < 30; i++) {
            addToList(COMMA, i + "", VALIDNUMBER);
            addToList(COMMA, i + "-" + (i+5), VALIDNUMBER);
        }

        addToList(COMMA, "ab9", TASKID, VALIDTASKID);
        addToList(COMMA, "9ab", TASKID, VALIDTASKID);
        addToList(COMMA, "1aa", TASKID, VALIDTASKID);
        addToList(COMMA, "aa0", TASKID, VALIDTASKID);
        addToList(COMMA, "po9", TASKID, VALIDTASKID);
        addToList(COMMA, "c93", TASKID);
        addToList(COMMA, "999", TASKID, RANDOM);
        addToList(COMMA, "aca", TASKID);
        addToList(COMMA, "12", NUMBER, TASKID);
        addToList(COMMA, "oct", DATETIME);
        addToList(COMMA, "op2", TASKID, VALIDTASKID, RANDOM);
        addToList(COMMA, "9sds", TASKID);
        addToList(COMMA, "s9asd", TASKID);
        addToList(COMMA, "4rea", TASKID);
        addToList(COMMA, "%53", TASKID);
        addToList(COMMA, "%%%", TASKID, RANDOM);
        addToList(COMMA, "#ta3", TASKID);

        addToList("a", RANDOM);
        addToList("%", RANDOM, SYMBOL);
        addToList("aj3", RANDOM);
        addToList("T##", RANDOM);
        addToList("\"test\"", RANDOM);
        addToList("\"", RANDOM, SYMBOL);
        addToList("\n", RANDOM);
        addToList("five", RANDOM);
        addToList(AGAIN, "    ", RANDOM);
        addToList("___", RANDOM);
        addToList("orange", RANDOM, ITEM);
        addToList("apple", RANDOM, ITEM);
        addToList("pear pear", RANDOM, ITEM);
        addToList("-", RANDOM, SYMBOL, CONNECTOR);
        addToList(",", RANDOM, SYMBOL, CONNECTOR, COMMA);
        addToList("+", RANDOM, SYMBOL);
        addToList(">", RANDOM, SYMBOL);
        addToList("\\", RANDOM, SYMBOL);
    }

    private KeywordToken getRandomElement(ArrayList<KeywordToken> list) {
        int next = rand.nextInt(list.size());
        return list.get(next);
    }

    private ArrayList<KeywordToken> getRandomList(ListType...listTypes) {
        int next = rand.nextInt(listTypes.length);
        return getList(listTypes[next]);
    }

    private ArrayList<KeywordToken> getList(ListType listType) {
        if (listType == ListType.ALL) {
            return getRandomList(allListTypes);
        } else {
            int index = listType.ordinal();
            return libraryLists.get(index);
        }
    }
    
    private void createLists() {
        int nTypes = ListType.values().length;
        
        libraryLists = new ArrayList<>();
        allListTypes = new ListType[nTypes - 1];
        
        for (int i = 0; i < nTypes; i++) {
            ListType currentListType = ListType.values()[i];
            
            if (currentListType != ListType.ALL) {
                libraryLists.add(new ArrayList<KeywordToken>());
                allListTypes[i] = currentListType;
            }
        }
    }
    
    private void addToList(ListType successor, String token, ListType...listTypes) {
        for (ListType listType : listTypes) {
            ArrayList<KeywordToken> list = getList(listType);
            list.add(new KeywordToken(token, successor));
        }
    }
    
    private void addToList(boolean again, String token, ListType...listTypes) {
        for (ListType listType : listTypes) {
            ArrayList<KeywordToken> list = getList(listType);
            list.add(new KeywordToken(token, again));
        }
    }
    
    private void addToList(String token, ListType...listTypes) {
        for (ListType listType : listTypes) {
            ArrayList<KeywordToken> list = getList(listType);
            list.add(new KeywordToken(token));
        }
    }
}

class KeywordToken {
    public final String token;
    
    /**
     * If again is true, there is a chance that another token of the same
     * keyword type will be added again after this keyword.
     */
    public final boolean again;
    public final ListType successor;

    public KeywordToken(String token) {
        this.token = token;
        this.again = false;
        this.successor = null;
    }
    
    public KeywordToken(String token, boolean repeat) {
        this.token = token;
        this.again = repeat;
        this.successor = null;
    }
    
    public KeywordToken(String token, ListType successor) {
        this.token = token;
        this.again = false;
        this.successor = successor;
    }
}
