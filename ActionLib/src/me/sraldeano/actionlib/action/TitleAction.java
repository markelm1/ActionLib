package me.sraldeano.actionlib.action;

import me.sraldeano.actionlib.Action;
import me.sraldeano.actionlib.util.TitleUtil;

public class TitleAction extends Action {

    public String title = "Default title", subtitle;
    public int fadein = 10, fadeout = 10, stay = 30;

    public TitleAction() {
        super ("Title");
    }

    @Override
    public void onExecute() {
        TitleUtil.sendTitle(getPlayer(), title, subtitle, fadein, stay, fadeout);
    }
}
