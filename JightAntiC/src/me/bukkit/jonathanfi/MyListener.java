package me.bukkit.jonathanfi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
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
import org.bukkit.event.entity.PlayerDeathEvent;

public class MyListener extends Thread implements Listener {
	private static Map<String, Location> PlayerData = new HashMap<String, Location>();//List<Location>
	private static Map<String, List<Long>> PlastB = new HashMap<String, List<Long>>();
	private static Map<String, List<Object>> PlayerDPS = new HashMap<String, List<Object>>();
	//public static Object PObj = new Object();
	@EventHandler (priority = EventPriority.LOWEST)
	public void onPlayerMove(PlayerMoveEvent event) {
		final String player = event.getPlayer().getName();
		Location loc = event.getPlayer().getLocation();
		if(!event.getPlayer().hasPermission("jla.cert")) {
		if(PlayerData.get(player) != null) {
			if(event.getPlayer().getAllowFlight() != true) {final Location Lloc = (Location) PlayerData.get(player);Boolean cancel = true;
			Calendar c1 = Calendar.getInstance();
			Date Dnow = c1.getTime(); 
			if(JLA.aflight) {Long mayCheat=(long)0;
				if(loc.getBlockY()>Lloc.getBlockY()) {
					if(!PlastB.containsKey(player)) {
						List<Long>tmp = new ArrayList<Long>();
						tmp.add((long)1);
						tmp.add((long)Dnow.getTime());
						tmp.add((long)0);
						PlastB.put(player, tmp);
					}
					PlastB.get(player).set(0, (long) 1);
					PlastB.get(player).set(1, Dnow.getTime());
					}//else if(loc.getBlockY()==Lloc.getBlockY() && loc.getWorld().getBlockAt(loc.getBlockX(),loc.getBlockY()-1,loc.getBlockZ()).getType() == Material.AIR){event.setCancelled(true);JLA.action("Flight detected?", event.getPlayer());}//Lloc.getBlockY()-loc.getBlockY()
				if(PlastB.get(player)!=null && PlastB.get(player).get(0)!=null) {
					PlastB.get(player).set(2,(PlastB.get(player).get(2)!=null?PlastB.get(player).get(2):0)+Lloc.getBlockY()-loc.getBlockY());
					if(PlastB.get(player).get(2)!=null && Math.round(PlastB.get(player).get(2))<=0){
						PlastB.get(player).set(0, null);
					}else{
						mayCheat=PlastB.get(player).get(2);
						if((long)Dnow.getTime()-PlastB.get(player).get(1)>300) {mayCheat=mayCheat+1;}
					}}else if(PlastB.containsKey(player)){PlastB.remove(player);}
				 if(loc.distance(Lloc)>0.1) {
					if(loc.getBlockY()>Lloc.getBlockY()+0.3) if(event.getPlayer().getInventory().getChestplate() != null && event.getPlayer().getInventory().getChestplate().getType() != Material.ELYTRA || (!event.getPlayer().hasPotionEffect(PotionEffectType.JUMP) || !event.getPlayer().hasPotionEffect(PotionEffectType.LEVITATION))) {
						boolean blockNear = false;int offset=2;
						try {
						org.bukkit.potion.PotionEffect speed = event.getPlayer().getPotionEffect(PotionEffectType.SPEED);
						if(speed!=null)if(speed.getAmplifier()>0)offset+=Math.round(speed.getAmplifier()/1000);
						}catch(NoSuchMethodError e) {}
						for(int i=-offset;i<offset;i++) {
							if(Lloc.getWorld().getBlockAt(Lloc.getBlockX()-i,Lloc.getBlockY()-1,loc.getBlockZ()-i).getType() != Material.AIR) {blockNear = true;}
						}
						 if(loc.getWorld().getBlockAt(loc.getBlockX(),(int)(loc.getBlockY()-loc.distance(Lloc)),loc.getBlockZ()).getType() == Material.AIR && Lloc.getWorld().getBlockAt(Lloc.getBlockX(),Lloc.getBlockY()-1,Lloc.getBlockZ()).getType() == Material.AIR && !blockNear) {event.setCancelled(true);if(JLA.log)System.out.println(player+" AntiFlight1");JLA.action("Stop flycheating!", event.getPlayer());}else{cancel = false;int airs = 0;for(int i=0; i < 2; i++){if(loc.getWorld().getBlockAt(loc.getBlockX(),loc.getBlockY()-i,loc.getBlockZ()).getType() != Material.AIR) {cancel=false;}else{airs = airs + 1;}}if(airs>3) {JLA.action("\nPlease stop!",event.getPlayer());}else if(airs > 2) {cancel=true;}if(cancel&&mayCheat>1) {event.setCancelled(cancel);if(JLA.log)System.out.println(player+" AntiFlight2");}}
					}
					try {if(event.getPlayer().getPassengers() != null && event.getPlayer().getPassengers().size()>0) {if(JLA.log)System.out.println(event.getPlayer().getPassengers());}}catch(NoSuchMethodError e) {}
				 }
			}
			if(JLA.atp) {cancel = false;try {
				if(loc.distance(Lloc)>2 && !(event.getPlayer().hasPotionEffect(PotionEffectType.JUMP)||event.getPlayer().hasPotionEffect(PotionEffectType.LEVITATION))) {cancel = true;
					if(loc.getWorld().getBlockAt(loc.getBlockX(),loc.getBlockY(),loc.getBlockZ()).getType() == Material.AIR && Lloc.getWorld().getBlockAt(loc.getBlockX(),Lloc.getBlockY(),Lloc.getBlockZ()).getType() == Material.AIR) {cancel = false;}
					if(loc.getBlockY() < Lloc.getBlockY()-1)for(int i=0;i < Lloc.getBlockY()-loc.getBlockY(); i++) {if(Lloc.getWorld().getBlockAt(Lloc.getBlockX(),Lloc.getBlockY()-i,Lloc.getBlockZ()).getType() != Material.AIR) {cancel = true;}}
					//Location loca = new Location(Lloc.getWorld(), Lloc.getBlockX(), Lloc.getBlockY(), Lloc.getBlockZ());
					//event.getPlayer().teleport(loca);
				}}catch(NoSuchFieldError e) {}
				if(cancel) {loc = Lloc;if(loc.distance(Lloc)>5) {event.getPlayer().teleport(Lloc);PlayerData.remove(player);}else{event.setCancelled(cancel);}JLA.action("\n§6Please stop!", event.getPlayer());PlayerData.remove(player);if(JLA.log)System.out.println("AntiTP for: "+player);}
			}
			if(JLA.aliq>0) {
				if(PlayerDPS.get(player)!=null) {if(PlayerDPS.get(player).get(3) != null) {}else{PlayerDPS.get(player).set(3, (float)Dnow.getTime());}}else{List<Object>tmp = new ArrayList<Object>();
				tmp.add((float)loc.distance(Lloc));
				tmp.add((float)Dnow.getTime());
				tmp.add((float)Dnow.getTime());
				tmp.add((float)Dnow.getTime());
				PlayerDPS.put(player, tmp);}
				//System.out.println(loc.getWorld().getBlockAt(loc.getBlockX(),loc.getBlockY(),loc.getBlockZ()).getType()+":"+loc.getWorld().getBlockAt(loc.getBlockX(),loc.getBlockY()-1,loc.getBlockZ()).getType());
				if(loc.getWorld().getBlockAt(loc.getBlockX(),loc.getBlockY(),loc.getBlockZ()).getType() == Material.WATER) {PlayerDPS.get(player).set(3, (float)Dnow.getTime());}else if(loc.getWorld().getBlockAt(loc.getBlockX(),loc.getBlockY()-1,loc.getBlockZ()).getType() == Material.WATER && (float)Dnow.getTime() - (float)PlayerDPS.get(player).get(3)>2000) {event.getPlayer().teleport(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY()-JLA.aliq, loc.getBlockZ()));PlayerDPS.get(player).set(3, Dnow.getTime());}
			}
			if(cancel != true && JLA.bmps>0) {
				if(PlayerDPS.get(player) != null) {
					Float d = (float) PlayerDPS.get(player).get(0);
					d = d + (float) loc.distance(Lloc);
					Float Ts = (float) PlayerDPS.get(player).get(1);
					if(Dnow.getTime() - Ts > 1000) {Ts = (float)Dnow.getTime();d=(float) 0;}
					float dps = d/Ts;
					if(dps > JLA.bmps) {
						event.setCancelled(true);
						if(JLA.log)System.out.println("AntiDPS for: "+player);
					}
					PlayerDPS.get(player).set(0, d);
					PlayerDPS.get(player).set(1, Ts);
				}else{
					List<Object>tmp = new ArrayList<Object>();
					tmp.add((float)loc.distance(Lloc));
					tmp.add((float)Dnow.getTime());
					PlayerDPS.put(player, tmp);
				}
			}
			}
		}
		PlayerData.put(player,loc);//PlayerData.get(player).set(0, loc);
		//PlayerData.player[(int) player] = event.getPlayer().canSee(arg0);
		}
	}
	public void onTeleport(PlayerTeleportEvent e) {
		final String player = e.getPlayer().getName();
		PlayerData.remove(player);
	}
	public void onDeath(PlayerDeathEvent e) {
		final String player = e.getEntity().getName();PlayerData.remove(player);PCD.remove(player);PlastB.remove(player);PlayerDPS.remove(player);
	}
	public static Map<String, Long> PCD = new HashMap<String, Long>();
	@EventHandler (priority = EventPriority.LOWEST)
	public void onPlayerDmg(EntityDamageByEntityEvent event) {
		if(event.getEntity().getType() == EntityType.PLAYER) {if(event.getEntity().isDead()){final String player = event.getEntity().getName();
			PlayerData.remove(player);PCD.remove(player);PlastB.remove(player);PlayerDPS.remove(player);}}
		if(event.getDamager().getType() == EntityType.PLAYER) {
			if(!event.getDamager().hasPermission("jla.cert")) {
			if(JLA.ar>0) {
				if(event.getDamager().getLocation().distance(event.getEntity().getLocation())>JLA.ar){
					event.setCancelled(true);
					if(JLA.log)System.out.println(event.getDamager().getName()+" is using Reach?");
				}
			}
			if(JLA.aka>0) {//thanks https://bukkit.org/threads/get-if-player-is-looking-at-an-entity.106661/!
				Player player = (Player) event.getDamager();
				if(JLA.aka==1) {if(player.getEyeLocation() != event.getEntity().getLocation()) {event.setCancelled(true);JLA.action("\nPlease stop with Aura!",player);}}else if(JLA.aka==2) {
					
				}
			}
			//if(event.getDamager().getLocation() > event.getEntity().getLocation()) {}
			if(event.getEntity().getType() == EntityType.PLAYER && JLA.acl != 0) {
				final String player = event.getEntity().getName();
				Calendar c1 = Calendar.getInstance();
				Date Dnow = c1.getTime();
				PCD.put(player, Dnow.getTime());
			}
			}
		}
	}
	@EventHandler (priority = EventPriority.LOWEST)
	public void onPlayerLeave1(PlayerKickEvent event) {
		final String player = event.getPlayer().getName();
		if(PlayerData.get(player) != null) {
			PlayerData.remove(player);
		}
		if(PlayerDPS.get(player) != null) {
			PlayerDPS.remove(player);
		}PDD.remove(player);
		if(PlastB.containsKey(player))PlastB.remove(player);
		if(PCD.get(player) != null && JLA.acl != 0) {
			Calendar c1 = Calendar.getInstance();
			Date Dnow = c1.getTime();
			if(Dnow.getTime() - PCD.get(player) < JLA.acl) {
				event.getPlayer().setHealth(0);
				PCD.remove(player);
			}
		}
	}
	public static Map<String, Long> PDD = new HashMap<String, Long>();
	@EventHandler (priority = EventPriority.LOWEST)
	public void onPDrop(PlayerDropItemEvent event) {
		if(!event.getPlayer().hasPermission("jla.cert")) {
		if(JLA.ais!=-1) {
			if(JLA.ais != 0) {
			final String player = event.getPlayer().getName();
			Calendar c1 = Calendar.getInstance();
			Date Dnow = c1.getTime(); 
			if(PDD.get(player) != null){
				if(JLA.ais > (Dnow.getTime() - PDD.get(player))) {event.setCancelled(true);return;}
			}
			PDD.put(player,Dnow.getTime());
		}
		}else{event.setCancelled(true);}
		}
	}
	@EventHandler (priority = EventPriority.LOW)
	public void onPConsume(PlayerItemConsumeEvent event) {
		
	}
}
