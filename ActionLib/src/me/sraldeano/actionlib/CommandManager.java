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
import me.sraldeano.actionlib.util.TextUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
/**
 *
 * @author markelm
 */
public class CommandManager implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String main, String[] args) {
        switch (args[0].toLowerCase()) {
            case "testaction" : {
                Player p = (Player) cs;
                ActionManager.sendAction(p, ActionLib.getAction(args[1]), new HashMap<String, Object>(), new HashMap<String, Object>());
            }
            case "about" : {
                String[] about = {"&6&m--------------&r&6[]&6&m---------------",
                "&6Created by: &3Sr_Aldeano",
                "&6Version: &3" + ActionLib.plugin.getDescription().getVersion(),
                "&6GitHub: &3github.com/Markelm16/ActionLib",
                "&6&m--------------&r&6[]&6&m---------------"
                };
                cs.sendMessage(TextUtil.colored(about));
            }
            case "reload" : {
                ActionLib.plugin.reloadConfig();
            }
        }
        return true;
    }
}
