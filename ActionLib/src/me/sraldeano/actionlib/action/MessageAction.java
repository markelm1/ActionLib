package me.sraldeano.actionlib.action;

import me.sraldeano.actionlib.Action;

/**
 *
 * @author markelm
 */
public class MessageAction extends Action {

    public MessageAction() {
        super("Message");
    }

    @Override
    public void onExecute() {
        getPlayer().sendMessage("Hijoeputa");
    }
    
}
