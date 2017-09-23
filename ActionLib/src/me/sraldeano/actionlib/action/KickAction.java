package me.sraldeano.actionlib.action;

import me.sraldeano.actionlib.Action;
import me.sraldeano.actionlib.ActionLib;
import me.sraldeano.actionlib.util.TextUtil;

/**
 *
 * @author markelm
 */
public class KickAction extends Action{
    
    public String message = "You have been kicked from the server.";
    
    public KickAction() {
        super("Kick");
    }

    @Override
    public void onExecute() {
        getPlayer().kickPlayer(replaceText(message));
    }
    
}
