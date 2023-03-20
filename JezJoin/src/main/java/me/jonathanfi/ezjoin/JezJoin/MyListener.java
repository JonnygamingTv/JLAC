package me.jonathanfi.ezjoin.JezJoin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
//import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
//import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class MyListener implements Listener {
	private static String hub = "Hub";
	public static String hub2;
	public static String hub3;
	public static void set(String n) {
		hub=n;
	}
	
    @EventHandler
    public void onLeave(PlayerDisconnectEvent e) {
    	Db.unsetLogp(e.getPlayer().getName());
    	Db.ison(e.getPlayer().getName(), false);
    }
    @EventHandler
    public void onJoin2(PostLoginEvent e) {
    	String name=e.getPlayer().getName();
    	System.out.println("[JJ] Done Logged in: "+name);
    	ProxiedPlayer p = (ProxiedPlayer) e.getPlayer();
    	if(Db.needLogp(name)) {
    		if(App.fpx||name.substring(0,App.prefix.length())!=App.prefix) {
    			p.setDisplayName(App.prefix+name);
    		}
    	if(Db.getPpl(name, "")) {
    		p.sendMessage(new ComponentBuilder(App.motd+App.logMsg).color(ChatColor.RED).create());
    	}else {
    		if(!App.force)Db.unsetLogp(name);
    		p.sendMessage(new ComponentBuilder(App.motd+App.regMsg).color(ChatColor.RED).create());
    	}
    	}
    }
    @EventHandler
    public void onShift(ServerConnectEvent e) {
    	String name=e.getPlayer().getName();
    	String ip;
    	if(Db.needLogp(name)) {
    		if(e.getTarget().getName() != null)
    			if(e.getTarget().getName() != hub) {
    				if(App.tpserv)Db.setsrv(name, e.getTarget().getName());
    				e.setTarget(ProxyServer.getInstance().getServerInfo(hub));
    			}
    	}else if((ip=e.getPlayer().getPendingConnection().getVirtualHost().getHostString()) != null) {
    		if(Db.alias.containsKey(ip)) {
    			if((!Db.on(name))||App.forceIP||Db.ipforce.containsKey(ip)) {Db.ison(name, true);e.setTarget(ProxyServer.getInstance().getServerInfo(Db.alias.get(ip)));}
    		}
    	}
    }
    @EventHandler
    public void onChat(ChatEvent e) {
    	String name=e.getSender().toString();
    	if(Db.needLogp(name)) if(!e.getMessage().startsWith("/login")&&!e.getMessage().startsWith("/register")){
    		e.setCancelled(true);
    	}
    }
    @EventHandler (priority = net.md_5.bungee.event.EventPriority.HIGH)
    public void onPing(ProxyPingEvent e) {
    	final String ip;
    	if(e.getConnection().getVirtualHost()!=null&&(ip=e.getConnection().getVirtualHost().getHostString())!=null&&Db.alias.containsKey(ip)) {
    		ServerInfo gg = ProxyServer.getInstance().getServerInfo(Db.alias.get(ip));
    		if(gg!=null) {
    			if(Db.ipPing.containsKey(ip)) {
    				ServerPing og = e.getResponse();
    				ServerPing LastPing = Db.ipPing.get(ip);
    				if(!Db.pingFavi.containsKey(ip))LastPing.setFavicon(og.getFaviconObject());
					if(!Db.pingP.containsKey(ip))LastPing.setPlayers(og.getPlayers());
					if(!Db.pingMotD.containsKey(ip))LastPing.setDescriptionComponent(og.getDescriptionComponent());
					e.setResponse(LastPing);
    			}
    		final ProxyPingEvent ev = e;
    		Callback<ServerPing> callback = new Callback<ServerPing>() {
				@SuppressWarnings("deprecation")
				@Override
				public void done(ServerPing result, Throwable error) {
					Db.ipPing.put(ip, result);
					ServerPing og = ev.getResponse();
					if(result!=null) {
						if(!Db.pingFavi.containsKey(ip))result.setFavicon(og.getFaviconObject());
						if(!Db.pingP.containsKey(ip))result.setPlayers(og.getPlayers());
						if(!Db.pingMotD.containsKey(ip))result.setDescriptionComponent(og.getDescriptionComponent());
						ev.setResponse(result);
					}else if(Db.pingF.containsKey(ip)){
						//net.md_5.bungee.chat.BaseComponentSerializer
						//net.md_5.bungee.api.chat.BaseComponent desc = new net.md_5.bungee.api.chat.TextComponent(Db.pingF.get(ip));
						og.setDescription(Db.pingF.get(ip));
						ev.setResponse(og);
					}
				}
    		};
    		//gg.getMotd();
    		//ServerPing res = null;
			gg.ping(callback);
			//Throwable err = null;
			//callback.done(res, err);
			//ServerPing res = null;
			//Throwable err = null;
			//if(callback!=null)callback.done(res, err);
    		}
    	}
    }
    @EventHandler
    public void onJoin3(PreLoginEvent e) {
    	if(!App.enabled)return;
    	System.out.println("[JJ] Logging in: "+(e.getConnection().getUniqueId()!=null?e.getConnection().getUniqueId():e.getConnection().getName()));
    	String name=e.getConnection().getName();
    	boolean ignor=false;
    	if(App.ignore!=null) {
    		for(int i=0;i<App.ignore.size();i++) {
    	String str=name.substring(0,App.ignore.get(i).toString().length());
    	if(str==App.ignore.get(i).toString()||str.contentEquals(App.ignore.get(i).toString())) {
    		ignor=true;
    	}
    		}
    	}
    	if(!ignor&&App.blacklist!=null) {
			for(int i=0;i<App.blacklist.size();i++) {
		    	String str=name;
		    	if(str.contains(App.blacklist.get(i).toString())) {
		    		ignor=true;
		    	}
			}
		}
    	if(!Db.doexist(name)&&!ignor){e.setCancelled(false);
    		if(Db.on(name))if(Db.needLogp(name))ProxyServer.getInstance().getPlayer(name).disconnect();
    	if(App.whitelist!=null) {
    		for(int i=0;i<App.whitelist.size();i++) {
    			String str=name;
    			if(str.contains(App.whitelist.get(i).toString())) {
		    		ignor=true;
		    		Db.setLogp(name);
		        	e.getConnection().setOnlineMode(false);
		    	}
    		}
    	}
    	if(!ignor)if(name.length()<App.minlength) {
    		Db.setLogp(name);
        	e.getConnection().setOnlineMode(false);
    		ignor=true;
    	}
    	if(!ignor) {
    	String requestUrl = "https://api.mojang.com/profiles/minecraft";
    	JsonArray payload = new JsonArray();
    	payload.add(name);
    	StringBuilder sb = new StringBuilder();
    	try {
    		URL url = new URL(requestUrl);
    		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    		connection.setDoInput(true);
    		connection.setDoOutput(true);
    		connection.setRequestMethod("POST");
    		connection.setRequestProperty("Accept", "application/json");
    		connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
    		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
    		writer.write(payload.toString());
    		writer.close();
    		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    		String line;
    		while ((line = br.readLine()) != null) {
    			sb.append(line);
    		}
    		br.close();
    		connection.disconnect();
    	}catch(Exception er) {}
    	String res = sb.toString();
    	try {
    		JsonParser resp = new JsonParser();
    		JsonArray json = (JsonArray) resp.parse(res);
    		if(json.get(0) != null) {
    			e.getConnection().setOnlineMode(true);
    			Db.exists(name);
    			return;
    		}
    	}catch(Exception er) {}
    	Db.setLogp(name);
    	e.getConnection().setOnlineMode(false);
    	}
    	}
    }

    @EventHandler
    public void onLogin(LoginEvent e) {
    	System.out.println("[JJ:"+e.getConnection().getVirtualHost().getHostString()+"] Logged in: "+(e.getConnection().getUniqueId()!=null?e.getConnection().getUniqueId():e.getConnection().getName()));
    	PendingConnection connection = e.getConnection();
    	String name = connection.getName();
    	if(!connection.isOnlineMode()) {
    		boolean ignor=false;
    		if(App.ignore!=null) {
    	    	for(int i=0;i<App.ignore.size();i++) {
    	    		String str=name.substring(0,App.ignore.get(i).toString().length());
    	    		if(str==App.ignore.get(i).toString()||str.contentEquals(App.ignore.get(i).toString())) {
    	    			ignor=true;
    	    		}
    	    	}
    		}
    		if(!ignor) {
    			Db.setLogp(name);
    			System.out.println("[JezJ] Offline mode detected: "+App.prefix+name);
    		}
    	}
    }
    /*
    @EventHandler
    public void onAuth(PlayerHandshakeEvent e) {
    	System.out.println(e.getHandshake()+" handshake [JezJ]");
    }*/
}
