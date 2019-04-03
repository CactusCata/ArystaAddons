package com.mateusfrz.arystaaddons.utils.config;

import java.io.File;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.Log;

import com.mateusfrz.arystaaddons.ArystaAddons;

/**
 * 
 * Administrator of config.yml file
 * Can read and write on file
 * 
 * @author CactusCata
 *
 */

public final class ConfigFile {

	private File file;
	private FileConfiguration config;
	private ChestViewerConfig chestViewer;
	private ChunkViewerConfig chunkViewer;

	/**
	 * 
	 * @return part chest_viewer of config.yml
	 */
	public final ChestViewerConfig getChestViewer() {
		return chestViewer;
	}

	/**
	 * 
	 * @return part chunk_viewer of config.yml
	 */
	public final ChunkViewerConfig getChunkViewer() {
		return chunkViewer;
	}

	/**
	 * Generate file if is not created in data folder of plugin
	 * 
	 * @param file
	 * @return the same file, but loader and saved
	 */
	private File load(File file) {
		RessourceSaver.save(file);
		return file;
	}

	/**
	 * update config.yml with new values
	 */
	public final void update() {
		try {
			ConfigurationSection section = this.config.getConfigurationSection("chest_viewer");
			section.set(ChestViewerConfig.ChestViewerConfigData.CHEST_NAME.toString(), this.getChestViewer().getChestName().replace('§', '&'));
			ConfigViewer.ConfigViewerData.save(this.getChestViewer(), section);
			section = this.config.getConfigurationSection("chunk_viewer");
			section.set(ChunkViewerConfig.ChunkViewerConfigData.TIME_TO_WAIT.toString(), this.getChunkViewer().getTimeToWait());
			section.set(ChunkViewerConfig.ChunkViewerConfigData.WAIT_MESSAGE.toString(), this.getChunkViewer().getWaitMessage().replace('§', '&'));
			ConfigViewer.ConfigViewerData.save(this.getChunkViewer(), section);
			this.config.save(this.file);
		} catch (Exception e) {
			Log.warn("Error while save config.yml: ");
			e.printStackTrace();
		}
	}

	public void reload() {
		this.file = load(new File(ArystaAddons.getIntance().getDataFolder(), "config.yml"));
		this.config = YamlConfiguration.loadConfiguration(this.file);
		this.chestViewer = new ChestViewerConfig(this.config.getConfigurationSection(ChestViewerConfig.ChestViewerConfigData.getPath()));
		this.chunkViewer = new ChunkViewerConfig(this.config.getConfigurationSection(ChunkViewerConfig.ChunkViewerConfigData.getPath()));
	}

}
