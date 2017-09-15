package me.sraldeano.actionlib.util;

import java.util.Map;
import me.sraldeano.actionlib.ActionLib;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author markelm
 */
public class Util {
    
    public static PotionEffect potionEffectBuilder(Player player, Map<String, Object> settings) {
        PotionEffectType type = PotionEffectType.getByName((String) settings.get("effect"));
        int duration = (int) settings.getOrDefault("duration", 60);
        int amplifier = (int) settings.getOrDefault("level", settings.getOrDefault("amplifier", 1));
        boolean particles = (boolean) settings.getOrDefault("particles", true);
        boolean ambient = (boolean) settings.getOrDefault("ambient", false);
        return new PotionEffect(type, duration, amplifier, particles, ambient);
    }
    
    public static boolean isUsingVault() {
        return ActionLib.plugin.getServer().getPluginManager().getPlugin("Vault") != null;
    }

    public static Location locationBuilder(String loc) {
        loc = loc.replace(" ", "");
        String[] splits = loc.split(",");
        World world = Bukkit.getWorld(splits[0]);
        double x, y, z;
        x = Double.valueOf(splits[1]);
        y = Double.valueOf(splits[2]);
        z = Double.valueOf(splits[3]);
        Location location = new Location(world, x, y, z);
        return location;

    }
}
