package com.mateusfrz.arystaaddons.utils.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mateusfrz.arystaaddons.ArystaAddons;
import com.mateusfrz.arystaaddons.utils.ex.MaterialMatchException;

/**
 * 
 * @author CactusCata
 *
 * @param <E>  enum
 * 
 * Administrator of config (1/2 part of config.yml)
 * 
 */

public class ConfigViewer<E extends Enum<E>> {

	private ItemStack item;
	private String regionNotAvaible, itemName, received;
	private Material material;
	private List<String> lore = new ArrayList<>();
	private final E[] enumsViewer;

	public ConfigViewer(E[] enumsViewer, ConfigurationSection section) {
		this.enumsViewer = enumsViewer;
		this.material = Material.matchMaterial(section.getString(ConfigViewerData.MATERIAL.toString()));
		this.regionNotAvaible = ChatColor.translateAlternateColorCodes('&', section.getString(ConfigViewerData.REGION_NOT_AVAIBLE.toString()));
		this.itemName = ChatColor.translateAlternateColorCodes('&', section.getString(ConfigViewerData.ITEM_NAME.toString()));
		this.received = ChatColor.translateAlternateColorCodes('&', section.getString(ConfigViewerData.RECEIVED.toString()));
		section.getStringList(ConfigViewerData.LORE.toString()).forEach(str -> lore.add(ChatColor.translateAlternateColorCodes('&', str)));
		updateItem();
	}

	/**
	 * Update all stats on item for the next give
	 */
	private void updateItem() {
		this.item = new ItemStack(this.getMaterial());
		ItemMeta itemM = item.getItemMeta();

		itemM.setDisplayName(this.getItemName());
		itemM.setLore(this.getLore());
		itemM.addEnchant(Enchantment.DAMAGE_UNDEAD, 1, true);
		itemM.addItemFlags(ItemFlag.HIDE_ENCHANTS);

		item.setItemMeta(itemM);

	}

	/**
	 * 
	 * @param matchMaterial the new material
	 */
	public final void setMaterial(Material matchMaterial) {
		this.material = matchMaterial;
		this.updateItem();
	}

	/**
	 * 
	 * @return the material
	 */
	public final Material getMaterial() {
		return this.material;
	}
	
	/**
	 * 
	 * @param player to whom to send the message
	 */
	public final void giveAtPlayer(Player player) {
		player.getInventory().addItem(item);
		player.sendMessage(ArystaAddons.getPrefixSentence() + this.getReceived());
	}

	/**
	 * 
	 * @return region no avaible message
	 */
	public final String getRegionNotAvaible() {
		return regionNotAvaible;
	}

	/**
	 * 
	 * @param regionNotAvaible set new message for not avaible region
	 */
	public final void setRegionNotAvaible(String regionNotAvaible) {
		this.regionNotAvaible = regionNotAvaible;
		this.updateItem();
	}

	/**
	 * 
	 * @return the name of item
	 */
	public final String getItemName() {
		return itemName;
	}

	/**
	 * 
	 * @param itemName the item name
	 */
	public final void setItemName(String itemName) {
		this.itemName = itemName;
		this.updateItem();
	}

	/**
	 * 
	 * @return get message who send in the futur at player
	 */
	public final String getReceived() {
		return received;
	}

	/**
	 * 
	 * @param received set received message
	 */
	public final void setReceived(String received) {
		this.received = received;
	}

	/**
	 * 
	 * @return lore of item
	 */
	public final List<String> getLore() {
		return lore;
	}

	/**
	 * 
	 * @param lore new lore
	 */
	public final void setLore(List<String> lore) {
		this.lore = lore;
		this.updateItem();
	}

	/**
	 * 
	 * @return Return an enum class
	 */
	public final E[] getEnumsViewer() {
		return enumsViewer;
	}

	/**
	 * 
	 * Used for setup name of path and setup item conditions
	 * @author CactusCata
	 *
	 */
	public static enum ConfigViewerData {

		REGION_NOT_AVAIBLE(new Added() {

			@Override
			public final String add(ConfigViewer<?> configViewer, String arg) {
				configViewer.setRegionNotAvaible(arg);
				return "Le message de région est maintenant '" + arg + "' !";
			}

		}),
		ITEM_NAME(new Added() {

			@Override
			public final String add(ConfigViewer<?> configViewer, String arg) {
				configViewer.setItemName(arg);
				return "Le nom de l'objet est maintenant '" + arg + "§e' !";
			}

		}),
		RECEIVED(new Added() {

			@Override
			public final String add(ConfigViewer<?> configViewer, String arg) {
				configViewer.setReceived(arg);
				return "Le message de dialogue lorsque l'objet est reçu est désormais '" + arg + "§e' !";
			}

		}),
		LORE(new Added() {

			@Override
			public final String add(ConfigViewer<?> configViewer, String arg) {
				configViewer.setLore(new ArrayList<String>(Arrays.asList(arg.split(","))));
				return "La nouvelle description de l'objet est maintenant '" + configViewer.getLore() + "§e' !";
			}

		}),
		MATERIAL(new Added() {

			@Override
			public final String add(ConfigViewer<?> configViewer, String arg) {
				Material material = Material.matchMaterial(arg);
				if (material == null) {
					throw new MaterialMatchException("Le material '" + arg + "' n'existe pas !");
				}
				configViewer.setMaterial(material);
				return "Le nouveau materiel qui sera utilisé est maintenant '" + configViewer.getMaterial().name() + "§e' !";
			}

		});

		private final Added add;

		/**
		 * 
		 * @param add for check conditions
		 */
		private ConfigViewerData(Added add) {
			this.add = add;
		}

		public final Added getAdded() {
			return add;
		}

		/**
		 * @return totalPath
		 */
		@Override
		public final String toString() {
			return super.toString().toLowerCase();
		}

		/**
		 * 
		 * @param config the config part
		 * @param section the section part
		 */
		public static void save(ConfigViewer<?> config, ConfigurationSection section) {
			section.set(ConfigViewer.ConfigViewerData.ITEM_NAME.toString().replace('§', '&'), config.getItemName());
			config.getLore().replaceAll(new UnaryOperator<String>() {

				@Override
				public String apply(String str) {
					return str.replace('§', '&');
				}
			});
			section.set(ConfigViewer.ConfigViewerData.LORE.toString(), config.lore);
			section.set(ConfigViewer.ConfigViewerData.RECEIVED.toString(), config.getReceived().replace('§', '&'));
			section.set(ConfigViewer.ConfigViewerData.REGION_NOT_AVAIBLE.toString(), config.getRegionNotAvaible().replace('§', '&'));
		}

	}

	/**
	 * 
	 * @author CactusCata
	 * Apply conditions and throw excpetion if is not correct input
	 *
	 */
	public static interface Added {
		/**
		 * 
		 * @param configViewer
		 * @param arg
		 * @return sentence for sender
		 * @throws Exception if is not correct input
		 */
		public String add(ConfigViewer<?> configViewer, String arg) throws Exception;
	}

}
