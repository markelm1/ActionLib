package me.sraldeano.actionlib.action;

import me.sraldeano.actionlib.Action;

public class ForceChatAction extends Action{

    public String message = "Hello!";

    public ForceChatAction() {
        super("ForceChat");
    }

    @Override
    public void onExecute() {
        getPlayer().chat(replaceText(message));
    }
}
