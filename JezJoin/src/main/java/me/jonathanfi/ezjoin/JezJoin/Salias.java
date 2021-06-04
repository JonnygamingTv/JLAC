package me.jonathanfi.ezjoin.JezJoin;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
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
			if(args.length==2) {
				Db.alias.put(args[0], args[1]);
				System.out.println("[JezJoin] Set "+args[0]+" to send to "+args[1]+"!");
			}else {
				System.out.println("[JezJoin] /salias <ip> <Server>");
			}
		}
	}
}
