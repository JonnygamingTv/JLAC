package me.jonathanfi.ezjoin.JezJoin;

import java.io.File;
import java.util.List;
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
	public static boolean enabled=true;
	public static boolean enacmd=true;
	public static boolean save=false;
	public static boolean uF=false;
	public static boolean async=false;
	public static boolean tpserv=true;
	public static boolean force=false;
	public static boolean forceIP=false;
	public static boolean fpx=false;
	public static int minlength=0;
	public static int lTri=0;
	public static String prefix=null;
	public static String offservmotd="§cOffline";
	public static String motd="JonHosting.com";
	public static String logMsg="\n\n/login <password>\n\n";
	public static String regMsg="\n\n/register <password>\n\n";
	public static List<String> ignore=null;
	public static List<String> blacklist=null;
	public static List<String> whitelist=null;
    @Override
    public void onEnable() {
        getLogger().info("Getting configuration..");
        try {
        	if(!getDataFolder().exists()) {
        		getDataFolder().mkdir();
        	}
        	File pD=new File(getDataFolder(),"p");
        	if(!pD.exists()) {
        		pD.mkdir();
        	}
        	Db.sDir(pD);
        	File fil = new File(getDataFolder(),"config.yml");
        	Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load("Hub: Hub\nsend-login: Lobby\nsend-reg: Lobby\nignore:\n  - \".\"\nblacklist:\n  - \"§\"\nwhitelist:\n  - \"-\"\nminlength: 3\nprefix: \"-\"\naddPrefix: false\nloginTries: 0\nloginPunishment: kick\nloginTp: true\nforce: false\nmotd: web.JonHosting.com\nloginMsg: \"\\n\\n/login <password>\\n\\n\"\nregisterMsg: \"\\n\\n/register <password>\\n\\n\"\noffServMotD: \"§cOffline\"\nforceIP: false\nsave: false\nuseFiles: false\nasync: false\ndisabled: false\ndisable_commands: false");
        	if(!fil.exists()) {
        		try{
        			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config,fil);
        		}catch(Exception e) {e.printStackTrace();}
        	}else {config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(fil);}
        	if(config.getBoolean("save"))save=true;
        	if(config.getBoolean("useFiles"))uF=true;
        	if(config.getBoolean("async"))async=true;
        	String hub=config.getString("Hub");
        	if(hub!=null)if(hub!=""){MyListener.set(hub);}
        	hub=config.getString("send-reg");
        	if(hub!=null)if(hub!=""){MyListener.hub2=hub;}
        	hub=config.getString("send-login");
        	if(hub!=null)if(hub!=""){MyListener.hub3=hub;}
        	if(config.getBoolean("tpserv")==false)tpserv=false;
        	if(config.getBoolean("force"))force=true;
        	if(config.getBoolean("forceIP"))forceIP=true;
        	motd=config.getString("motd");
        	if(motd==""||motd.length()<2)motd="JonHosting.com";
        	logMsg=config.getString("loginMsg");
        	regMsg=config.getString("registerMsg");
        	lTri=config.getInt("loginTries");
        	minlength=config.getInt("minlength");
        	if(config.getString("prefix")!=null&&config.getString("prefix")!="") {
        		prefix=config.getString("prefix");
        	}
        	if(config.getBoolean("addPrefix"))fpx=true;
        	getLogger().info("Hub: "+hub+" | Prefix: "+prefix+" | Tpserv: "+tpserv+" | Force: "+force+" | Tries: "+lTri+" | save: "+save+" | uF: "+uF+" | async: "+async);
        	if(config.getStringList("ignore")!=null) {
        		ignore=config.getStringList("ignore");//config.getString("ignore");
        		for(int i=0;i<App.ignore.size();i++) {
        			getLogger().info("ignoring players starting by '"+ignore.get(i).toString()+"'!");
        		}
        	}
        	if(config.getStringList("blacklist")!=null) {
        		blacklist=config.getStringList("blacklist");//config.getString("ignore");
        		for(int i=0;i<blacklist.size();i++) {
        			getLogger().info("ignoring players including '"+blacklist.get(i).toString()+"'!");
        		}
        	}
        	if(config.getStringList("whitelist")!=null) {
        		whitelist=config.getStringList("whitelist");//config.getString("ignore");
        		for(int i=0;i<whitelist.size();i++) {
        			getLogger().info("autoofflining players including '"+whitelist.get(i).toString()+"'!");
        		}
        	}
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
        	file = new File(getDataFolder(),"pings.json");
        	alias = ConfigurationProvider.getProvider(JsonConfiguration.class).load("{\"jonhosting.com\": [false, false, true, \"Hub is offline\"],\n\"s.jonhosting.com\": [false, true, true, \"Survival down\"]}");
        	if(!file.exists()) {
        		try {
        			ConfigurationProvider.getProvider(JsonConfiguration.class).save(alias,file);
        		}catch(Exception e) {e.printStackTrace();}
        	}else{alias = ConfigurationProvider.getProvider(JsonConfiguration.class).load(file);}
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
        			if(entry.getValue().isJsonArray()) {
        				com.google.gson.JsonArray values = entry.getValue().getAsJsonArray();
        				if(values.get(0).getAsBoolean()) {
        					Db.pingP.put(entry.getKey(), true);
        				}
        				if(values.get(1).getAsBoolean()) {
        					Db.pingFavi.put(entry.getKey(), true);
        				}
        				if(values.get(2).getAsBoolean()) {
        					Db.pingMotD.put(entry.getKey(), true);
        				}
        				if(values.get(3)!=null)if(values.get(3).getAsString()!="")Db.pingF.put(entry.getKey(),values.get(3).getAsString());
        			}
        		}
        		}catch(Exception e) {}
        	}
        	enabled=!config.getBoolean("disabled");
        	enacmd=!config.getBoolean("disable_commands");
        }catch(Exception er) {er.printStackTrace();getLogger().info("Uh oh! Seems like you need to remove my folder in the plugins folder!");}
        if(enacmd) {
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Login());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Register());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Salias());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Jservforce());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Spingconf());
        }
        if(enabled) {
        	ProxyServer.getInstance().getPluginManager().registerListener(this, new MyListener());
        }
    }
    @Override
    public void onDisable() {
        ProxyServer.getInstance().getPluginManager().unregisterCommands(this);
        ProxyServer.getInstance().getPluginManager().unregisterListeners(this);
    }
}
