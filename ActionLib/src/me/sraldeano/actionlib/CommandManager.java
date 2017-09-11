/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.sraldeano.actionlib;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
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
        if (args[0].equalsIgnoreCase("test")) {
            cs.sendMessage(ActionLib.plugin.getConfig().getConfigurationSection("actions").getMapList("KillAction") + "");
            for (Object x : ActionLib.plugin.getConfig().getConfigurationSection("actions").getList("KillPlayer")) {
                cs.sendMessage(x + "");
                cs.sendMessage(x.getClass().getName());
                if (x instanceof LinkedHashMap<?, ?>) {
                    System.out.println("fired");
                    for (String k : (Set<String>)((Map) x).keySet()) {
                        cs.sendMessage(k);
                    }
                    for (Object o : ((LinkedHashMap<?, ?>) x).values()) {
                        cs.sendMessage(o + "");
                        cs.sendMessage(o.getClass().getName());
                        for (String k : (Set<String>) ((Map) o).keySet()) {
                            cs.sendMessage(k);
                        }
                    }
                }
            }
        }
        return true;
    }
}
