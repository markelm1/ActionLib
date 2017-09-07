/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.sraldeano.actionlib;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
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
        
    }
    
    @EventHandler
    public void onLeave(PlayerJoinEvent e) {
        
    }
    
    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        
    }
    
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (e.getEntity().getKiller() instanceof Player) {
            return;
        }
        
    }
}
