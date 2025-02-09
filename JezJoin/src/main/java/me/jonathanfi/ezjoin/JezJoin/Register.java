package me.jonathanfi.ezjoin.JezJoin;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Register extends Command {
	public Register() {
		super("register");
	}
	public void execute(CommandSender sender, String[] args) {
		if ((sender instanceof ProxiedPlayer)) {
			ProxiedPlayer p = (ProxiedPlayer)sender;
			if(args.length>0) {
				if(Db.getPpl(p.getName(), "")) {
					p.sendMessage(new ComponentBuilder("Already registered.").color(ChatColor.RED).create());
				}else{
					Db.setPpl(p.getName(), args[0]);
					Db.unsetLogp(p.getName());
					Db.ison(p.getName(), true);
				p.sendMessage(new ComponentBuilder("Registered!").color(ChatColor.GREEN).create());
				// Db.unsetLogp(p.getName());
				String servur = Db.getsrv(p.getName());
				String ip;
				if(servur != "") {
					p.connect(ProxyServer.getInstance().getServerInfo(servur));
				}else if((ip=p.getPendingConnection().getVirtualHost().getHostString()) != null && Db.alias.containsKey(ip) /*&& (App.forceIP||Db.ipforce.containsKey(ip))*/ ) {
					p.connect(ProxyServer.getInstance().getServerInfo(Db.alias.get(ip)));
				}else if(MyListener.hub2!="") {
					p.connect(ProxyServer.getInstance().getServerInfo(MyListener.hub2));
				}
				}
			}else{p.sendMessage(new ComponentBuilder("/register <password>").color(ChatColor.RED).create());}
		}
	}
}
