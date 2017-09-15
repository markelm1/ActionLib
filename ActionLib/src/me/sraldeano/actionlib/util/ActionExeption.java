package me.sraldeano.actionlib.util;

import java.rmi.server.ExportException;

public class ActionExeption extends Exception{
    public ActionExeption(String message) {
        super(message);
    }
}
