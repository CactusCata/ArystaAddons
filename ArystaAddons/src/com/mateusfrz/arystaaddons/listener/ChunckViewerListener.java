package com.mateusfrz.arystaaddons.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.ps.PS;
import com.mateusfrz.arystaaddons.ArystaAddons;
import com.mateusfrz.arystaaddons.utils.Cuboid;
import com.mateusfrz.arystaaddons.utils.config.ChunkViewerConfig;

public final class ChunckViewerListener implements Listener {

	private final List<Player> spamCancel = new ArrayList<>();

	@EventHandler
	public final void onInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			ItemStack item = event.getItem();
			ChunkViewerConfig cvc = ArystaAddons.getIntance().getConfigFile().getChunkViewer();
			if (item != null && item.getType() == Material.WATCH
					&& item.getItemMeta().getDisplayName().equals(cvc.getItemName())) {

				Player p = event.getPlayer();
				
				if(spamCancel.contains(p)) {
					return;
				}
				
				spamCancel.add(p);
				
				Bukkit.getScheduler().runTaskLater(ArystaAddons.getIntance(), () -> {
					if(p != null && p.isOnline()) {
						spamCancel.remove(p);
					}
				}, 20 * 2);

				if (ArystaAddons.getIntance().getSynaxUsed().get(p) < System.currentTimeMillis()) {

					Location loc = p.getLocation();

					Faction faction = BoardColl.get().getFactionAt(PS.valueOf(loc));

					if (!faction.getName().equals("Arysta")) {
						p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 100.0F, 100.0F);
						Cuboid cube = new Cuboid(new Location(p.getWorld(), loc.getX() - 16, 1, loc.getZ() - 16),
								new Location(p.getWorld(), loc.getX() + 16, 256, loc.getZ() + 16));

						long obsi = StreamSupport.stream(cube.spliterator(), false).filter(b -> b.getType() == Material.OBSIDIAN).count();
						long chests = StreamSupport.stream(cube.spliterator(), false).filter(b -> b.getType() == Material.CHEST || b.getType() == Material.TRAPPED_CHEST).count();
						long pi = StreamSupport.stream(cube.spliterator(), false).filter(b -> b.getType() == Material.PACKED_ICE).count();
						long furnace = StreamSupport.stream(cube.spliterator(), false).filter(b -> b.getType() == Material.FURNACE || b.getType() == Material.BURNING_FURNACE).count();
						long dropper = StreamSupport.stream(cube.spliterator(), false).filter(b -> b.getType() == Material.DROPPER).count();
						long dispenser = StreamSupport.stream(cube.spliterator(), false).filter(b -> b.getType() == Material.DISPENSER).count();
						long hopper = StreamSupport.stream(cube.spliterator(), false).filter(b -> b.getType() == Material.HOPPER).count();
						
						p.sendMessage("§f ");
						p.sendMessage("§9Blocs dans les alentours§f:");
						p.sendMessage("§f ");
						p.sendMessage(String.format("§f*§c> §6Obsidienne%s§f: §e%d", obsi > 1 ? "s"  : "",obsi));
						p.sendMessage(String.format("§f*§c> §6Coffre%s§f: §e%d", chests > 1 ? "s" : "",chests));
						p.sendMessage(String.format("§f*§c> §6Glace%s compactée%s§f: §e%d",pi > 1 ? "s" : "", pi > 1 ? "s" : "", pi));
						p.sendMessage(String.format("§f*§c> §6Fours%s§f: §e%d",furnace > 1 ? "s" : "", pi));
						p.sendMessage(String.format("§f*§c> §6Droppers%s§f: §e%d",dropper > 1 ? "s" : "",  dropper));
						p.sendMessage(String.format("§f*§c> §6Dispensers%s§f: §e%d",dispenser > 1 ? "s" : "",  dispenser));
						p.sendMessage(String.format("§f*§c> §6Entonnoir%s§f: §e%d",hopper > 1 ? "s" : "",  hopper));
						p.sendMessage("§f ");

						ArystaAddons.getIntance().getSynaxUsed().put(p, System.currentTimeMillis() + cvc.getTimeToWait() * 1000);
					} else {
						p.sendMessage(ArystaAddons.getPrefixSentence() + cvc.getRegionNotAvaible());
					}
				} else {
					long time = (ArystaAddons.getIntance().getSynaxUsed().get(p) - System.currentTimeMillis()) / 1000L;
					p.sendMessage(ArystaAddons.getPrefixSentence() + cvc.getWaitMessage().replace("{TIME}", String.valueOf(time)));
				}
			}
		}
	}

	@EventHandler
	public final void onPlayerSneak(InventoryClickEvent event) {
		Inventory inv = event.getInventory();
		if (inv != null && inv.getType() == InventoryType.ANVIL) {
			ItemStack item = inv.getItem(0);
			if (item != null && item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals(ArystaAddons.getIntance().getConfigFile().getChunkViewer().getItemName())) {
				inv.setItem(3, item);
			}
		}
	}

	@EventHandler
	public final void onPlayerJoinEvent(PlayerJoinEvent event) {
		ArystaAddons.getIntance().getSynaxUsed().put(event.getPlayer(), System.currentTimeMillis() - 684L);
	}
}
