package net.sanctuaryhosting.FunctionHotkeyFix.hook;

import net.sanctuaryhosting.FunctionHotkeyFix.FunctionHotkeyFixPlugin;

public interface HookInterface{
    String getName();
    
    void register(FunctionHotkeyFixPlugin plugin);
    
    void unregister(FunctionHotkeyFixPlugin plugin);
}