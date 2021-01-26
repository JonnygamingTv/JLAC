package me.bukkit.jonathanfi;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class JLA extends JavaPlugin {
	FileConfiguration config = getConfig();
	public static int aflight = 0;
	public static boolean akb = false;
	public static int aka = 0;
	public static float ar = 0;
	public static boolean atp = false;
	public static float aliq = 0; 
	public static int ais = 0;
	public static int acl = 0;
	public static float bmps = 5;
	private static String dtcMsg = "";
	private static String bancmd = "";
	public static boolean log = false;
	private static Map<String, List<String>> actionList = new HashMap<String, List<String>>();
	public void onEnable() {
		getLogger().info("JLAC loaded!");
		config.addDefault("antiFlight", 0);
		config.addDefault("antiTP", false);
		config.addDefault("antiLiquid", 0.1);
		config.addDefault("antiKnockback", false);
		config.addDefault("antiAura", 1);
		config.addDefault("antiReach", 6);
		config.addDefault("antiCombatLeave", 5000);
		config.addDefault("blocksMovePerSecond", 5);
		config.addDefault("#Time", "Milliseconds (1 MS = 0.001 S)");
		config.addDefault("antiItemSpam", 100);
		config.addDefault("#", "0 = ignore, -1 = don't let anyone drop items");
		config.addDefault("#Actions", "log, warn, kick, ban (or leave empty for just preventing, you can also type just like here to make a scheme for each time cheats are detected)");
		config.addDefault("action", "log, warn");
		config.addDefault("DetectMessage", "§c§lCheats detected!");
		config.addDefault("bancmd", "ban %player% §fPlease turn off your hacks!");
		config.addDefault("async", false);
		config.options().copyDefaults(true);
		saveConfig();
		if(config.getString("DetectMessage")!="")dtcMsg=config.getString("DetectMessage");
		if(config.getString("bancmd")!="")bancmd=config.getString("bancmd");
		if(config.getInt("antiFlight")>0) {aflight = config.getInt("antiFlight");getLogger().info("AntiFlight="+aflight);}
		if(config.getBoolean("antiKnockback")) {akb = true;getLogger().info("AntiKB");}
		if(config.getBoolean("antiAura")) {aka = config.getInt("antiAura");getLogger().info("AntiAura");}
		if(config.getBoolean("antiReach")) {ar = config.getInt("antiReach");getLogger().info("AntiReach");}
		if(config.getBoolean("antiTP")) {atp = true;}
		if(config.getInt("antiItemSpam") > 0) {ais = config.getInt("antiItemSpam");}
		if(config.getString("antiLiquid")!="") {aliq=Float.parseFloat(config.getString("antiLiquid"));getLogger().info("AntiLiq="+aliq);}
		if(config.getInt("antiCombatLeave") > 0) {acl = config.getInt("antiCombatLeave");getLogger().info("AntiCombatLeave="+acl);}
		if(config.getInt("blocksMovePerSecond")>0) {bmps=config.getInt("blocksMovePerSecond");}
		if(config.getString("action") == "log") {log = true;getLogger().info(log?"Log":"Don't log");}else if(config.getString("action")!="") {
			List<String>actions = Arrays.asList(config.getString("action").split(","));
			actionList.put("-action",actions);
		}
		getLogger().info(config.getString("action"));
		if(atp)getLogger().info("AntiTP");
		if(config.getBoolean("async")) {
			getLogger().info("ASync is not available yet.");
		}else {
			getLogger().info("Running Sync.");
			getServer().getPluginManager().registerEvents(new MyListener(), this);
		}
	}
	public void onDisable() {
		getLogger().info("JLAC Unloaded :(");
	}
	public boolean onCommand(CommandSender sender,
            Command command,
            String label,
            String[] args) {
		if (command.getName().equalsIgnoreCase("jla")) {
			for(int i=0;i<args.length;i++){System.out.println(i+": "+args[i]);}
			if(args.length>0 && args != null)if(args[0] != null)if(args[0].equalsIgnoreCase("itemspam")){if(args.length==2) {ais = Integer.parseInt(args[1]);}else{sender.sendMessage(String.valueOf(ais));}}else if(args[0].equalsIgnoreCase("combatleave")){if(args.length==2) {acl = Integer.parseInt(args[1]);}else{sender.sendMessage(String.valueOf(acl));return true;}}else if(args[0].equalsIgnoreCase("liq")) {if(args[1] != null) {aliq=(float)Float.parseFloat(args[1]);}else{sender.sendMessage(String.valueOf(aliq));return true;}}else 
				if(args[0].equalsIgnoreCase("reload")) {config = getConfig();
				if(config.getString("DetectMessage")!="")dtcMsg=config.getString("DetectMessage");
				if(config.getString("bancmd")!="")bancmd=config.getString("bancmd");
				if(config.getInt("antiFlight")>0) {aflight = config.getInt("antiFlight");getLogger().info("AntiFlight="+aflight);}
				if(config.getBoolean("antiKnockback")) {akb = true;getLogger().info("AntiKB");}
				if(config.getBoolean("antiAura")) {aka = config.getInt("antiAura");getLogger().info("AntiAura");}
				if(config.getBoolean("antiReach")) {ar = config.getInt("antiReach");getLogger().info("AntiReach");}
				if(config.getBoolean("antiTP")) {atp = true;}
				if(config.getInt("antiItemSpam") > 0) {ais = config.getInt("antiItemSpam");}
				if(config.getString("antiLiquid")!="") {aliq=Float.parseFloat(config.getString("antiLiquid"));getLogger().info("AntiLiq="+aliq);}
				if(config.getInt("antiCombatLeave") > 0) {acl = config.getInt("antiCombatLeave");getLogger().info("AntiCombatLeave="+acl);}
				if(config.getInt("blocksMovePerSecond")>0) {bmps=config.getInt("blocksMovePerSecond");}
				if(config.getString("action") == "log") {log = true;getLogger().info(log?"Log":"Don't log");}else if(config.getString("action")!="") {
					List<String>actions = Arrays.asList(config.getString("action").split(","));
					actionList.put("-action",actions);
				}
				}
			sender.sendMessage("§a§lJLA\nby §4jonathanfi\n \n§b/jla itemspam <cooldown ms>§c\n/jla CombatLeave <cooldown ms>\n§6/jla liq <blocks>\n§f \n§d1 ms = 0.001 seconds");
			return true;
		}
		return false;
	}
	public static void action(String d, Player p) {
		//if(config.getString("action") == "log") {System.out.println(d);}else if(config.getString("action") == "kick") {p.kickPlayer(config.getString("DetectMessage"));}
		if(actionList.get("-action") != null) {String warn = "0";
		if(actionList.get(p.getName())!=null) {warn = actionList.get(p.getName()).get(0);}int warns = Integer.parseInt(warn);if(warns>actionList.get("-action").size())warns=warns-1;
		if(actionList.get("-action").get(warns)!=null){}else{warns = warns-1;}
		if(actionList.get(p.getName()) != null) {
		actionList.get(p.getName()).set(0,String.valueOf(warns));}else {List<String>tmp = new ArrayList<String>();
		tmp.add(String.valueOf(warns));
			actionList.put(p.getName(),tmp);}
		switch(actionList.get("-action").get(warns)) {
		case "log":
			System.out.println(d);
			break;
		case "warn":
			p.sendMessage(dtcMsg+d);
			break;
		case "kick":
			p.kickPlayer(dtcMsg);
			break;
		case "ban":
			p.performCommand(bancmd.replace("%player%", p.getName()));
			break;
			default:p.performCommand(actionList.get("-action").get(warns).replace("%player%", p.getName()));
		}}
	}
}
