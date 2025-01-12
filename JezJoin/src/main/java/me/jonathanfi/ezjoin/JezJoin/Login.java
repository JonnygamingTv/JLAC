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
				if(Db.getPpl(p.getName(), args[0])) {
					Db.unsetLogp(p.getName());
					Db.ison(p.getName(), true);
				p.sendMessage(new ComponentBuilder("Logged in!").color(ChatColor.AQUA).create());
				String servur = Db.getsrv(p.getName());
				if(servur != "") {
					p.connect(ProxyServer.getInstance().getServerInfo(servur));
				}else {
				String ip;
				if((ip=p.getPendingConnection().getVirtualHost().getHostString()) != null) {
		    		if(Db.alias.containsKey(ip)) {
		    			//if(App.forceIP||Db.ipforce.containsKey(ip)) {
		    				servur = Db.alias.get(ip);
		    			//}
		    		}
		    	}
				if(MyListener.hub3!="" && servur == "") {
					servur = MyListener.hub3;
				}
				if(servur != "") p.connect(ProxyServer.getInstance().getServerInfo(servur));
				}
				}else{
					p.sendMessage(new ComponentBuilder("Wrong password.").color(ChatColor.RED).create());
					if(App.lTri>0){
						if(Db.tri(p.getName(),true)>App.lTri){
						p.disconnect();
						}
					}
				}
			}else {p.sendMessage(new ComponentBuilder("/login <password>").color(ChatColor.RED).create());}
		}
	}
}
