package net.sanctuaryhosting.FunctionHotkeyFix;

import io.netty.channel.Channel;
import net.sanctuaryhosting.FunctionHotkeyFix.netty.NettyProvider;
import net.sanctuaryhosting.FunctionHotkeyFix.netty.ProviderException;
import net.sanctuaryhosting.FunctionHotkeyFix.reflections.ReflectionException;
import net.sanctuaryhosting.FunctionHotkeyFix.reflections.Reflections;
import org.bukkit.entity.Player;

public class FunctionHotkeyFixPacketHandler extends NettyProvider{
    @Override
    public void sendPacket(Player player) {
        FunctionHotkeyFixOperatorLevel level = getPlugin().getPlayerOperatorLevel(player);
        
        try {
            Object entityPlayer = getEntityPlayer(player);
            Object playerConnection = getPlayerConnection(entityPlayer);
            Object networkManager = getNetworkManager(playerConnection);
            
            Object packet = makeStatusPacket(entityPlayer, level.toStatusByte());
            sendPacket(networkManager, packet);
        } catch (ReflectionException e) {
            throw new ProviderException("Could not send packet!", e);
        }
    }
    
    @Override
    public void adjustPacket(Player player, Object packet) {
        try {
            if (!isStatusPacket(packet)) {
                return;
            }
            
            int entity = getStatusPacketEntity(packet);
            
            if (entity != player.getEntityId()) {
                return;
            }
            
            FunctionHotkeyFixOperatorLevel currentLevel = getStatusPacketStatus(packet);
            
            if (currentLevel == null) {
                return;
            }
            
            FunctionHotkeyFixOperatorLevel level = getPlugin().getPlayerOperatorLevel(player);
            setStatusPacketStatus(packet, level);
        } catch (ReflectionException e) {
            throw new ProviderException("Could not adjust packet!", e);
        }
    }
    
    @Override
    public Channel getChannel(Player player) {
        try {
            Object entityPlayer = getEntityPlayer(player);
            Object playerConnection = getPlayerConnection(entityPlayer);
            Object networkManager = getNetworkManager(playerConnection);
            
            return (Channel) getChannel(networkManager);
        } catch (ReflectionException e) {
            throw new ProviderException("Could not retrieve channel for " + player.getName() + "!", e);
        }
    }
    
    public Object getEntityPlayer(Player player) throws ReflectionException {
        return Reflections.call(player, "getHandle()");
    }
    
    public Object getPlayerConnection(Object entityPlayer) throws ReflectionException {
        return Reflections.get(entityPlayer, "c");
    }
    
    public Object getNetworkManager(Object playerConnection) throws ReflectionException {
        return Reflections.getPrivate(playerConnection.getClass().getSuperclass(), playerConnection, "e");
    }
    
    public Object getChannel(Object networkManager) throws ReflectionException {
        return Reflections.getPrivate(networkManager, "n");
    }
    
    public int getStatusPacketEntity(Object packet) throws ReflectionException {
        return (Integer) Reflections.getPrivate(packet, "b");
    }
    
    public FunctionHotkeyFixOperatorLevel getStatusPacketStatus(Object packet) throws ReflectionException {
        return FunctionHotkeyFixOperatorLevel.fromStatusByte((Byte) Reflections.getPrivate(packet, "c"));
    }
    
    public void setStatusPacketStatus(Object packet, FunctionHotkeyFixOperatorLevel level) throws ReflectionException {
        Reflections.setPrivate(packet, "c", level.toStatusByte());
    }
    
    public Object makeStatusPacket(Object entityPlayer, byte status) throws ReflectionException {
        return Reflections.make("net.minecraft.network.protocol.game.PacketPlayOutEntityStatus(net.minecraft.world.entity.Entity,byte)", entityPlayer, status);
    }
    
    public boolean isStatusPacket(Object packet) throws ReflectionException {
        return Reflections.resolve("net.minecraft.network.protocol.game.PacketPlayOutEntityStatus").isInstance(packet);
    }
    
    public void sendPacket(Object networkManager, Object packet) throws ReflectionException {
        Reflections.call(networkManager, "a(net.minecraft.network.protocol.Packet)", packet);
    }
}