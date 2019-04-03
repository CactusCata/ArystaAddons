package com.mateusfrz.arystaaddons.commands;

import org.bukkit.entity.Player;

import com.mateusfrz.arystaaddons.ArystaAddons;
import com.mateusfrz.arystaaddons.commands.sys.CCommand;
import com.mateusfrz.arystaaddons.utils.ex.CCommandException;

public final class ChunkViewerCmd extends CCommand<Player> {

	@Override
	public void execute(Player sender, String[] arguments) throws CCommandException {
		ArystaAddons.getIntance().getConfigFile().getChunkViewer().giveAtPlayer(sender);
	}
}
