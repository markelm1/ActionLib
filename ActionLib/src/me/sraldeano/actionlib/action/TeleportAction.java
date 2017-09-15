package me.sraldeano.actionlib.action;
import me.sraldeano.actionlib.Action;
import me.sraldeano.actionlib.util.Util;

public class TeleportAction extends Action {

    public String location = null;

    public TeleportAction() {
        super("Teleport");
    }

    @Override
    public void onExecute() {
        getPlayer().teleport(Util.locationBuilder(location));
    }
}
