package com.mateusfrz.arystaaddons.utils.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.libs.jline.internal.Log;

import com.mateusfrz.arystaaddons.ArystaAddons;

/**
 * 
 * Administrator of chunk_viewer of config.yml
 * 
 * @author CactusCata
 *
 */

public final class ChestViewerConfig extends ConfigViewer<ChestViewerConfig.ChestViewerConfigData> {

	private String chestName;

	public ChestViewerConfig(ConfigurationSection section) {
		super(ChestViewerConfigData.values(), section);
		try {
			this.setChestName(ChatColor.translateAlternateColorCodes('&', section.getString(ChestViewerConfigData.CHEST_NAME.toString())));
		} catch (NullPointerException e) {
			Log.warn(ArystaAddons.getPrefixSentence() + "File ChestViewerConfig is not correctly configurate !");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return the chest name
	 */
	public final String getChestName() {
		return chestName;
	}

	/**
	 * 
	 * @param chestName
	 *            new chest name
	 */
	public final void setChestName(String chestName) {
		this.chestName = chestName;
	}

	/**
	 * 
	 * Used for setup name of path and setup item conditions
	 * 
	 * @author CactusCata
	 *
	 */
	public static enum ChestViewerConfigData implements IConfig {

		CHEST_NAME(new Added() {

			@Override
			public final String add(ConfigViewer<?> configViewer, String arg) {
				((ChestViewerConfig) configViewer).setChestName(arg);
				return "Le nom du coffre est maintenant '" + arg + "§e' !";
			}

		});

		private final Added add;

		private ChestViewerConfigData(Added add) {
			this.add = add;
		}

		@Override
		public final String toString() {
			return super.toString().toLowerCase();
		}

		@Override
		public final Added getAdd() {
			return add;
		}

		/**
		 * @return path of yml part
		 */
		public static String getPath() {
			return "chest_viewer";
		}

	}

}
