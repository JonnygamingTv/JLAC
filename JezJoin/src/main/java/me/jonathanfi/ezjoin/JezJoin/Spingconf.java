package me.jonathanfi.ezjoin.JezJoin;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Spingconf extends Command {
	public Spingconf() {
		super("Spingconf");
	}
	public void execute(CommandSender sender, String[] args) {
		if ((sender instanceof ProxiedPlayer)) {
			sender.sendMessage(new ComponentBuilder("This command is for console.").color(ChatColor.GOLD).create());
		}else{
			if(args.length>3) {
				System.out.println("Set ping config for IP: "+args[0]);
				if(args[1]=="true") {
					Db.pingP.put(args[0], true);
				}else {
					Db.pingP.remove(args[0]);
				}
				if(args[2]=="true") {
					Db.pingFavi.put(args[0], true);
				}else {
					Db.pingFavi.remove(args[0]);
				}
				if(args[3]=="true") {
					Db.pingMotD.put(args[0], true);
				}else {
					Db.pingMotD.remove(args[0]);
				}
				if(args.length>4) {if(args[4]!="") {Db.pingF.put(args[0],args[4]);}else{Db.pingF.remove(args[0]);}}
				
			}else{
				System.out.println("[JezJoin] /Spingconf <ip> <playercount?> <icon?> <motd?> [message]");
			}
		}
	}
}
