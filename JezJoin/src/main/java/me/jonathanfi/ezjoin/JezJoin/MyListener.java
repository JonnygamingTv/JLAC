package me.jonathanfi.ezjoin.JezJoin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class MyListener implements Listener {
    @EventHandler
    public void onLeave(PlayerDisconnectEvent e) {
    	Db.unsetLogp(e.getPlayer().getName());
    	Db.ison(e.getPlayer().getName(), false);
    }
    @EventHandler
    public void onJoin2(PostLoginEvent e) {
    	String name=e.getPlayer().getName();
    	ProxiedPlayer p = (ProxiedPlayer) e.getPlayer();
    	if(Db.needLogp(name)) {
    	if(Db.getPpl(name, "")) {
    		p.sendMessage(new ComponentBuilder("JonHosting.com\n\n/login <password>\n\n").color(ChatColor.RED).create());
    	}else {
    		Db.unsetLogp(name);Db.ison(name, true);
    		p.sendMessage(new ComponentBuilder("JonHosting.com\n\n/register <password>\n\n").color(ChatColor.RED).create());
    	}
    	}
    }
    @EventHandler
    public void onShift(ServerConnectEvent e) {
    	String name=e.getPlayer().getName();
    	if(Db.needLogp(name)) {
    		if(e.getTarget().getName() != null)
    			if(e.getTarget().getName() != "Hub") {
    				Db.setsrv(name, e.getTarget().getName());
    				e.setTarget(ProxyServer.getInstance().getServerInfo("Hub"));
    				//e.getPlayer().connect(ProxyServer.getInstance().getServerInfo("Hub"));
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
    @EventHandler
    public void onJoin3(PreLoginEvent e) {
    	String name=e.getConnection().getName();
    	e.setCancelled(false);
    	if(!Db.doexist(name))if(!Db.on(name)) {
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
