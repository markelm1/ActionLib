package me.sraldeano.actionlib.action;

import me.sraldeano.actionlib.Action;
import me.sraldeano.actionlib.ActionLib;

public class VaultTakeAction extends Action{

    public double amount = 1;

    public VaultTakeAction() {
        super("VaultTake");
    }

    @Override
    public void onExecute() {
        ActionLib.plugin.eco.withdrawPlayer(getPlayer(), amount);
    }
}
