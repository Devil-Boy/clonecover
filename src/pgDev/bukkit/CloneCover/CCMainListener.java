package pgDev.bukkit.CloneCover;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import pgDev.bukkit.DisguiseCraft.Disguise;

public class CCMainListener implements Listener {
	final CloneCover plugin;
	
	public CCMainListener(final CloneCover plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (!plugin.hasPermissions(player, "clonecover.exempt")) {
			Disguise disguise = new Disguise(plugin.dcAPI.newEntityID(), plugin.pluginSettings.disguiseTo, null);
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
	public void onChat(PlayerChatEvent event) {
		for (String command : plugin.pluginSettings.cancelCommands) {
			if (event.getMessage().startsWith(command)) {
				if (plugin.dcAPI.isDisguised(event.getPlayer())) {
					plugin.dcAPI.undisguisePlayer(event.getPlayer());
					event.getPlayer().sendMessage(ChatColor.GOLD + "You are no longer a clone.");
				}
				return;
			}
		}
	}
}
