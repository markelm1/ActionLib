package me.sraldeano.actionlib.action;

import me.sraldeano.actionlib.Action;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.time.LocalDateTime;
import java.util.Date;

public class BanAction extends Action{

    String reason = "You have been banned.";

    public BanAction() {
        super("Ban");
    }

    @Override
    public void onExecute() {
        Bukkit.getBanList(BanList.Type.NAME).addBan(getPlayer().getName(), reason, new Date(), "ActionLib");
    }
}
