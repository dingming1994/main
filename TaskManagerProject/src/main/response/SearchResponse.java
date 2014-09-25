package main.response;

import data.taskinfo.TaskInfo;

public class SearchResponse implements Response {
    public final TaskInfo[] searchResults;  
    
    public SearchResponse(TaskInfo[] searchResults) {
        this.searchResults = searchResults;
    }

    @Override
    public Type getType() {
        return Type.SEARCH_RESULTS;
    }
}