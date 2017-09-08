/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.sraldeano.actionlib;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class ActionManager {
    
    public static boolean hasPermission(Player p, ConfigurationSection actionSection) {
        return p.hasPermission(actionSection.getString("permission"));
    }
    /**
     * Send an Action to a player. The action will be performed on the Player.
     * @param player the player to send the Action
     * @param action the Action to send
     * @param settings a map with settings
     * @param variables variables are needed on some Actions. In the 'Effect' action, you need to add a location variable
     */
    public static void sendAction(Player player, Action action, Map<String, Object> settings, HashMap<String, Object> variables) {
        action.setPlayer(player);
        action.setSettings(settings);
        action.setVariables(variables);
        action.onExecute();
        action.reset();
    }
    
    public static void sendAction(Player player, ConfigurationSection settings) {
    }
    
    public static void sendActions(Player player, String settings) {
        
    }
    
    /**
     * Register your action to the plugin.
     * You don't need to do this if the Action is loaded from the addons folder.
     * @param action Action to be registered
     * @param asAddon true if you want to register the Action as external addon
     */
    public static void registerAction(Action action, boolean asAddon) {
        if (asAddon) {
            ActionLib.addonActions.add(action);
        }
        else {
            ActionLib.actions.add(action);
        }
        ActionLib.actionMap.put(action.getName(), action);
    }
}
