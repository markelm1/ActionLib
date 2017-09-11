package me.sraldeano.actionlib.util;

import java.util.Map;
import me.sraldeano.actionlib.ActionLib;
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
}
