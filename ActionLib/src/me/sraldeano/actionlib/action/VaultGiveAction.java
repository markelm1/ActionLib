package me.sraldeano.actionlib.action;

import me.sraldeano.actionlib.Action;
import me.sraldeano.actionlib.ActionLib;

public class VaultGiveAction extends Action{

    public int amount = 1;

    public VaultGiveAction() {
        super("VaultGiveAction");
    }

    @Override
    public void onExecute() {
        ActionLib.plugin.eco.depositPlayer(getPlayer(), amount);
    }
}
