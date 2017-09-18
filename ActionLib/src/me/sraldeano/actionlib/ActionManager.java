package me.sraldeano.actionlib;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

/**
 *
 * @author markelm
 */
public class ActionManager {
    
    private Player player;
    private Action action;
    private Map<String, Object> settings;
    private Map<String, Object> variables;
    private Map<String, String> placeholders;
    
    public ActionManager(Player player) {
        this.player = player;
    }
    
    public ActionManager setAction(String actionName) {
        this.action = ActionLib.getAction(actionName);
        return this;
    }
    
    public ActionManager setAction(Action action) {
        this.action = action;
        return this;
    }
    
    public ActionManager setAction(Class<? extends Action> action) {
        try {
            this.action = action.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ActionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }
    
    public ActionManager setSettings(ConfigurationSection section) {
        this.settings = section.getValues(true);
        return this;
    }
    
    public ActionManager setSettings(Map<String, Object> settings) {
        this.settings = settings;
        return this;
    }
    
    public ActionManager setVariables(Map<String, Object> variables) {
        this.variables = variables;
        return this;
    }

    public ActionManager withPlaceholders(Map<String, String> placeholders) {
        this.placeholders = placeholders;
        return this;
    }

    public Action build() {
        if (settings != null) {
            action.setSettings(settings);
        }
        if (variables != null) {
            action.setVariables(variables);
        }
        if (placeholders != null) {
            action.addPlaceholders(placeholders);
        }
        return action;
    }
    
    public void execute() {
        build().execute(player);
    }

    public static void sendActions(Player player, Configuration conf, String path) {

    }
}