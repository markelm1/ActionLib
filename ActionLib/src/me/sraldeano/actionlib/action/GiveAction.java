package me.sraldeano.actionlib.action;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import me.sraldeano.actionlib.Action;
import me.sraldeano.actionlib.MapSettingsAction;
import me.sraldeano.actionlib.util.ItemUtil;
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
        ItemStack is = ItemUtil.itemConstructor(settings);
        getPlayer().getInventory().addItem(is);
    }
    
}
