package com.mateusfrz.arystaaddons.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.ps.PS;

public final class FarmlandListener implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public final void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Faction faction = BoardColl.get().getFactionAt(PS.valueOf(player.getLocation()));

		if ((faction.getName().equals("Arysta")) && (event.getAction() == Action.PHYSICAL)) {
			Block block = event.getClickedBlock();
			if (block != null && block.getType() == Material.SOIL) {
				event.setUseInteractedBlock(Event.Result.DENY);
				event.setCancelled(true);
				block.setTypeIdAndData(block.getType().getId(), block.getData(), true);
			}
		}
	}
}