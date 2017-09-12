package me.sraldeano.actionlib.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.sraldeano.actionlib.ActionLib;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 *
 * @author markelm
 */
public class TextUtil {


    public static String colored(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
    
    public static String[] colored(String[] text) {
        List<String> newText = new ArrayList<>();
        for (String t : text) {
            newText.add(colored(t));
        }
        return (String[]) newText.toArray(text);
    }
    
    public static Map<String, String> getPlayerVariables(Player player) {
        Map<String, String> map = new HashMap<>();
        map.put("%player%", player.getName());
        map.put("%health%", player.getHealth() + "");
        map.put("%xp%", player.getExp() + "");
        if (Util.isUsingVault()) {
            map.put("%money%", ActionLib.plugin.eco.getBalance(player) + "");
        }
        map.put("%display_name%", player.getDisplayName());
        map.put("%uuid%", player.getUniqueId().toString());
        return map;
    }
}
