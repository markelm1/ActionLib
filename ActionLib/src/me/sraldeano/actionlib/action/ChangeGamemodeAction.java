package me.sraldeano.actionlib.action;

import me.sraldeano.actionlib.Action;
import org.bukkit.GameMode;

/**
 *
 * @author markelm
 */
public class ChangeGamemodeAction extends Action{

    public String gamemode;
    
    public ChangeGamemodeAction() {
        super("ChangeGamemode");
    }

    @Override
    public void onExecute() {
        getPlayer().setGameMode(GameMode.valueOf(gamemode.toUpperCase()));
    }
}
