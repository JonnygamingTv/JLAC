package me.jonathanfi.ezjoin.JezJoin;

import java.io.File;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class App extends Plugin implements Listener {
	public static boolean save=false;
	public static boolean async=false;
	public static boolean tpserv=true;
	public static boolean force=false;
	public static int lTri=0;
    @Override
    public void onEnable() {
        getLogger().info("Getting configuration..");
        try {
        	if(!getDataFolder().exists()) {
        		getDataFolder().mkdir();
        	}
        	File fil = new File(getDataFolder(),"config.yml");
        	Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load("Hub: Hub\n# Hub is the server where person would be forced to /login in.\nloginTries: 0\nloginPunishment: kick # more support coming soon\nloginTp: true # teleport player back to the server they were going to after /login.\nforce: false # force /register\nsave: false # save on file what logins the players got\nasync: false\nmotd: https://JonHosting.com");
        	if(!fil.exists()) {
        		try{
        			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(getDataFolder(), "config.yml"));
        		}catch(Exception e) {e.printStackTrace();}
        	}else {config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));}
        	if(config.getBoolean("save"))save=true;
        	if(config.getBoolean("async"))async=true;
        	String hub=config.getString("Hub");
        	if(hub!=null)if(hub!=""){MyListener.set(hub);}
        	if(config.getBoolean("tpserv")==false)tpserv=false;
        	if(config.getBoolean("force"))force=true;
        	lTri=config.getInt("loginTries");
        	getLogger().info("Hub: "+hub+" | Tpserv: "+tpserv+" | Force: "+force+" | Tries: "+lTri+" | save: "+save+" | async: "+async);
        }catch(Exception er) {getLogger().info("Uh oh! Seems like you need to remove my folder in the plugins folder!");}
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Login());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Register());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new MyListener());
    }
}
