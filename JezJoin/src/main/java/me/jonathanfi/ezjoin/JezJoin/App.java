package me.jonathanfi.ezjoin.JezJoin;

import java.io.File;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.JsonConfiguration;
import net.md_5.bungee.config.YamlConfiguration;

public class App extends Plugin implements Listener {
	public static boolean save=false;
	public static boolean async=false;
	public static boolean tpserv=true;
	public static boolean force=false;
	public static boolean forceIP=false;
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
        	Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load("Hub: Hub\nloginTries: 0\nloginPunishment: kick\nloginTp: true\nforce: false\nmotd: web.JonHosting.com\nforceIP: false\nsave: false\nasync: false");
        	if(!fil.exists()) {
        		try{
        			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config,fil);
        		}catch(Exception e) {e.printStackTrace();}
        	}else {config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(fil);}
        	if(config.getBoolean("save"))save=true;
        	if(config.getBoolean("async"))async=true;
        	String hub=config.getString("Hub");
        	if(hub!=null)if(hub!=""){MyListener.set(hub);}
        	if(config.getBoolean("tpserv")==false)tpserv=false;
        	if(config.getBoolean("force"))force=true;
        	if(config.getBoolean("forceIP"))forceIP=true;
        	motd=config.getString("motd");
        	if(motd==""||motd.length()<2)motd="JonHosting.com";
        	lTri=config.getInt("loginTries");
        	getLogger().info("Hub: "+hub+" | Tpserv: "+tpserv+" | Force: "+force+" | Tries: "+lTri+" | save: "+save+" | async: "+async);
        	File file = new File(getDataFolder(),"servs.json");
        	Configuration alias = ConfigurationProvider.getProvider(JsonConfiguration.class).load("{\"jonhosting.com\": \"Hub\",\n\"s.jonhosting.com\": \"Survival\"}");
        	if(!file.exists()) {
        		try {
        			ConfigurationProvider.getProvider(JsonConfiguration.class).save(alias,file);
        		}catch(Exception e) {e.printStackTrace();}
        	}else{alias = ConfigurationProvider.getProvider(JsonConfiguration.class).load(file);}
        	for(String i : alias.getKeys()) {
    		try {
    			Object addr = alias.get(i);
    			if(addr!=null) {System.out.println(addr);
    			String ip=addr.toString();
    			Db.alias.put(i, ip);
    			}
    		}catch(Exception err) {err.printStackTrace();}
    		//
        	}
        	if(file.canRead()) {
        		Scanner myReader = new Scanner(file);
        		String dat = "";
        		while (myReader.hasNextLine()) {
        			dat += myReader.nextLine();
        		}
        		myReader.close();
        		try {
        		JsonParser resp = new JsonParser();
        		JsonElement obj = (JsonElement) resp.parse(dat);
        		JsonObject arr = (JsonObject)obj.getAsJsonObject();
        		Set<Entry<String, JsonElement>> entrySet = arr.entrySet();
        		for(Entry<String, JsonElement> entry : entrySet) {
        			Db.alias.put(entry.getKey(),entry.getValue().getAsString());
        		}
        		}catch(Exception e) {}
        	}
        }catch(Exception er) {er.printStackTrace();getLogger().info("Uh oh! Seems like you need to remove my folder in the plugins folder!");}
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Login());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Register());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Salias());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Jservforce());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new MyListener());
    }
}
