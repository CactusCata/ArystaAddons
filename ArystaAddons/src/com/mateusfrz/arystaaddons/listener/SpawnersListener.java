package com.mateusfrz.arystaaddons.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;

public final class SpawnersListener implements Listener {

	@EventHandler
	public final void onBlockBreakEvent(BlockBreakEvent event) {
		Faction faction = BoardColl.get().getFactionAt(PS.valueOf(event.getPlayer().getLocation()));
		Faction wilderness = FactionColl.get().getNone();
		MPlayer mp = MPlayer.get(event.getPlayer());
		if(mp.getFaction() != faction && faction != wilderness) {
			return;
		}
		Block block = event.getBlock();
		if ((block.getType() == Material.MOB_SPAWNER)
				&& (event.getPlayer().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH))) {
			CreatureSpawner spawner = (CreatureSpawner) block.getState();
			ItemStack spawnerItem = new ItemStack(Material.MOB_SPAWNER);
			ItemMeta spawnerMeta = spawnerItem.getItemMeta();
			spawnerMeta.setDisplayName(spawner.getCreatureTypeName());
			spawnerItem.setItemMeta(spawnerMeta);

			block.getLocation().getWorld().dropItem(block.getLocation(), spawnerItem);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public final void onPlaceBlockEvent(BlockPlaceEvent event) {
		Block block = event.getBlock();
		if (block.getType() == Material.MOB_SPAWNER) {
			block.setType(Material.MOB_SPAWNER);
			CreatureSpawner cs = (CreatureSpawner) block.getState();
			cs.setSpawnedType(EntityType
					.fromName(event.getPlayer().getItemInHand().getItemMeta().getDisplayName().toUpperCase()));
			if(block.getState() != null)
				block.getState().update();
		}
	}

	@EventHandler
	public final void onPlayerSneak(InventoryClickEvent event) {
		InventoryView invv = event.getView();
		if (invv != null && invv.getType() == InventoryType.ANVIL) {
			Inventory inv = event.getInventory();
			if (inv != null) {
				ItemStack item = inv.getItem(0);
				if (item != null && item.getType() == Material.MOB_SPAWNER) {
					inv.setItem(3, item);
				}
			}
		}
	}
}
