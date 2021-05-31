package me.jonathanfi.ezjoin.JezJoin;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Login extends Command {
	public Login() {
		super("login");
	}

	public void execute(CommandSender sender, String[] args) {
		if ((sender instanceof ProxiedPlayer)) {
			ProxiedPlayer p = (ProxiedPlayer)sender;
			if(args.length>0) {
				if(Db.getPpl(p.getName(), args[0])) {Db.unsetLogp(p.getName());
				p.connect(ProxyServer.getInstance().getServerInfo(Db.getsrv(p.getName())));
				Db.setsrv(p.getName(),"");
				p.sendMessage(new ComponentBuilder("Logged in!").color(ChatColor.AQUA).create());}else{
					p.sendMessage(new ComponentBuilder("Wrong password.").color(ChatColor.RED).create());
				}
			}else {p.sendMessage(new ComponentBuilder("/login <password>").color(ChatColor.RED).create());}
		}
	}
}
