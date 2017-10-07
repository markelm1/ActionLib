package me.sraldeano.actionlib.action;

import me.sraldeano.actionlib.Action;
import me.sraldeano.actionlib.ActionLib;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class PlayerCommand extends Action {

    public String command = "";
    public boolean op = true;

    public PlayerCommand() {
        super("PlayerCommand");
    }

    @Override
    public void onExecute() {
        Server s = ActionLib.plugin.getServer();
        Player p = getPlayer();
        if (!p.isOp() && op) {
                p.setOp(true);
                s.dispatchCommand(p, command);
                p.setOp(false);
        }
        else {
            s.dispatchCommand(p, command);
        }
    }
}