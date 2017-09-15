package me.sraldeano.actionlib.action;

import me.sraldeano.actionlib.Action;

public class SetFlyingAction extends Action{

    public SetFlyingAction() {
        super("SetFlying");
    }

    @Override
    public void onExecute() {
        getPlayer().setFlying(true);
    }
}
