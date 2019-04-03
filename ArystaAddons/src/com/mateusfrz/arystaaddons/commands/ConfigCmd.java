package com.mateusfrz.arystaaddons.commands;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import com.mateusfrz.arystaaddons.ArystaAddons;
import com.mateusfrz.arystaaddons.commands.sys.CCommand;
import com.mateusfrz.arystaaddons.utils.ex.CCommandException;

public final class ConfigCmd extends CCommand<CommandSender>{

//	public ConfigCmd() {
//		super("Veuillez préciser '" + ChestViewerConfig.ChestViewerConfigData.getPath() +"' ou '" + ChunkViewerConfig.ChunkViewerConfigData.getPath() + "' !");
//	}

	@Override
	public void execute(CommandSender sender, String[] args) throws CCommandException {
		
		//CommandValidator.isFalse(args[0].equalsIgnoreCase("reload"), "L'argument reload est le seul utilisable !");
		ArystaAddons.getIntance().getConfigFile().reload();
		sender.sendMessage(ArystaAddons.getPrefixSentence() + YamlConfiguration.loadConfiguration(new File(ArystaAddons.getIntance().getDataFolder(), "config.yml")).getString("message_config_reload"));
		
		//return;
//		
//		CommandValidator.isTrue(!args[0].equalsIgnoreCase(ChestViewerConfig.ChestViewerConfigData.getPath()) && !args[0].equalsIgnoreCase(ChunkViewerConfig.ChunkViewerConfigData.getPath()), "Veuillez préciser '" + ChestViewerConfig.ChestViewerConfigData.getPath() +"' ou '" + ChunkViewerConfig.ChunkViewerConfigData.getPath() + "' !");
//
//		final ConfigViewer<?> config = args[0].equalsIgnoreCase(ChestViewerConfig.ChestViewerConfigData.getPath()) ? ArystaAddons.getIntance().getConfigFile().getChestViewer() : ArystaAddons.getIntance().getConfigFile().getChunkViewer();
//		final Enum<?>[] enumsConfig = config.getEnumsViewer();
//		final StringBuilder builder = new StringBuilder(ArystaAddons.getPrefixSentence()).append("Veuillez utiliser les arguments suivants: ").append(StringUtils.join(ConfigViewerData.values(), 0, ", ")).append(", ").append(StringUtils.join(enumsConfig, 0, ", "));
//		
//		CommandValidator.arrayHaveMinArgSize(args, 1, builder.toString());
//		CommandValidator.arrayHaveMinArgSize(args, 2, "Veuillez préciser l'argument !");
//		
//		final Enum<?> enuDeep = Arrays.stream(enumsConfig).filter(enu -> enu.toString().equalsIgnoreCase(args[1])).findFirst().orElse(null);
//		final ConfigViewer.ConfigViewerData enuConfigViewer = Arrays.stream(ConfigViewer.ConfigViewerData.values()).filter(enu -> enu.toString().equalsIgnoreCase(args[1])).findFirst().orElse(null);
//		
//		CommandValidator.isTrue(enuDeep == null && enuConfigViewer == null, builder.toString());
//		
//		final Added add = enuDeep != null ? ((IConfig) enuDeep).getAdd() : enuConfigViewer.getAdded();
//		
//		try {
//			sender.sendMessage(ArystaAddons.getPrefixSentence() + add.add(config, ChatColor.translateAlternateColorCodes('&', StringUtils.join(args, 2, " "))));
//		} catch (NumberFormatException e) {
//			throw new CCommandException("Le nombre '" + args[2] + "' n'est pas un nombre valide !");
//		} catch (MaterialMatchException e) {
//			throw new CCommandException(e.getMessage());
//		} catch (Exception e) {
//			Log.warn("Error while execute command !");
//			e.printStackTrace();
//		}
		
	}
	
	@Override
	public final List<String> onTabComplete(CommandSender player, String[] args) {
		if(args.length == 1) 
			return Arrays.asList("reload");
		return Collections.emptyList();
//		if(args.length == 1)
//			return Arrays.asList(ChestViewerConfig.ChestViewerConfigData.getPath(), ChunkViewerConfig.ChunkViewerConfigData.getPath());
//		else if(args.length == 2) {
//			if(args[0].equalsIgnoreCase(ChestViewerConfig.ChestViewerConfigData.getPath()))
//				return Arrays.stream(ChestViewerConfig.ChestViewerConfigData.values()).map(ChestViewerConfig.ChestViewerConfigData::toString).filter(str -> str.toLowerCase().startsWith(args[0].toLowerCase())).collect(Collectors.toList());
//			if(args[0].equalsIgnoreCase(ChunkViewerConfig.ChunkViewerConfigData.getPath()))
//				return Arrays.stream(ChunkViewerConfig.ChunkViewerConfigData.values()).map(ChunkViewerConfig.ChunkViewerConfigData::toString).filter(str -> str.toLowerCase().startsWith(args[0].toLowerCase())).collect(Collectors.toList());
//		}
//		return Collections.emptyList();
	}

}
