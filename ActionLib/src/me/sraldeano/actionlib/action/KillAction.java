package me.sraldeano.actionlib.action;

import me.sraldeano.actionlib.Action;

/**
 *
 * @author markelm
 */
public class KillAction extends Action {

    public KillAction() {
        super("Kill");
    }

    @Override
    public void onExecute() {
        getPlayer().setHealth(0.0D);
    }
    
    
}
