package com.mateusfrz.arystaaddons.commands.sys;

import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.mateusfrz.arystaaddons.utils.ex.CCommandException;

public abstract class CCommand<E extends CommandSender> implements CommandExecutor, TabCompleter {

	private final String[] argumentsSentence;

	public CCommand(String... argumentsSentence) {
		this.argumentsSentence = argumentsSentence;
	}

	public abstract void execute(E commandSender, String[] arguments) throws CCommandException;

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		E e = null;
		try {
			e = CommandValidator.get(sender);
		} catch (ClassCastException ex) {
			return CCommand.this.onTabComplete(null, null);
		}
		return this.onTabComplete(e, args);
	}

	public List<String> onTabComplete(E sender, String[] args) {
		return Collections.emptyList();
	}

	@Override
	public final boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		try {
			if (args.length < this.argumentsSentence.length)
				throw new CCommandException(this.argumentsSentence[args.length]);
			this.execute(CommandValidator.get(sender), args);
		} catch (CCommandException e) {
			e.sendMessage(sender);
		}
		return true;
	}

}
