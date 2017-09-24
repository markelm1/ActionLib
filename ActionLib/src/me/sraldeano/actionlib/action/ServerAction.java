package me.sraldeano.actionlib.action;

import me.sraldeano.actionlib.Action;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ServerAction extends Action{

    public String server = null;

    public ServerAction() {
        super("Server");
    }

    @Override
    public void onExecute() {
        if (server != null) {
            ServerInfo server = ProxyServer.getInstance().getServerInfo(this.server);
            ProxiedPlayer player = (ProxiedPlayer) getPlayer();
            player.connect(server);
        }
    }
}
