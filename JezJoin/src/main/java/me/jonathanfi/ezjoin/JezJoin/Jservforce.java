package me.jonathanfi.ezjoin.JezJoin;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Jservforce extends Command {
	public Jservforce() {
		super("Jservforce");
	}
	public void execute(CommandSender sender, String[] args) {
		if ((sender instanceof ProxiedPlayer)) {
			sender.sendMessage(new ComponentBuilder("This command is for console.").color(ChatColor.GOLD).create());
		}else{
			if(args.length==2) {
				Db.ipforce.put(args[0], true);
				System.out.println("[JezJoin] Set "+args[0]+" to force");
			}else if(args.length==1){
				Db.ipforce.remove(args[0]);
				System.out.println("[JezJoin] Removed "+args[0]+" from force");
			}else{
				System.out.println("[JezJoin] /jservforce <ip> <force>");
			}
		}
	}
}
