package me.sraldeano.actionlib;

/**
 *
 * @author SrAldeano
 */
public abstract class Action {
    
    private String name;
    
    public Action(String name) {
        this.name = name;
    }
    
    public final String getName() {
        return name;
    }
    
    public abstract void onExecute();
}
