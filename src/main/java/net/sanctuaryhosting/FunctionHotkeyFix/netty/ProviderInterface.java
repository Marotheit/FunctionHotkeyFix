package net.sanctuaryhosting.FunctionHotkeyFix.netty;

import net.sanctuaryhosting.FunctionHotkeyFix.FunctionHotkeyFixPlugin;
import org.bukkit.entity.Player;

public interface ProviderInterface{
    void register(FunctionHotkeyFixPlugin plugin);
    
    void unregister(FunctionHotkeyFixPlugin plugin);
    
    void update(Player player);
}