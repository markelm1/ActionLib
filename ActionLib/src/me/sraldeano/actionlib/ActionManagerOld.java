package me.sraldeano.actionlib;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.sraldeano.actionlib.util.ReflectionUtil;
import me.sraldeano.actionlib.util.Util;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class ActionManagerOld {
    
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
        action.setSettings(settings);
        action.setVariables(variables);
        action.execute(player);
        action.reset();
    }
    
    public static void sendAction(Player player, String actionName, ConfigurationSection settings) {
        Action action = null;
        try {
            action = ActionLib.getActionClass(actionName).newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ActionManagerOld.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        sendAction(player, action, settings.getValues(false), null);
    }
    
    /**
     * Register your action to the plugin.
     * You don't need to do this if the Action is loaded from the addons folder.
     * @param action Action to be registered
     * @param asAddon true if you want to register the Action as external addon
     */
    @Deprecated
    public static void registerAction(Action action, boolean asAddon) {
        if (asAddon) {
            ActionLib.addonActions.add(action);
        }
        ActionLib.actions.add(action);
        ActionLib.actionMap.put(action.getName(), action);
        ActionLib.actionClassMap.put(action.getName(), action.getClass());
    }
    


}
