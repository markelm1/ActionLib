package me.sraldeano.actionlib.action;

import me.sraldeano.actionlib.Action;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

/**
 *
 * @author markelm
 */
public class CancellEventAction extends Action{

    public CancellEventAction() {
        super("CancellEvent");
    }

    @Override
    public void onExecute() {
        Event e = (Event) getVariables().get("event");
        if (e instanceof Cancellable) {
            ((Cancellable) e).setCancelled(true);
        }
    }
    
}
