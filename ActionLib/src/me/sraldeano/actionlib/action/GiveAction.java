package me.sraldeano.actionlib.action;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import me.sraldeano.actionlib.Action;
import me.sraldeano.actionlib.MapSettingsAction;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author markelm
 */
public class GiveAction extends Action implements MapSettingsAction{
    public Map<String, Object> settings = new LinkedHashMap<>();
    
    public GiveAction() {
        super("Give");
    }

    @Override
    public void onExecute() {
        System.out.println("Settings: " + settings);
        Material type = Material.getMaterial(((String) settings.get("type")).toUpperCase());
        System.out.println("material name: " + type.name());
        ItemStack is = new ItemStack(type);
        is.setAmount((Integer) settings.get("amount"));
        getPlayer().getInventory().addItem(is);
    }
    
}
