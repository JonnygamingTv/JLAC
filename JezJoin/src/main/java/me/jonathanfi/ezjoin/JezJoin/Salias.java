package me.jonathanfi.ezjoin.JezJoin;

import java.net.InetSocketAddress;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Salias extends Command {
	public Salias() {
		super("salias");
	}
	public void execute(CommandSender sender, String[] args) {
		if ((sender instanceof ProxiedPlayer)) {
			sender.sendMessage(new ComponentBuilder("This command is for console.").color(ChatColor.GOLD).create());
		}else{
			if(args.length>2) {
				System.out.println("[JezJoin] /salias <name> <ip> <port> [restrict]\nServer name: "+args[0]+"\nServer IP: "+args[1]+"\nServer port: "+args[2]);
				try {
				InetSocketAddress socketAddress = new InetSocketAddress(args[1], Integer.parseInt(args[2]));
				ServerInfo info = ProxyServer.getInstance().constructServerInfo(args[0], socketAddress, "JonHosting.com", (args[3]!=null?true:false));
				ProxyServer.getInstance().getServers().put(args[0], info);
				}catch(Exception er) {er.printStackTrace();}
			}else if(args.length==2) {
				Db.alias.put(args[0], args[1]);
				System.out.println("[JezJoin] Set "+args[0]+" to send to "+args[1]+"!");
			}else {
				System.out.println("[JezJoin] /salias <ip> <Server>");
			}
		}
	}
}
