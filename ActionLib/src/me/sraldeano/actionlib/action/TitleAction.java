package me.sraldeano.actionlib.action;

import me.sraldeano.actionlib.Action;
import me.sraldeano.actionlib.util.TitleUtil;

public class TitleAction extends Action {

    public String title = "Default title";
    public String subtitle = "";
    public int fadein = 10;
    public int fadeout = 10;
    public int stay = 30;

    public TitleAction() {
        super ("Title");
    }

    @Override
    public void onExecute() {
        TitleUtil.sendTitle(getPlayer(), replaceText(title), replaceText(subtitle), fadein, stay, fadeout);
    }
}
