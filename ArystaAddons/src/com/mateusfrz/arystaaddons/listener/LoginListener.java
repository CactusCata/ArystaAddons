package com.mateusfrz.arystaaddons.listener;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.mateusfrz.arystaaddons.ArystaAddons;

public final class LoginListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public final void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		Map<Player, Long> map = ArystaAddons.getIntance().getSynaxUsed();
		if (map.containsKey(player)) {
			map.remove(player);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public final void onJoin(PlayerJoinEvent event) {
		ArystaAddons.getIntance().getSynaxUsed().put(event.getPlayer(), System.currentTimeMillis() - 65L);
	}

}
