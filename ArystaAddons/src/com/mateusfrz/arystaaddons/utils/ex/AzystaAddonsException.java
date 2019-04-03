package com.mateusfrz.arystaaddons.utils.ex;

import java.text.Normalizer;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import com.mateusfrz.arystaaddons.ArystaAddons;
import com.mateusfrz.arystaaddons.utils.ColoredConsoleMessage;

public class AzystaAddonsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param message
	 *            to send to the sender who throw the exception
	 */
	public AzystaAddonsException(String message) {
		super(message);
	}

	/**
	 * Send message with prefix and text
	 * 
	 * @param sender
	 *            to send message
	 */
	public void sendMessage(CommandSender sender) {
		String message = ArystaAddons.getPrefixSentence() + ChatColor.RED + super.getMessage();
		sender.sendMessage(
				sender instanceof ConsoleCommandSender
						? ColoredConsoleMessage.ANSI_RESET.getTotalColorCode()
								+ Normalizer.normalize(ColoredConsoleMessage.fixString(message), Normalizer.Form.NFD)
										.replaceAll("[^\\p{ASCII}]", "")
						: message);
	}

}
