package net.sanctuaryhosting.FunctionHotkeyFix.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.sanctuaryhosting.FunctionHotkeyFix.FunctionHotkeyFixPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public abstract class NettyProvider implements ProviderInterface, Listener{
    private final Map<Player, PlayerChannelHandler> playerHandlers;
    
    private FunctionHotkeyFixPlugin plugin;
    
    public NettyProvider() {
        playerHandlers = new HashMap<>();
    }
    
    public abstract void sendPacket(Player player);
    
    public abstract void adjustPacket(Player player, Object packet);
    
    public abstract Channel getChannel(Player player);
    
    @Override
    public void update(Player player) {
        sendPacket(player);
    }
    
    @Override
    public void register(FunctionHotkeyFixPlugin plugin) {
        this.plugin = plugin;
        
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    @Override
    public void unregister(FunctionHotkeyFixPlugin plugin) {
        HandlerList.unregisterAll(this);
        
        for (PlayerChannelHandler handler : playerHandlers.values()) {
            handler.unregister();
        }
        
        playerHandlers.clear();
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerChannelHandler handler = playerHandlers.get(player);
        
        if (handler == null) {
            Channel channel = getChannel(player);
            PlayerChannelHandler newHandler = new PlayerChannelHandler(player, channel);
            
            newHandler.register();
            playerHandlers.put(player, newHandler);
        }
    }
    
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        PlayerChannelHandler handler = playerHandlers.remove(event.getPlayer());
        
        if (handler != null) {
            handler.unregister();
        }
    }
    
    public FunctionHotkeyFixPlugin getPlugin() {
        return plugin;
    }
    
    public final class PlayerChannelHandler extends ChannelDuplexHandler{
        private static final String NAME = "functionhotkeyfix_handler";
        
        private final Player player;
        private final Channel channel;
        
        public PlayerChannelHandler(Player player, Channel channel) {
            this.player = player;
            this.channel = channel;
        }
        
        public void register() {
            channel.pipeline().addBefore("packet_handler", NAME, this);
        }
        
        public void unregister() {
            if (channel.pipeline().get(NAME) != null) {
                channel.pipeline().remove(NAME);
            }
        }
        
        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            try {
                adjustPacket(player, msg);
            } catch (Exception e) {
                getPlugin().getLogger().log(Level.SEVERE, "Could not adjust packet!", e);
            }
            
            super.write(ctx, msg, promise);
        }
    }
}