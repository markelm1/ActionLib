/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.sraldeano.actionlib.util;

import me.sraldeano.actionlib.ActionLib;
import me.sraldeano.actionlib.BasicListener;
import org.bukkit.plugin.PluginManager;

public class LoadUtil {
    public static void registerEvents() {
        PluginManager pm = ActionLib.plugin.getServer().getPluginManager();
        pm.registerEvents(new BasicListener(), ActionLib.plugin);
    }

    public static void registerActions() {
        Package.getPackage("me.sraldeano.actionlib.action");
    }
}
