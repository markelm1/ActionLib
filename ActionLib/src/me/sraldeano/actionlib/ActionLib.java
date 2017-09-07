package me.sraldeano.actionlib;

import java.util.List;
import java.util.Map;
import me.sraldeano.actionlib.util.AddonUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ActionLib extends JavaPlugin{
    private static List<Action> addonActions;
    private static List<Action> actions;
    private static Map<String, Action> actionMap;
    public static ActionLib plugin;
    
    @Override
    public void onEnable() {
        plugin = this;
        getServer().getLogger().fine("ActionLib was loaded successfully.");
        List<Action> actions = new AddonUtil().loadAddons();
        for (Action a : actions) {
            getLogger().info(ChatColor.BOLD + a.getName());
        }
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        getServer().getLogger().fine("ActionLib was unloaded successfully.");
    }
    
    public static void testMsg(Object msg) {
        plugin.getLogger().info(msg + "");
    }
    
    public static void sendAction(Player receiver, Action action) {
        
    }

    public static List<Action> getActions() {
        return actions;
    }

    public static List<Action> getAddonActions() {
        return addonActions;
    }
}
