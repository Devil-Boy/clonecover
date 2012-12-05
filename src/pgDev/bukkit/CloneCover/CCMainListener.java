package pgDev.bukkit.CloneCover;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import pgDev.bukkit.DisguiseCraft.disguise.Disguise;
import pgDev.bukkit.DisguiseCraft.disguise.DisguiseType;

public class CCMainListener implements Listener {
	final CloneCover plugin;
	
	public CCMainListener(final CloneCover plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (!player.hasPermission("clonecover.exempt")) {
			Disguise disguise = new Disguise(plugin.dcAPI.newEntityID(), plugin.pluginSettings.disguiseTo, DisguiseType.Player);
			if (plugin.dcAPI.isDisguised(player)) {
				plugin.dcAPI.changePlayerDisguise(player, disguise);
			} else {
				plugin.dcAPI.disguisePlayer(player, disguise);
			}
			if (!plugin.pluginSettings.disguiseNotification.equalsIgnoreCase("none")) {
				player.sendMessage(plugin.pluginSettings.disguiseNotification);
			}
		}
	}
	
	@EventHandler
	public void onChat(PlayerCommandPreprocessEvent event) {
		for (String command : plugin.pluginSettings.cancelCommands.split(",")) {
			if (event.getMessage().startsWith(command)) {
				event.setCancelled(true);
				if (plugin.dcAPI.isDisguised(event.getPlayer())) {
					plugin.dcAPI.undisguisePlayer(event.getPlayer());
					event.getPlayer().sendMessage(ChatColor.GOLD + "You are no longer a clone.");
				}
				return;
			}
		}
	}
}
