package net.sanctuaryhosting.FunctionHotkeyFix;

import net.sanctuaryhosting.FunctionHotkeyFix.hook.HookInterface;
import net.sanctuaryhosting.FunctionHotkeyFix.hook.LuckPermsHook;
import net.sanctuaryhosting.FunctionHotkeyFix.netty.ProviderException;
import net.sanctuaryhosting.FunctionHotkeyFix.netty.ProviderInterface;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public final class FunctionHotkeyFixPlugin extends JavaPlugin implements Listener{
    private final HookInterface[] hooks = new HookInterface[] {
            new LuckPermsHook()
    };
    private List<HookInterface> registeredHooks;
    private ProviderInterface provider;
    
    @Override
    public void onLoad() {
        String version = Bukkit.getServer().getBukkitVersion();
        if (!version.equals("1.21.1-R0.1-SNAPSHOT")) {
            getLogger().warning("Server version not recognized. Proceed with caution! (Server Version: " + version + ")");
        }
        
        provider = findProvider();
    }
    
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new FunctionHotkeyFixListener(this), this);
        
        try {
            provider.register(this);
        } catch (ProviderException e) {
            getLogger().log(Level.SEVERE, "Could not register provider " + provider.getClass().getSimpleName() + "!", e);
        }
        
        loadHook();
    }
    
    @Override
    public void onDisable() {
        if (registeredHooks != null) {
            for (HookInterface hook : registeredHooks) {
                hook.unregister(this);
            }
        }
        try {
            provider.unregister(this);
        } catch (ProviderException e) {
            getLogger().log(Level.SEVERE, "Could not unregister provider " + provider.getClass().getSimpleName() + "!", e);
        }
    }
    
    private void loadHook() {
        registeredHooks = new ArrayList<>();
        
        for (HookInterface hook : hooks) {
                hook.register(this);
                registeredHooks.add(hook);
        }
    }
    
    private ProviderInterface findProvider() {
        return new FunctionHotkeyFixPacketHandler();
    }
    
    public FunctionHotkeyFixOperatorLevel getPlayerOperatorLevel(Player player) {
        if (player.hasPermission("minecraft.command.gamemode") || player.isOp()) {
            return FunctionHotkeyFixOperatorLevel.ADMIN_COMMANDS;
        }
        return FunctionHotkeyFixOperatorLevel.NO_PERMISSIONS;
    }
    
    public ProviderInterface getProvider() {
        return provider;
    }
}