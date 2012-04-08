package com.mcheaven.zombiecraft;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

public class ZombieSpawner implements Runnable {
	private final Zombiecraft plugin;
	private final String player;
	private int counter = 0;
	private int pid;
	private final Random rand = new Random();

	public ZombieSpawner(Zombiecraft plugin, String player) {
		this.plugin = plugin;
		this.player = player;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public void run() {
		Player p = plugin.getServer().getPlayerExact(player); // Get the Plyer
																// from its name
		if (p == null) // If the Player is null he's offline. Stop the task.
		{
			plugin.getServer().getScheduler().cancelTask(pid);
			return;
		}
		Location loc = p.getLocation(); // get the players location
		// A bit random magic so the zombie spawns near the player...
		if (rand.nextBoolean())
			loc.setX(loc.getX() + rand.nextInt(20));
		else
			loc.setX(loc.getX() - rand.nextInt(20));
		if (rand.nextBoolean())
			loc.setZ(loc.getZ() + rand.nextInt(20));
		else
			loc.setZ(loc.getZ() - rand.nextInt(20));
		Block block = loc.getBlock(); // and the block
		while (block.getType() != Material.AIR
				&& block.getRelative(BlockFace.UP).getType() != Material.AIR) // While
																				// a
																				// zombie
																				// would
																				// spawn
																				// in
																				// non
																				// air
																				// blocks...
		{
			loc = p.getLocation(); // Do the random again
			if (rand.nextBoolean())
				loc.setX(loc.getX() + rand.nextInt(20));
			else
				loc.setX(loc.getX() - rand.nextInt(20));
			if (rand.nextBoolean())
				loc.setZ(loc.getZ() + rand.nextInt(20));
			else
				loc.setZ(loc.getZ() - rand.nextInt(20));
			block = loc.getBlock();
		}
		World world = loc.getWorld();
		Zombie zombie = (Zombie) world.spawnCreature(loc, EntityType.ZOMBIE);
		zombie.setTarget(p); // let the zombie attack the player
		world.strikeLightningEffect(loc);
		counter++;
		if (counter > 100) // Stop after 100 zombies
			plugin.getServer().getScheduler().cancelTask(pid);
	}
}