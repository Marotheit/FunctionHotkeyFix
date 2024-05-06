package net.sanctuaryhosting.FunctionHotkeyFix.hook;

import net.sanctuaryhosting.FunctionHotkeyFix.FunctionHotkeyFixPlugin;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventSubscription;
import net.luckperms.api.event.user.UserDataRecalculateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class LuckPermsHook implements HookInterface{
    private EventSubscription<?> subscription;
    
    @Override
    public String getName() {
        return "LuckPerms";
    }
    
    @Override
    public void register(FunctionHotkeyFixPlugin plugin) {
        if (plugin.getServer().getPluginManager().getPlugin("LuckPerms") == null) {
            return;
        }
        
        LuckPerms luckPerms = plugin.getServer().getServicesManager().load(LuckPerms.class);
        
        if (luckPerms == null) {
            return;
        }
        
        subscription = luckPerms.getEventBus().subscribe(UserDataRecalculateEvent.class, e -> {
            Player player = Bukkit.getPlayer(e.getUser().getUniqueId());
            
            if (player != null) {
                plugin.getProvider().update(player);
            }
        });
        
        plugin.getLogger().info("Successfully hooked into LuckPerms!");
    }
    
    @Override
    public void unregister(FunctionHotkeyFixPlugin plugin) {
        if (subscription != null) {
            subscription.close();
        }
    }
}