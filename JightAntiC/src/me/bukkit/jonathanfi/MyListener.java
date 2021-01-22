package me.bukkit.jonathanfi;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class MyListener extends Thread implements Listener {
	public static Map<String, Location> PlayerData = new HashMap<String, Location>();//List<Location>
	@EventHandler (priority = EventPriority.LOWEST)
	public void onPlayerMove(PlayerMoveEvent event) {
		final String player = event.getPlayer().getName();
		Location loc = event.getPlayer().getLocation();
		if(PlayerData.get(player) != null) {
			if(event.getPlayer().getAllowFlight() != true) {final Location Lloc = (Location) PlayerData.get(player);System.out.println(loc.distance(Lloc));
			boolean cancel = false;
			if(JLA.aflight) {
				 if(loc.distance(Lloc)>0.8) {
					if(loc.getBlockY()>Lloc.getBlockY()) if(event.getPlayer().getInventory().getChestplate() != null && event.getPlayer().getInventory().getChestplate().getType() != Material.ELYTRA || (!event.getPlayer().hasPotionEffect(PotionEffectType.JUMP) || !event.getPlayer().hasPotionEffect(PotionEffectType.LEVITATION))) {
						 if(loc.getWorld().getBlockAt(loc.getBlockX(),loc.getBlockY()-1,loc.getBlockZ()).getType() == Material.AIR && Lloc.getWorld().getBlockAt(Lloc.getBlockX(),Lloc.getBlockY()-1,Lloc.getBlockZ()).getType() == Material.AIR) {event.setCancelled(true);}
					 }
				 }
			}
			if(JLA.atp) {
				if(loc.distance(Lloc)>2) {cancel = true;
					if(loc.getWorld().getBlockAt(loc.getBlockX(),loc.getBlockY(),loc.getBlockZ()).getType() == Material.AIR && Lloc.getWorld().getBlockAt(loc.getBlockX(),Lloc.getBlockY(),Lloc.getBlockZ()).getType() == Material.AIR) {cancel = false;}
					if(loc.getBlockY() < Lloc.getBlockY()-1)for(int i=0;i < Lloc.getBlockY()-loc.getBlockY(); i++) {if(Lloc.getWorld().getBlockAt(Lloc.getBlockX(),Lloc.getBlockY()-i,Lloc.getBlockZ()).getType() != Material.AIR) {cancel = true;}}
					//Location loca = new Location(Lloc.getWorld(), Lloc.getBlockX(), Lloc.getBlockY(), Lloc.getBlockZ());
					//event.getPlayer().teleport(loca);
					if(event.getPlayer().hasPotionEffect(PotionEffectType.JUMP)) {cancel=false;}
				}
				if(cancel) {loc = Lloc;event.setCancelled(cancel);if(loc.distance(Lloc)>5)event.getPlayer().teleport(Lloc);if(JLA.log)System.out.println("AntiTP for: "+player);}
			}
			}
		}
		PlayerData.put(player,loc);//PlayerData.get(player).set(0, loc);
		//PlayerData.player[(int) player] = event.getPlayer().canSee(arg0);
	}
	public void onTeleport(PlayerTeleportEvent e) {
		final String player = e.getPlayer().getName();
		if(PlayerData.get(player) != null) {
			PlayerData.remove(player);
		}
	}
	public static Map<String, List<Long>> PCD = new HashMap<String, List<Long>>();
	@EventHandler (priority = EventPriority.LOWEST)
	public void onPlayerDmg(EntityDamageByEntityEvent event) {
		if(event.getDamager().getType() == EntityType.PLAYER) {
			if(JLA.ar) {
				if(event.getDamager().getLocation().distance(event.getEntity().getLocation())>6){
					event.setCancelled(true);
					if(JLA.log)System.out.println(event.getDamager().getName()+" is using Reach?");
				}
			}
			//if(event.getDamager().getLocation() > event.getEntity().getLocation()) {}
			if(event.getEntity().getType() == EntityType.PLAYER && JLA.acl != 0) {
				final String player = event.getEntity().getName();
				Calendar c1 = Calendar.getInstance();
				Date Dnow = c1.getTime(); 
				if(PCD.get(player) == null){PCD.get(player).add(Dnow.getTime());}
				PCD.get(player).set(0, Dnow.getTime());
			}
		}
	}
	@EventHandler (priority = EventPriority.LOWEST)
	public void onPlayerLeave1(PlayerKickEvent event) {
		final String player = event.getPlayer().getName();
		if(PlayerData.get(player) != null) {
			PlayerData.remove(player);
		}
		if(PCD.containsKey(player) && PCD.get(player) != null && JLA.acl != 0) {
			Calendar c1 = Calendar.getInstance();
			Date Dnow = c1.getTime();
			if(Dnow.getTime() - PCD.get(player).get(0) < JLA.acl) {
				event.getPlayer().setHealth(0);
			}
		}
	}
	public static Map<String, List<Long>> PDD = new HashMap<String, List<Long>>();
	@EventHandler (priority = EventPriority.LOWEST)
	public void onPDrop(PlayerDropItemEvent event) {
		if(JLA.ais != 0) {
			final String player = event.getPlayer().getName();
			Calendar c1 = Calendar.getInstance();
			Date Dnow = c1.getTime(); 
			if(PDD.containsKey(player) && PDD.get(player) != null) {if(PDD.get(player).get(0) != null){
				if(JLA.ais > (Dnow.getTime() - PDD.get(player).get(0))) {event.setCancelled(true);return;}
			}}if(PDD.get(player) == null){PDD.get(player).add(Dnow.getTime());}
			PDD.get(player).set(0, Dnow.getTime());
		}
	}
	@EventHandler (priority = EventPriority.LOW)
	public void onPConsume(PlayerItemConsumeEvent event) {
		
	}
}
