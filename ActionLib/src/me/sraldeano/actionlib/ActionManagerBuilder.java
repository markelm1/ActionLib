package me.sraldeano.actionlib;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

/**
 *
 * @author markelm
 */
public class ActionManagerBuilder {
    
    private Player player;
    private Action action;
    private Map<String, Object> settings;
    private Map<String, Object> variables;
    
    public ActionManagerBuilder(Player player) {
        this.player = player;
    }
    
    public ActionManagerBuilder setAction(String actionName) {
        this.action = ActionLib.getAction(actionName);
        return this;
    }
    
    public ActionManagerBuilder setAction(Action action) {
        this.action = action;
        return this;
    }
    
    public ActionManagerBuilder setAction(Class<? extends Action> action) {
        try {
            this.action = action.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ActionManagerBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }
    
    public ActionManagerBuilder setSettings(ConfigurationSection section) {
        this.settings = section.getValues(true);
        return this;
    }
    
    public ActionManagerBuilder setSettings(Map<String, Object> settings) {
        this.settings = settings;
        return this;
    }
    
    public ActionManagerBuilder setVariables(Map<String, Object> variables) {
        this.variables = variables;
        return this;
    }
    
    public Action build() {
        if (settings != null) {
            action.setSettings(settings);
        }
        if (variables != null) {
            action.setVariables(variables);
        }
        return action;
    }
    
    public void send() {
        build().execute(player);
    }
}
