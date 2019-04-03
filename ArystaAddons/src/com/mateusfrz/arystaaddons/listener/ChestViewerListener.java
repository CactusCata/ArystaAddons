package com.mateusfrz.arystaaddons.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.ps.PS;
import com.mateusfrz.arystaaddons.ArystaAddons;
import com.mateusfrz.arystaaddons.utils.config.ChestViewerConfig;

public final class ChestViewerListener implements Listener {
	
	private List<Player> list = new ArrayList<>();

	@EventHandler(priority = EventPriority.MONITOR)
	public final void onPlayerInteractEvent(PlayerInteractEvent event) {
		ItemStack item = event.getItem();
		if (item != null && item.getType() == Material.BONE) {
			Block block = event.getClickedBlock();
			if (block != null && ((block.getType() == Material.CHEST) || (block.getType() == Material.TRAPPED_CHEST)
					|| (block.getType() == Material.FURNACE) || (block.getType() == Material.HOPPER)
					|| (block.getType() == Material.DISPENSER) || (block.getType() == Material.DROPPER)
					|| block.getType() == Material.BURNING_FURNACE)) {
				event.setCancelled(true);
				Action action = event.getAction();
				if (action == Action.RIGHT_CLICK_BLOCK || action == Action.LEFT_CLICK_BLOCK) {
					ChestViewerConfig cvc = ArystaAddons.getIntance().getConfigFile().getChestViewer();
					if (item.getItemMeta() != null && item.getItemMeta().getDisplayName().equals(cvc.getItemName())) {

						Player player = event.getPlayer();

						Faction faction = BoardColl.get().getFactionAt(PS.valueOf(player.getLocation()));

						if (!faction.getName().equals("Arysta")) {
							event.setCancelled(true);
							player.playSound(player.getLocation(), Sound.ANVIL_USE, 100.0F, 100.0F);
							if (block.getType() == Material.CHEST || block.getType() == Material.TRAPPED_CHEST) {
								InventoryHolder ih = (InventoryHolder) block.getState();
								Inventory i = ih.getInventory();

								ItemStack[] inventory = i.getContents().clone();

								Inventory inv = Bukkit.createInventory(null, i.getSize() == 54 ? 54 : 27,
										cvc.getChestName());
								inv.setContents(inventory);

								player.openInventory(inv);
							} else if (block.getState() instanceof InventoryHolder) {
								this.list.add(player);
								InventoryHolder ih = (InventoryHolder) block.getState();
								player.openInventory(ih.getInventory());
							}
						} else {
							player.sendMessage(ArystaAddons.getPrefixSentence() + cvc.getRegionNotAvaible());
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void inventoryClose(InventoryCloseEvent e) {
		this.list.remove(e.getPlayer());
	}

	@EventHandler
	public final void onTake(InventoryClickEvent event) {
		Inventory inv = event.getClickedInventory();
		if (inv != null && (this.list.contains(event.getWhoClicked()) || inv.getName().contains(ArystaAddons.getIntance().getConfigFile().getChestViewer().getChestName()))) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public final void onPlayerSneak(InventoryClickEvent event) {
		Inventory inv = event.getInventory();
		if (inv != null && inv.getType() == InventoryType.ANVIL) {
			ItemStack item = inv.getItem(0);
			if (item != null && item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null
					&& item.getItemMeta().getDisplayName()
							.equals(ArystaAddons.getIntance().getConfigFile().getChestViewer().getItemName())) {
				inv.setItem(3, item);
			}
		}
	}
}