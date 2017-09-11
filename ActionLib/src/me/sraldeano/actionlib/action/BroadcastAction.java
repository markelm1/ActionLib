package me.sraldeano.actionlib.action;

import me.sraldeano.actionlib.Action;
import me.sraldeano.actionlib.ActionLib;

/**
 *
 * @author markelm
 */
public class BroadcastAction extends Action{

    public String message = "Default broadcast message.";
    
    public BroadcastAction() {
        super("Broadcast");
    }

    @Override
    public void onExecute() {
        ActionLib.plugin.getServer().broadcastMessage(message);
    }
    
}
