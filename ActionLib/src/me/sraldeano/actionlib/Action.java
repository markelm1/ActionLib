package me.sraldeano.actionlib;

import me.sraldeano.actionlib.util.TextUtil;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * Object that represents an Action
 * @author SrAldeano
 */
public abstract class Action {
    
    private String name;
    private Player player;
    private Map<String, Object> variables;
    private String[] requiredVariables;

    
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

    public Map<String, Object> getVariables() {
        return variables;
    }

    /**
     * Color a text and replace all variables.
     * @param text to replace
     * @return the text but it is colored and all Placeholders has their values
     */
    public String replaceText(String text) {
        Map<String, String> placeholders = TextUtil.getPlayerVariables(getPlayer());
        text = TextUtil.colored(text);
        if (text.contains("%")) {
            for (String key : placeholders.keySet()) {
                text = text.replace(key, (String) placeholders.get(key));
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
                    try {
                        throw new Exception("The action does not have the required variable '" + var + "'");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        this.player = player;
        onExecute();
        this.player = null;
    }

    public final void execute(Player player, Map<String, Object> variables) {
        this.variables = variables;
        execute(player);
        this.variables = null;
    }
    
    public String[] getRequiredVariables() {
        return requiredVariables;
    }
}