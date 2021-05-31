package me.jonathanfi.ezjoin.JezJoin;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

public class App extends Plugin implements Listener {
    @Override
    public void onEnable() {
        getLogger().info("Jon's EzJoin plugin is alive!");
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Login());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Register());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new MyListener());
    }
}
