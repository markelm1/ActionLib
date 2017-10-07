package me.sraldeano.actionlib.action;

import me.sraldeano.actionlib.Action;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

public class SoundAction extends Action {

    public String sound = Bukkit.getVersion().contains("1.9") ? "BLOCK_NOTE_PLING" : "NOTE_PLING";
    public float volume = 1;
    public float pitch = 1;

    public SoundAction() {
        super("Sound");
    }

    @Override
    public void onExecute() {
        System.out.println(sound);
        getPlayer().playSound(getPlayer().getLocation(), Sound.valueOf(sound), volume, pitch);
    }
}