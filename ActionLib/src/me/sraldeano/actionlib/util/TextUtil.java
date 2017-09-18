package me.sraldeano.actionlib.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.sraldeano.actionlib.ActionLib;
import org.bukkit.ChatColor;
import org.bukkit.Color;
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

    public static List<String> colored (List<String> text) {
        List<String> newText = new ArrayList<>();
        for (String t : text) {
            newText.add(colored(t));
        }
        return newText;
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

    public static Color getRGBColor(String rgb) {
        if (rgb.contains(",")) {
            String[] splited = rgb.split(",");
            Integer[] ints = {Integer.parseInt(splited[0]), Integer.parseInt(splited[1]),
                    Integer.parseInt(splited[2])};
            return Color.fromRGB(ints[0], ints[1], ints[2]);
        }
        else {
            switch (rgb) {
                case "RED" :
                    return Color.RED;
                case "GREEN" :
                    return Color.GREEN;
                case "BLUE" :
                    return Color.BLUE;
                case "BLACK" :
                    return Color.BLACK;
                case "WHITE" :
                    return Color.WHITE;
                case "AQUA" :
                    return Color.AQUA;
                case "FUCHSIA" :
                    return Color.FUCHSIA;
                case "GRAY" :
                    return Color.GRAY;
                case "LIME" :
                    return Color.LIME;
                case "MAROON" :
                    return Color.MAROON;
                case "NAVY" :
                    return Color.NAVY;
                case "OLIVE" :
                    return Color.OLIVE;
                case "SILVER" :
                    return Color.SILVER;
                case "ORANGE" :
                    return Color.ORANGE;
                case "PURPLE" :
                    return Color.PURPLE;
                case "TEAL" :
                    return Color.TEAL;
                case "YELLOW" :
                    return Color.YELLOW;
                case "PINK" :
                    return Color.fromRGB(255, 20, 147);
                case "MAGENTA" :
                    return Color.FUCHSIA;
                case "BEIGE" :
                    return Color.fromRGB(245, 245, 220);
                case "BROWN" :
                    return Color.fromRGB(139, 69, 19);
                case "AZURE" :
                    return Color.fromRGB(240, 255, 255);
                case "INDIGO" :
                    return Color.fromRGB(75, 0, 130);
                case "TURQUOISE" :
                    return Color.fromRGB(64, 224, 208);
                case "GOLD" :
                    return Color.fromRGB(255, 215, 0);
                case "SALMON" :
                    return Color.fromRGB(250, 128, 114);
                default:
                    return null;
            }
        }
    }

    public static List<Color> getRGBColors(List<String> list) {
        List<Color> colorList= new ArrayList<>();
        for (String color : list) {
            colorList.add(getRGBColor(color));
        }
        return colorList;
    }
}
