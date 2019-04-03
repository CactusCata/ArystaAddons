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

public final class ChunkViewerConfig extends ConfigViewer<ChunkViewerConfig.ChunkViewerConfigData> {

	private long timeToWait;
	private String waitMessage;

	/**
	 * 
	 * @param section
	 *            (chunk_viewer)
	 */
	public ChunkViewerConfig(ConfigurationSection section) {
		super(ChunkViewerConfigData.values(), section);
		try {
			this.setWaitMessage(ChatColor.translateAlternateColorCodes('&',
					section.getString(ChunkViewerConfigData.WAIT_MESSAGE.toString())));
			this.setTimeToWait(section.getLong(ChunkViewerConfigData.TIME_TO_WAIT.toString()));
		} catch (NullPointerException e) {
			Log.warn(ArystaAddons.getPrefixSentence() + "File ChestViewerConfig is not correctly configurate !");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return wait message
	 */
	public final String getWaitMessage() {
		return waitMessage;
	}

	/**
	 * 
	 * @param waitMessage
	 *            new message to sent
	 */
	public final void setWaitMessage(String waitMessage) {
		this.waitMessage = waitMessage;
	}

	/**
	 * 
	 * @return time to wait before new action
	 */
	public final long getTimeToWait() {
		return timeToWait;
	}

	/**
	 * 
	 * @param timeToWait
	 *            new time to wait
	 */
	public final void setTimeToWait(long timeToWait) {
		this.timeToWait = timeToWait;
	}

	/**
	 * 
	 * Used for setup name of path and setup item conditions
	 * 
	 * @author CactusCata
	 *
	 */
	public static enum ChunkViewerConfigData implements IConfig {

		TIME_TO_WAIT(new Added() {

			@Override
			public final String add(ConfigViewer<?> configViewer, String arg) {
				long time = Long.parseLong(arg);
				ChunkViewerConfig cvc = ((ChunkViewerConfig) configViewer);
				if (time < cvc.getTimeToWait()) {
					long resetTime = System.currentTimeMillis() + time * 1000;
					ArystaAddons.getIntance().getSynaxUsed().entrySet().stream()
							.filter(entry -> entry.getValue() > resetTime).forEach(entry -> entry.setValue(resetTime));
				}
				cvc.setTimeToWait(time);
				return "Le delais d'attente est désormais de " + arg + " seconde" + (time > 1 ? "s" : "") + " !";
			}

		}),
		WAIT_MESSAGE(new Added() {
			@Override
			public final String add(ConfigViewer<?> configViewer, String arg) {
				((ChunkViewerConfig) configViewer).setWaitMessage(arg);
				return "Le message d'attente est désormais '" + arg + "§e' !";
			}
		});

		private final Added add;

		private ChunkViewerConfigData(Added add) {
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
		 * 
		 * @return path of part
		 */
		public static String getPath() {
			return "chunk_viewer";
		}

	}

}
