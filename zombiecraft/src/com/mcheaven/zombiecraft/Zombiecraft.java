package com.mcheaven.zombiecraft;

import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Zombiecraft extends JavaPlugin {
	@Override
	public void onEnable() {
		Server server = getServer();
		server.getPluginManager().registerEvents(new ZombiecraftListener(),
				this);
		saveDefaultConfig();
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("zombiecraft")) {
			Player player = (Player) sender;
			if (player.hasPermission("zombiecraft.main"))
				;
			{
				Location loc = player.getLocation();
				World world = loc.getWorld();
				world.setSpawnFlags(false, false);
				ZombieSpawner spawner = new ZombieSpawner(this,
						player.getName());
				spawner.setPid(getServer().getScheduler()
						.scheduleSyncRepeatingTask(this, spawner, 0L, 20L));
				world.setTime(getConfig().getInt("ChangeTimeTo"));
				world.setStorm(getConfig().getBoolean("ChangeWeather"));
				world.setDifficulty(Difficulty.HARD);
			}
			return true;
		}
		return false;
	}

}
