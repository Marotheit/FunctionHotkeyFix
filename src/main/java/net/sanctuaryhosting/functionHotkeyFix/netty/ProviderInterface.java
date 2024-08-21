package net.sanctuaryhosting.functionHotkeyFix.netty;

import net.sanctuaryhosting.functionHotkeyFix.FunctionHotkeyFixPlugin;
import org.bukkit.entity.Player;

public interface ProviderInterface{
    void register(FunctionHotkeyFixPlugin plugin);
    
    void unregister(FunctionHotkeyFixPlugin plugin);
    
    void update(Player player);
}