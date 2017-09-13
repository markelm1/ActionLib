package me.sraldeano.actionlib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.sraldeano.actionlib.util.TextUtil;
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
    private String[] requiredVariables;
    private Map<String, Object> placeholders = new HashMap<>();

    public Action() {
    }
    
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
    public String replaceText(String text) {
        text = TextUtil.colored(text);
        for (String key : placeholders.keySet()) {
            text = text.replace(key, (String) getVariables().get(key));
        }
        
        return text;
    }
    
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
    }
    
    public void setPlaceholders(Map<String, String> placeholders) {
        
    }
    
    public String[] getRequiredVariables() {
        return requiredVariables;
    }
}