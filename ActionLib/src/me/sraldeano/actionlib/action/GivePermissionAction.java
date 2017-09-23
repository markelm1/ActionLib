package me.sraldeano.actionlib.action;

import me.sraldeano.actionlib.Action;
import me.sraldeano.actionlib.ActionLib;

public class GivePermissionAction extends Action {

    public String permission = null;

    public GivePermissionAction() {
        super("GivePermission");
    }

    @Override
    public void onExecute() {
        if (permission != null) {
            ActionLib.perms.playerAdd(getPlayer(), permission);
        }
    }
}
