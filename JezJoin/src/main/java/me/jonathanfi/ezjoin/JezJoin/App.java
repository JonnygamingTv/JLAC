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
	public static String motd="JonHosting.com";
    @Override
    public void onEnable() {
        getLogger().info("Getting configuration..");
        try {
        	if(!getDataFolder().exists()) {
        		getDataFolder().mkdir();
        	}
        	File fil = new File(getDataFolder(),"config.yml");
        	Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load("Hub: Hub\nloginTries: 0\nloginPunishment: kick\nloginTp: true\nforce: false\nsave: false\nasync: false\nmotd: web.JonHosting.com");
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
        	motd=config.getString("motd");
        	if(motd==""||motd.length()<2)motd="JonHosting.com";
        	lTri=config.getInt("loginTries");
        	getLogger().info("Hub: "+hub+" | Tpserv: "+tpserv+" | Force: "+force+" | Tries: "+lTri+" | save: "+save+" | async: "+async);
        }catch(Exception er) {getLogger().info("Uh oh! Seems like you need to remove my folder in the plugins folder!");}
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Login());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Register());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new MyListener());
    }
}
