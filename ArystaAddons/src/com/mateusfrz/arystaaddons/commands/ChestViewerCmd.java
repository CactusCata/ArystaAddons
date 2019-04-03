package com.mateusfrz.arystaaddons.commands;

import org.bukkit.entity.Player;

import com.mateusfrz.arystaaddons.ArystaAddons;
import com.mateusfrz.arystaaddons.commands.sys.CCommand;
import com.mateusfrz.arystaaddons.utils.ex.CCommandException;

public final class ChestViewerCmd extends CCommand<Player> {

	@Override
	public void execute(Player player, String[] arguments) throws CCommandException {
		ArystaAddons.getIntance().getConfigFile().getChestViewer().giveAtPlayer(player);
	}

}
