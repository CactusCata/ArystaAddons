package com.mateusfrz.arystaaddons.commands;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mateusfrz.arystaaddons.ArystaAddons;
import com.mateusfrz.arystaaddons.commands.sys.CCommand;
import com.mateusfrz.arystaaddons.commands.sys.CommandValidator;
import com.mateusfrz.arystaaddons.utils.ex.CCommandException;

public final class PingCmd extends CCommand<CommandSender> {

	private final char[] codeColor = new char[] { 'a', '6', 'c' };

	@Override
	public void execute(CommandSender sender, String[] args) throws CCommandException {

		if (args.length == 0) {
			CommandValidator.instanceOf(sender, Player.class, "Veuillez utiliser /ping <joueur>");
			Player p = CommandValidator.get(sender);
			sendColorMessage(sender, p);
		} else {
			CommandValidator.isFalse(sender.hasPermission("arysta.cmdping"), "Tu n'as pas la permission d'utiliser cette commande !");
			sendColorMessage(sender, CommandValidator.getPlayer(args[0]));
		}

	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, String[] args) {
		return args.length == 1 ? Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(str -> str.toLowerCase().startsWith(args[0].toLowerCase())).collect(Collectors.toList()) : Collections.emptyList();
	}

	/**
	 * 
	 * @param sender
	 *            to send message
	 * @param player
	 *            target
	 */
	private void sendColorMessage(CommandSender sender, Player player) {
		int ping = ((CraftPlayer) player).getHandle().ping;
		int pingResult = ping / 60;
		sender.sendMessage(String.format("%s§aPing de %s: §%c%d §6ms.", ArystaAddons.getPrefixSentence(),
				player.getDisplayName(), pingResult > this.codeColor.length ? 'c' : this.codeColor[pingResult], ping));
	}
}
