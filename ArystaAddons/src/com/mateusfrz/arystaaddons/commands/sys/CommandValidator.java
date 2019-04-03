package com.mateusfrz.arystaaddons.commands.sys;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mateusfrz.arystaaddons.utils.ex.CCommandException;

public class CommandValidator {

	/**
	 * 
	 * @param b boolean
	 * @param message toSend
	 * @throws CCommandException if b is true
	 */
	public static void isTrue(boolean b, String message) throws CCommandException {
		if(b) {
			throw new CCommandException(message);
		}
	}
	
	public static void isFalse(boolean b, String message) throws CCommandException {
		isTrue(!b, message);
	}
	
	public static void arrayHaveMinArgSize(Object[] objects, int minimum, String message) throws CCommandException {
		isTrue(objects.length < minimum, message);
	}
	
	public static void arrayHaveMaxArgSize(Object[] objects, int maximum, String message) throws CCommandException {
		isTrue(objects.length > maximum, message);
	}
	
	public static void equals(Object object1, Object object2, String message) throws CCommandException {
		isTrue(object1.equals(object2), message);
	}
	
	public static void notEquals(Object object1, Object object2, String message) throws CCommandException {
		isFalse(object1.equals(object2), message);
	}
	
	public static <T> void instanceOf(Object object, Class<T> arg, String message) throws CCommandException {
		isFalse(arg.isAssignableFrom(object.getClass()), message);
	}
	
	public static Player getPlayer(String playerName) throws CCommandException {
		Player player = Bukkit.getPlayerExact(playerName);
		isTrue(player == null, "Le joueur '" + playerName + "' n'est pas connecté !");
		return player;
	}
	
	public static Player getPlayer(CommandSender sender) {
		return (Player) sender;
	}
	
	@SuppressWarnings("unchecked")
	public static <M, C extends M> C get(M mother) {
		return (C) mother;
	}
	
 }
