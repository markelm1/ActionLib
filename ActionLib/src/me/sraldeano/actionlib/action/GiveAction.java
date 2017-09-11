package me.sraldeano.actionlib.action;

import java.util.HashMap;
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
    Map<String, Object> settings = new HashMap<>();
    
    public GiveAction() {
        super("Give");
    }

    @Override
    public void onExecute() {
        Material type = Material.getMaterial((String) settings.get("type"));
        getPlayer().getInventory().addItem(new ItemStack(type, (int) settings.get("amount")));
    }
    
}
