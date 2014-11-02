package main.command.alias;

public interface IAliasStorageFileInputOutput {

    /**
     * @return Get the entire list of custom aliases.
     */
    public abstract AliasValuePair[] getAllCustomAliases();

    /**
     * @param aliases Overwrites the original set of custom aliases with these.
     * @return true if successful. If unsuccessful, no change will be made to
     * AliasStorage.
     */
    public abstract boolean setAllCustomAliases(AliasValuePair[] aliases);

}