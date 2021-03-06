package main.command.alias;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

import main.command.AddCommand;
import main.command.AliasCommand;
import main.command.AliasDeleteCommand;
import main.command.ArgumentCommand;
import main.command.BackCommand;
import main.command.Command;
import main.command.DeleteCommand;
import main.command.DetailsCommand;
import main.command.EditCommand;
import main.command.EditCommand.ParseType;
import main.command.ExitCommand;
import main.command.FreeDaySearchCommand;
import main.command.FreeTimeSearchCommand;
import main.command.RedoCommand;
import main.command.ReportCommand;
import main.command.SearchCommand;
import main.command.UndoCommand;
import main.command.ViewAliasCommand;
import manager.ManagerHolder;


//@author A0065475X
public class AliasStorage implements IAliasStorage, IAliasStorageFileInputOutput {
    // Variable string: \$ (String replaced with user's argument when aliased)
    public static final String VARIABLE_STRING = "\\$";

    private HashSet<String> unoverridableStringSet;
    private HashMap<String, BiFunction<String, ManagerHolder, Command>> defaultMap;
    private HashMap<String, String> customMap;

    public AliasStorage() {
        defaultMap = new HashMap<>();
        customMap = new HashMap<>();
        unoverridableStringSet = new HashSet<>();

        initialiseUnoverriableStrings();
        initialiseDefaultCommands();
    }

    /* (non-Javadoc)
     * @see main.command.alias.IAliasStorage#getDefaultCommand(java.lang.String)
     */
    @Override
    public BiFunction<String, ManagerHolder, Command> getDefaultCommand(
            String commandString) {

        BiFunction<String, ManagerHolder, Command> makeCommandFunction;

        makeCommandFunction = defaultMap.get(commandString);
        if (makeCommandFunction == null) {
            return defaultMakeCommand(commandString);
        } else {
            return makeCommandFunction;
        }
    }

    /* (non-Javadoc)
     * @see main.command.alias.IAliasStorage#getCustomCommand(java.lang.String)
     */
    @Override
    public String getCustomCommand(String commandString) {
        return customMap.get(commandString);
    }

    /* (non-Javadoc)
     * @see main.command.alias.IAliasStorage#createCustomCommand(java.lang.String, java.lang.String)
     */
    @Override
    public String createCustomCommand(String alias, String replacement) {
        assert canOverride(alias);

        String[] keywords = replacement.split(" ", 2);
        String value;
        if (keywords.length == 1) {
            value = keywords[0] + " " + VARIABLE_STRING;
        } else {
            value = replacement;
        }

        put(alias, value);
        return value;
    }
    
    /* (non-Javadoc)
     * @see main.command.alias.IAliasStorage#canOverride(java.lang.String)
     */
    @Override
    public boolean canOverride(String alias) {
        return !unoverridableStringSet.contains(alias);
    }
    
    /* (non-Javadoc)
     * @see main.command.alias.IAliasStorage#deleteCustomCommand(java.lang.String)
     */
    @Override
    public String deleteCustomCommand(String alias) {
        return customMap.remove(alias);
    }

    
    /* (non-Javadoc)
     * @see main.command.alias.IAliasStorage#isAlreadyBinded(java.lang.String)
     */
    @Override
    public boolean isAlreadyBinded(String alias) {
        boolean alreadyBinded = customMap.containsKey(alias) ||
                defaultMap.containsKey(alias);        
        return alreadyBinded;
    }
    
    /* (non-Javadoc)
     * @see main.command.alias.IAliasStorageFileInputOutput#getAllCustomAliases()
     */
    @Override
    public AliasValuePair[] getAllCustomAliases() {
        AliasValuePair[] aliases = new AliasValuePair[customMap.size()];
        Set<Map.Entry<String, String>> entrySet = customMap.entrySet();

        int index = 0;
        for (Map.Entry<String, String> entry : entrySet) {
            String alias = entry.getKey();
            String value = entry.getValue();
            aliases[index] = new AliasValuePair(alias, value);
            index++;
        }
        
        return aliases;
    }
    
    /* (non-Javadoc)
     * @see main.command.alias.IAliasStorageFileInputOutput#setAllCustomAliases(main.command.alias.AliasValuePair[])
     */
    @Override
    public boolean setAllCustomAliases(AliasValuePair[] aliases) {
        HashMap<String, String> backupCustomMap = customMap;
        customMap = new HashMap<>();

        boolean successful = tryPutAllCustomAliases(aliases);
        
        if (successful) {
            return true;
        } else {
            customMap = backupCustomMap;
            return false;
        }
    }


    @Override
    public String[] getAllBindedStrings() {
        int totalSize = customMap.size() + defaultMap.size();
        HashSet<String> stringSet = new HashSet<>(totalSize);
        
        Set<String> customSet = customMap.keySet();
        Set<String> defaultSet = defaultMap.keySet();

        stringSet.addAll(customSet);
        stringSet.addAll(defaultSet);
        
        return stringSet.toArray(new String[0]);
    }

    private boolean tryPutAllCustomAliases(AliasValuePair[] aliases) {
        for (AliasValuePair aliasValuePair : aliases) {
            String alias = aliasValuePair.alias;
            String value = aliasValuePair.value;
            
            if (canOverride(alias)) {
                put(alias, value);
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the default command that is executed when the command string fits
     * none of the other commands.
     * @param input the first token of the input string.
     * @return an ArgumentCommand, which is the default command.
     */
    private BiFunction<String, ManagerHolder, Command> defaultMakeCommand(
            String input) {

        return (args, managerHolder) ->
                new ArgumentCommand(input + " " + args, managerHolder);
    }

    
    /**
     * Initialise all the default commands in taskline here.
     */
    private void initialiseDefaultCommands() {

        defineDefaultCommands(
                (args, managerHolder) -> new AddCommand(args, managerHolder),
                "add", "create");

        defineDefaultCommands(
                (args, managerHolder) -> new SearchCommand(args, managerHolder),
                "show", "search", "list", "view", "tasks");

        defineDefaultCommands(
                (args, managerHolder) -> new EditCommand(args, managerHolder),
                "edit", "set", "modify", "change");

        defineDefaultCommands(
                (args, managerHolder) ->
                new EditCommand(args, managerHolder, ParseType.MARK),
                "mark", "done");

        defineDefaultCommands(
                (args, managerHolder) ->
                new EditCommand(args, managerHolder, ParseType.UNMARK),
                "unmark");

        defineDefaultCommands(
                (args, managerHolder) ->
                new EditCommand(args, managerHolder, ParseType.STATUS),
                "status");

        defineDefaultCommands(
                (args, managerHolder) ->
                new EditCommand(args, managerHolder, ParseType.PRIORITY),
                "priority");

        defineDefaultCommands(
                (args, managerHolder) ->
                new EditCommand(args, managerHolder, ParseType.URGENT),
                "urgent");

        defineDefaultCommands(
                (args, managerHolder) ->
                new EditCommand(args, managerHolder, ParseType.RESCHEDULE),
                "reschedule", "time", "date", "datetime");

        defineDefaultCommands(
                (args, managerHolder) ->
                new EditCommand(args, managerHolder, ParseType.RENAME),
                "rename", "name");

        defineDefaultCommands(
                (args, managerHolder) -> new UndoCommand(args, managerHolder),
                "undo");

        defineDefaultCommands(
                (args, managerHolder) -> new RedoCommand(args, managerHolder),
                "redo");

        defineDefaultCommands(
                (args, managerHolder) -> new ReportCommand(managerHolder),
                "report");

        defineDefaultCommands(
                (args, managerHolder) ->
                new FreeDaySearchCommand(args, managerHolder),
                "freeday", "freedays", "freedate", "freedates");

        defineDefaultCommands(
                (args, managerHolder) ->
                new FreeTimeSearchCommand(args, managerHolder),
                "freetime", "freeslot");

        defineDefaultCommands(
                (args, managerHolder) -> new DeleteCommand(args, managerHolder),
                "delete", "del", "remove", "rm");

        defineDefaultCommands(
                (args, managerHolder) -> new DetailsCommand(args, managerHolder),
                "detail", "details", "info");

        defineDefaultCommands(
                (args, managerHolder) -> new BackCommand(managerHolder),
                "back", "return");

        defineDefaultCommands(
                (args, managerHolder) -> new ExitCommand(managerHolder),
                "quit", "exit");

        defineDefaultCommands(
                (args, managerHolder) -> new AliasCommand(args, managerHolder),
                "alias", "custom", "bind");

        defineDefaultCommands(
                (args, managerHolder) -> new AliasDeleteCommand(args, managerHolder),
                "unalias", "unbind");

        defineDefaultCommands(
                (args, managerHolder) -> new ViewAliasCommand(managerHolder),
                "aliases", "viewalias", "viewaliases");

    }

    private void initialiseUnoverriableStrings() {
        unoverridableStringSet.add("custom");
        unoverridableStringSet.add("alias");
        unoverridableStringSet.add("unalias");
        unoverridableStringSet.add("bind");
        unoverridableStringSet.add("unbind");
    }

    private void defineDefaultCommands(
            BiFunction<String, ManagerHolder, Command> commandFunction,
            String... commandStrings) {

        for (String commandString : commandStrings) {
            assert !commandString.contains(" ") : "Error in command: [" +
                    commandString + "] - Command cannot have multiple words.";
            defaultMap.put(commandString, commandFunction);
        }
    }
    
    private void put(String alias, String value) {
        customMap.put(alias, value);
    }
}
