package me.bukkit.jonathanfi;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class JLA extends JavaPlugin {
	FileConfiguration config = getConfig();
	public static boolean aflight = false;
	public static boolean akb = false;
	public static boolean aka = false;
	public static boolean ar = false;
	public static boolean atp = false;
	public static int ais = 0;
	public static int acl = 0;
	public static float bmps = 5;
	public static boolean log = false;
	public void onEnable() {
		getLogger().info("JLAC loaded!");
		config.addDefault("antiFlight", false);
		config.addDefault("antiTP", false);
		config.addDefault("antiKnockback", false);
		config.addDefault("antiAura", false);
		config.addDefault("antiReach", false);
		config.addDefault("antiCombatLeave", 5000);
		config.addDefault("blocksMovePerSecond", 5);
		config.addDefault("#Time", "Milliseconds (1 MS = 0.001 S)");
		config.addDefault("antiItemSpam", 100);
		config.addDefault("#Actions", "log, warn, kick, ban (or leave empty for just preventing, you can also type just like here to make a scheme for each time cheats are detected)");
		config.addDefault("action", "log");
		config.addDefault("DetectMessage", "§c§lCheats detected!");
		config.addDefault("async", false);
		config.options().copyDefaults(true);
		saveConfig();
		if(config.getBoolean("antiFlight")) {aflight = true;getLogger().info("AntiFlight");}
		if(config.getBoolean("antiKnockback")) {akb = true;getLogger().info("AntiKB");}
		if(config.getBoolean("antiAura")) {aka = true;getLogger().info("AntiAura");}
		if(config.getBoolean("antiReach")) {ar = true;getLogger().info("AntiReach");}
		if(config.getBoolean("antiTP")) {atp = true;}
		if(config.getInt("antiItemSpam") > 0) {ais = config.getInt("antiItemSpam");}
		getLogger().info("AntiItemSpam="+ais);
		if(config.getInt("antiCombatLeave") > 0) {acl = config.getInt("antiCombatLeave");getLogger().info("AntiCombatLeave="+acl);}
		if(config.getInt("blocksMovePerSecond")>0) {bmps=config.getInt("blocksMovePerSecond");}
		if(config.getString("action") == "log") {log = true;getLogger().info(log?"Log":"Don't log");}else if(config.getString("action")!="") {
			//String actions = config.getString("action");
			//for(int i=0; i<actions.length();i++) {if(actions.(i, i+1) == ",") {}}
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
			if(args[0] != null)if(args[1] != null)if(args[0] == "itemspam"){ais = Integer.parseInt(args[1]);}
			sender.sendMessage("/jla itemspam <cooldown>");
			return true;
		}
		return false;
	}
	public void action(String d, Player p) {
		if(config.getString("action") == "log") {System.out.println(d);}else if(config.getString("action") == "warn") {}else if(config.getString("action") == "kick") {p.kickPlayer(config.getString("DetectMessage"));}
	}
}
