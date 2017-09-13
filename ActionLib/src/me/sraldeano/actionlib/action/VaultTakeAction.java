package me.sraldeano.actionlib.action;

import me.sraldeano.actionlib.Action;
import me.sraldeano.actionlib.ActionLib;

public class VaultTakeAction extends Action{

    public int amount = 1;

    public VaultTakeAction() {
        super("VaultTakeAction");
    }

    @Override
    public void onExecute() {
        ActionLib.plugin.eco.withdrawPlayer(getPlayer(), amount);
    }
}
