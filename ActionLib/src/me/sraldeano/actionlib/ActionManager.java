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
import org.yaml.snakeyaml.error.YAMLException;

/**
 *
 * @author markelm
 */
public class ActionManager {
    
    private Player[] players;
    private Action action;
    private Map<String, Object> settings;
    private Object setting;
    private Map<String, Object> variables;

    /**
     * Create an ActionManager with one or more players
     * @param player
     */
    public ActionManager(Player... player) {
        this.players = player;
    }

    public ActionManager() {

    }

    /**
     * Create an ActionManager with a list of players
     * @param players
     */
    public ActionManager(List<Player> players) {
        this.players = players.toArray(new Player[players.size()]);
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

    public ActionManager setSettings(Object setting) {
        this.setting = setting;
        return this;
    }

    public ActionManager setPlayers(Player... players) {
        this.players = players;
        return this;
    }

    public ActionManager setPlayers(List<Player> players) {
        this.players = players.toArray(new Player[players.size()]);
        return this;
    }

    /**
     * Build the final action.
     * @return
     */
    public Action build() {
        if (settings != null) {
            if (action instanceof MapSettingsAction) {
                try {
                    action.getClass().getField("settings").set(action, settings);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
            else {
                for (Field f : action.getClass().getFields()) {
                    ReflectionUtil.setField(f, settings.get(f.getName()), action);
                }
            }
        }
        if (setting != null) {
            if (action.getClass().getFields().length != 0) {
                ReflectionUtil.setField(action.getClass().getFields()[0], setting, action);
            }
        }
        return action;
    }
    
    public void execute() {
        if (players == null) {return;}
        for (Player p : players) {
            if (variables != null) {
                build().execute(p, variables);
                return;
            }
            build().execute(p);
        }
    }

    public static void sendActions(Player player, Configuration conf, String path) {
        for (Action action : buildActions(conf, path)) {
            action.execute(player);
        }
    }

    public static void sendActions(Player player, Configuration conf, String path, Map<String, Object> variables) {
        for (Action action : buildActions(conf, path)) {
            action.execute(player, variables);
        }
    }

    public static List<Action> buildActions(Configuration config, String path) {
        List<?> list = config.getList(path);
        if (list == null) {
            Util.getLogger().severe("Cannot found list '" + path + "' in the configuration: " + config.getName());
            return null;
        }
        try {
            return buildActions(list);
        } catch (Exception e) {
            Util.getLogger().severe("The list '" + path + "' in the configuration " + config.getName() + " contains has an error");
        }
        return null;
    }

    public static List<Action> buildActions(List<?> list){

        ArrayList<Action> actions = new ArrayList<>();
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
                    action = new ActionManager().setAction(actionName).setSettings(keyString.replace(splited[0], "")).build();
                    actions.add(action);
                }
                else if (keyString.startsWith("{")) {
                    actions.addAll(buildDefaultAction(keyString));
                }
                else throw new YAMLException("The formatting of the String is erroneous");
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
                            ReflectionUtil.setField(action.getClass().getField("settings"), o, action);
                        } catch (NoSuchFieldException | SecurityException ex) {
                            ex.printStackTrace();
                        }
                    }
                    else {
                        for (Field f : action.getClass().getFields()) {
                            try {
                                f.set(action, secondMap.get(f.getName()));
                            } catch (IllegalArgumentException | IllegalAccessException ex) {
                                ex.printStackTrace();
                            }
                        }

                    }
                }
                actions.add(action);
            }
            else {
                throw new YAMLException("An unknown object was found in the list.");
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
        ActionLib.actions.add(action);
        ActionLib.actionMap.put(action.getName(), action);
        ActionLib.actionClassMap.put(action.getName(), action.getClass());
    }
}