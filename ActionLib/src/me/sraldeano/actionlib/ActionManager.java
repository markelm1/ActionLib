package me.sraldeano.actionlib;

import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.sraldeano.actionlib.util.ReflectionUtil;
import me.sraldeano.actionlib.util.Util;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

/**
 *
 * @author markelm
 */
public class ActionManager {
    
    private Player[] player;
    private Action action;
    private Map<String, Object> settings;
    private Map<String, Object> variables;

    /**
     * Create an ActionManager with one or more players
     * @param player
     */
    public ActionManager(Player... player) {
        this.player = player;
    }

    /**
     * Create an ActionManager with a list of players
     * @param players
     */
    public ActionManager(List<Player> players) {
        player = players.toArray(new Player[players.size()]);
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

    public Action build() {
        if (settings != null) {
            action.setSettings(settings);
        }
        if (variables != null) {
            action.setVariables(variables);
        }
        return action;
    }
    
    public void execute() {
        for (Player p : player) {
            build().execute(p);
        }
    }

    public static void sendActions(Player player, Configuration conf, String path) {
        for (Action action : buildActions(conf, path)) {
            action.execute(player);
        }
    }

    public static List<Action> buildActions(Configuration config, String path) {
        List<?> list = config.getList(path);
        ArrayList<Action> actions = new ArrayList<>();
        if (list == null) {
            Util.getLogger().severe("Cannot found list '" + path + "' in the configuration: " + config.getName());
            return null;
        }
        for (Object key : list) {
            Action action = null;
            if (key instanceof String) {
                String keyString = (String) key;
                if (keyString.startsWith("[") && keyString.endsWith("]") && !keyString.contains(" ")) {
                    action = ActionLib.getAction(keyString.replace("[", "").replace("]", ""));
                    actions.add(action);
                }
                else if (keyString.startsWith("[")) {
                    String[] splited = keyString.split(" ", 2);
                    String actionName = splited[0].replace("[", "").replace("]", "");
                    System.out.println(actionName);
                    action = ActionLib.getAction(actionName);
                    Field[] fields = action.getClass().getFields();
                    ReflectionUtil.setField(fields[0], splited[1], action);
                    actions.add(action);
                }
                else if (keyString.startsWith("{")) {
                    actions.addAll(buildDefaultAction(keyString));
                }
                else {
                    Util.getLogger().severe("The list '" + path + "' in the configuration " + config.getName() + " contains a not valid format string '" + keyString + "'");
                }
            }
            else if (key instanceof Map) {
                Map map = (Map) key;
                String actionStr = null;
                Map secondMap = null;
                for (String k : (Set<String>)map.keySet()) {
                    actionStr = k.replace("[", "").replace("]", "");
                }
                action = ActionLib.getAction(actionStr);
                for (Object o : ((LinkedHashMap<?, ?>) map).values()) {
                    secondMap = (Map) o;
                    if (action instanceof MapSettingsAction) {
                        try {
                            //Send hashmap values (type and amount)
                            ReflectionUtil.setField(action.getClass().getField("settings"), o, action);
                        } catch (NoSuchFieldException | SecurityException ex) {
                            Logger.getLogger(ActionManagerOld.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else {
                        for (Field f : action.getClass().getFields()) {
                            try {
                                f.set(action, secondMap.get(f.getName()));
                            } catch (IllegalArgumentException | IllegalAccessException ex) {
                                Logger.getLogger(ActionManagerOld.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                    }
                }
                actions.add(action);
            }
            else {
                Util.getLogger().severe("The list '" + path + "' in the configuration " + config.getName() + " contains a not valid value '" + key + "'");
            }
        }
        return actions;
    }

    public static List<Action> buildDefaultAction(String name) {
        List<Action> list = null;
        name = name.replace("{", "").replace("}", "");
        list = buildActions(ActionLib.plugin.getConfig(), "actions." + name);
        return list;
    }

}