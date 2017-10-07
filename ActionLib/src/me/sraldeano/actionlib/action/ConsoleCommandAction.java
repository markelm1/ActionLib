package me.sraldeano.actionlib.action;

import me.sraldeano.actionlib.Action;
import me.sraldeano.actionlib.ActionLib;
import org.bukkit.Bukkit;

public class ConsoleCommandAction extends Action {

    public String command = "";

    public ConsoleCommandAction() {
        super("ServerCommand");
    }

    @Override
    public void onExecute() {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
    }
}
