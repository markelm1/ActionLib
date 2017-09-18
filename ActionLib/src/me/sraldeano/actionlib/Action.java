package me.sraldeano.actionlib;

import java.util.HashMap;
import java.util.Map;

import me.sraldeano.actionlib.util.TextUtil;
import org.bukkit.entity.Player;

/**
 * Object that represents an Action
 * @author SrAldeano
 */
public abstract class Action {
    
    private String name;
    private Player player;
    private Map<String, Object> settings;
    private Map<String, Object> variables;
    private String[] requiredVariables;
    private Map<String, Object> placeholders = new HashMap<>();

    
    public Action(String name) {
        this.name = name;
    }
    
    public Action(String name, String... needVariables) {
        this.name = name;
        this.requiredVariables = needVariables;
    }
    
    public final String getName() {
        return name;
    }

    /**
     * Required on creating a custom Action
     * This will be fired when the action is executed
     */
    public abstract void onExecute();

    public Player getPlayer() {
        return player;
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

    /**
     * Color a text and replace all variables.
     * @param text to replace
     * @return the text but it is colored and all Placeholders has their values
     */
    public String replaceText(String text) {
        if (!placeholders.containsKey("%player%")) {
            placeholders.putAll(TextUtil.getPlayerVariables(getPlayer()));
        }

        text = TextUtil.colored(text);
        if (text.contains("%")) {
            for (String key : placeholders.keySet()) {
                text = text.replace(key, (String) getPlaceholders().get(key));
            }
        }
        
        return text;
    }

    /**
     * Execute this upgrade to a player
     * @param player the player to execute
     */

    public final void execute(Player player) {
        if (requiredVariables != null) {
            for (String var : getRequiredVariables()) {
                if (!getVariables().containsKey(var)) {
                    return;
                }
            }
        }
        this.player = player;
        onExecute();
        this.player = null;
    }
    
    public void addPlaceholders(Map<String, String> placeholders) {
        this.placeholders.putAll(placeholders);
    }
    
    public String[] getRequiredVariables() {
        return requiredVariables;
    }

    public Map<String, Object> getPlaceholders() {
        return placeholders;
    }
}