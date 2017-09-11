package me.sraldeano.actionlib;

import java.util.Map;
import org.bukkit.entity.Player;

/**
 *
 * @author SrAldeano
 */
public abstract class Action {
    
    private String name;
    private Player player;
    private Map<String, Object> settings;
    private Map<String, Object> variables;
    
    public Action(String name) {
        this.name = name;
    }
    
    public final String getName() {
        return name;
    }
    
    public abstract void onExecute();
    
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setSettings(Map<String, Object> settings) {
        this.settings = settings;
    }

    public Map<String, Object> getSettings() {
        return settings;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }
    
    public void reset() {
        name = null;
        player = null;
        settings = null;
        variables = null;
    }
}
