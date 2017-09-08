/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.sraldeano.actionlib;

import java.util.HashMap;
import me.sraldeano.actionlib.ActionLib;
import me.sraldeano.actionlib.ActionManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.permissions.CommandPermissions;
/**
 *
 * @author markelm
 */
public class CommandManager implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String main, String[] args) {
        if (args[0].equalsIgnoreCase("testaction")) {
            Player p = (Player) cs;
            ActionManager.sendAction(p, ActionLib.getAction(args[1]), new HashMap<String, Object>(), new HashMap<String, Object>());
        }
        return true;
    }
}
