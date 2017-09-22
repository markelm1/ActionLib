package me.sraldeano.actionlib.action;

import me.sraldeano.actionlib.Action;
import me.sraldeano.actionlib.ActionLib;

public class VaultGiveAction extends Action{

    public double amount = 1;

    public VaultGiveAction() {
        super("VaultGive");
    }

    @Override
    public void onExecute() {
        ActionLib.plugin.eco.depositPlayer(getPlayer(), amount);
    }
}
