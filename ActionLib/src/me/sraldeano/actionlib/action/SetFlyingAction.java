package me.sraldeano.actionlib.action;

import me.sraldeano.actionlib.Action;

public class SetFlyingAction extends Action{

    public boolean fly = true;

    public SetFlyingAction() {
        super("SetFlying");
    }

    @Override
    public void onExecute() {
        getPlayer().setFlying(fly);
    }
}
