package net.sanctuaryhosting.FunctionHotkeyFix;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class FunctionHotkeyFixListener implements Listener{
    private final FunctionHotkeyFixPlugin plugin;
    
    public FunctionHotkeyFixListener(FunctionHotkeyFixPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        // Since the Operator packet is sent early in the login process
        // (before the permission plugin is even initialized...)
        // we check again after the player has fully loaded in.
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            if (player.isOnline()) {
                plugin.getProvider().update(player);
            }
        }, 10L);
    }
}