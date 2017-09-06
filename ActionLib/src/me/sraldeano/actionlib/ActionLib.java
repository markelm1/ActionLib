package me.sraldeano.actionlib;

import org.bukkit.plugin.java.JavaPlugin;

public class ActionLib extends JavaPlugin{

    public static ActionLib plugin;
    
    @Override
    public void onEnable() {
        plugin = this;
        getServer().getLogger().fine("ActionLib was loaded successfully.");
    }

    @Override
    public void onDisable() {
        getServer().getLogger().fine("ActionLib was unloaded successfully.");
    }
}
