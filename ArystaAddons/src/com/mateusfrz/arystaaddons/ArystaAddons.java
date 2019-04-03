package com.mateusfrz.arystaaddons;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.mateusfrz.arystaaddons.commands.ChestViewerCmd;
import com.mateusfrz.arystaaddons.commands.ChunkViewerCmd;
import com.mateusfrz.arystaaddons.commands.ConfigCmd;
import com.mateusfrz.arystaaddons.commands.PingCmd;
import com.mateusfrz.arystaaddons.listener.ChestViewerListener;
import com.mateusfrz.arystaaddons.listener.ChunckViewerListener;
import com.mateusfrz.arystaaddons.listener.FarmlandListener;
import com.mateusfrz.arystaaddons.listener.LoginListener;
import com.mateusfrz.arystaaddons.listener.SpawnersListener;
import com.mateusfrz.arystaaddons.utils.config.ConfigFile;

public final class ArystaAddons extends JavaPlugin {

	private static ArystaAddons instance;

	public static String getPluginName() {
		return getIntance().getName();
	}
	
	/**
	 * 
	 * @return prefix for all messages
	 */
	public static String getPrefixSentence() {
		return String.format("§2%s §5> §e", getPluginName());
	}

	/**
	 * 
	 * @return instance of Plugin
	 */
	public static ArystaAddons getIntance() {
		return instance;
	}

	private final Map<Player, Long> synaxUsed = new HashMap<>();
	private ConfigFile config;

	@Override
	public final void onEnable() {

		instance = this;
		
		this.config = new ConfigFile();
		this.config.reload();

		PluginManager pm = super.getServer().getPluginManager();
		pm.registerEvents(new ChestViewerListener(), this);
		pm.registerEvents(new ChunckViewerListener(), this);
		pm.registerEvents(new SpawnersListener(), this);
		pm.registerEvents(new FarmlandListener(), this);
		pm.registerEvents(new LoginListener(), this);

		super.getCommand("ofix").setExecutor(new ChestViewerCmd());
		super.getCommand("ping").setExecutor(new PingCmd());
		super.getCommand("synax").setExecutor(new ChunkViewerCmd());
		super.getCommand("arysta").setExecutor(new ConfigCmd());

		Bukkit.getOnlinePlayers().forEach(p -> getSynaxUsed().put(p, System.currentTimeMillis() - 65L));

		Iterator<Recipe> recipes = Bukkit.recipeIterator();
		while (recipes.hasNext()) {
			Recipe recipe = recipes.next();
			if (recipe.getResult().equals(new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1))) {
				recipes.remove();
			}
		}

		ShapedRecipe pommecheat = new ShapedRecipe(new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1));

		pommecheat.shape("AAA", "ABA", "AAA");
		pommecheat.setIngredient('A', Material.EMERALD_BLOCK);
		pommecheat.setIngredient('B', Material.GOLDEN_APPLE);

		Bukkit.getServer().addRecipe(pommecheat);
	}
	
	@Override
	public final void onDisable() {
		this.config.update();
	}
	
	public ConfigFile getConfigFile() {
		return this.config;
	}
	
	/**
	 * 
	 * @return SynaxUsed
	 */
	public Map<Player, Long> getSynaxUsed() {
		return this.synaxUsed;
	}
}
