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
import org.bukkit.configuration.Configuration;
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
        action.setSettings(settings);
        action.setVariables(variables);
        action.execute(player);
        action.reset();
    }
    
    public static void sendAction(Player player, ConfigurationSection settings) {
    }
    
    public static void sendAction(Player player, String actionName, ConfigurationSection settings) {
        Action action = null;
        try {
            action = ActionLib.getActionClass(actionName).newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ActionManager.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        sendAction(player, action, settings.getValues(false), null);
    }
    
    public static void sendActions(Player player, Configuration config, String path) {
        
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
    
    public static List<Action> buildActions(Configuration config, String path) {
        List<?> list = config.getList(path);
        ArrayList<Action> actions = new ArrayList<>();
        if (list == null) {
            System.out.println("list is null");
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
                    for (Field f : fields) {
                        ReflectionUtil.setField(f, splited[1], action);
                    }
                    actions.add(action);
                }
                else if (keyString.startsWith("{")) {
                    actions.addAll(buildDefaultAction(keyString));
                }
            }
            else if (key instanceof Map) {
                Map map = (Map) key;
                String actionStr = null;
                Map secondMap = null;
                for (String k : (Set<String>)map.keySet()) {
                    actionStr = k.replace("[", "").replace("]", "");
                }
                System.out.println("actionstr: " + actionStr);
                action = ActionLib.getAction(actionStr);
                
                                        
                for (Object o : ((LinkedHashMap<?, ?>) map).values()) {
                    secondMap = (Map) o;
                    System.out.println(o);
                    if (action instanceof MapSettingsAction) {
                        try {
                        //Send hashmap values (type and amount)
                        ReflectionUtil.setField(action.getClass().getField("settings"), o, action);
                        } catch (NoSuchFieldException | SecurityException ex) {
                            Logger.getLogger(ActionManager.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else {
                        for (Field f : action.getClass().getFields()) {
                            try {
                                f.set(action, secondMap.get(f.getName()));
                            } catch (IllegalArgumentException | IllegalAccessException ex) {
                                Logger.getLogger(ActionManager.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                    }
                }
                                        
//                    try {
//                        ReflectionUtil.setField(action.getClass().getField("settings"), secondMap, action);
//                    } catch (NoSuchFieldException | SecurityException ex) {
//                        Logger.getLogger(ActionManager.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                actions.add(action);
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
