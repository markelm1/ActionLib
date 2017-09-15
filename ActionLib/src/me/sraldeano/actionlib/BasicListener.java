package me.sraldeano.actionlib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 *
 * @author markelm
 */
public class BasicListener implements Listener{
    
    public BasicListener() {}
    
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        sendEvent(e.getPlayer(), e, "join", null);
    }
    
    @EventHandler
    public void onLeave(PlayerJoinEvent e) {
        sendEvent(e.getPlayer(), e, "leave", null);
    }
    
    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("teleportCause", e.getCause());
        sendEvent(e.getPlayer(), e, "teleport", map);
    }
    
    @EventHandler
    public void onDeathOrKill(PlayerDeathEvent e) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("drops", e.getDrops());
        if (e.getEntity().getKiller() instanceof Player) {
            Player killer = e.getEntity().getKiller();
            sendEvent(killer, e, "kill", map);
            return;
        }
        sendEvent(e.getEntity(), e, "death", map);
    }
    @EventHandler
    public void onFish(PlayerFishEvent e) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("caught", e.getCaught());
        map.put("fishState", e.getState());
        sendEvent(e.getPlayer(), e, "fish", map);
    }
    
    @EventHandler
    public void onTame(EntityTameEvent e) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("entity", e.getEntity());
        sendEvent((Player) e.getOwner(), e, "tame", map);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        sendEvent(e.getPlayer(), e, "chat", null);
    }
    
    public static void sendEvent(Player player, Event event, String eventConfig, Map<String, Object> variables) {
        List<Action> actions;
        if (variables == null) {
            variables = new HashMap<String, Object>();
        }
        variables.put("event", event);
        System.out.println("events." + eventConfig);
        actions = ActionManager.buildActions(ActionLib.plugin.getConfig(), "events." + eventConfig);
        if (actions != null) {
            for (Action a : actions) {
                a.setVariables(variables);
                a.execute(player);
            }
        }
    }
}
