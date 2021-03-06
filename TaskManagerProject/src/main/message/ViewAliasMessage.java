package main.message;

import main.command.alias.AliasValuePair;

//@author A0065475X
public class ViewAliasMessage implements Message {

    private AliasValuePair[] aliases;

    public ViewAliasMessage(AliasValuePair[] aliases) {
        this.aliases = aliases;
    }

    @Override
    public Type getType() {
        return Type.VIEW_ALIAS;
    }
    
    public AliasValuePair[] getAliases() {
        return aliases;
    }

}
