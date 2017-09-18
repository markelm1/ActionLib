package me.sraldeano.actionlib.action;

import me.sraldeano.actionlib.Action;
import me.sraldeano.actionlib.util.TitleUtil;

public class ActionBarAction extends Action{

    public String message = "Default ActionBar.";

    public ActionBarAction() {
        super ("ActionBar");
    }

    @Override
    public void onExecute() {
        TitleUtil.sendActionBar(getPlayer(), replaceText(message));
    }
}
