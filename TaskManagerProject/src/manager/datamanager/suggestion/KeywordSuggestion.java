package manager.datamanager.suggestion;

public class KeywordSuggestion {
    private String keyword;
    private int distance;
    private int numberOfMatches;
    
    public KeywordSuggestion(String keyword, int distance) {
        this.keyword = keyword;
        this.distance = distance;
        this.numberOfMatches = 0;
    }
    
    public String getKeyword() {
        return keyword;
    }
    
    public int getDistance(){
        return distance;
    }
    
    public void setNumberOfMatches(int numberOfMatches) {
        this.numberOfMatches = numberOfMatches;
    }
    
    public int getNumberOfMatches() {
        return numberOfMatches;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        
        if (o == null) {
            return false;
        }
        
        if (getClass() != o.getClass()) {
            return false;
        }
        
        KeywordSuggestion keywordSuggestion = (KeywordSuggestion)o;
        return keyword.equals(keywordSuggestion.keyword);
    }
}