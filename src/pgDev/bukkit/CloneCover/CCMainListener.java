package pgDev.bukkit.CloneCover;

import org.bukkit.event.*;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class CCMainListener implements Listener {
	final CloneCover plugin;
	
	public CCMainListener(final CloneCover plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		
	}
	
	@EventHandler
	public void onChat(PlayerChatEvent event) {
		
	}
}
