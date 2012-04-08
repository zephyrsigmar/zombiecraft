package com.mcheaven.zombiecraft;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class ZombiecraftListener implements Listener {

	public static Zombiecraft plugin;

	public ZombiecraftListener(Zombiecraft instance) {
		plugin = instance;
	}

	public ZombiecraftListener() {
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void playerTot(PlayerDeathEvent event) {
		Player player = event.getEntity();
		EntityDamageEvent damageEvent = player.getLastDamageCause();
		if (!(damageEvent instanceof EntityDamageByEntityEvent)
				|| !(((EntityDamageByEntityEvent) damageEvent).getDamager() instanceof Zombie))
			return;
		Location loc = player.getLocation();
		World world = loc.getWorld();
		if (world.getName().equals("zombieland")) {
			for (int i = 0; i < 1; i++)
				world.spawnCreature(loc, EntityType.ZOMBIE);
		}
	}
}
