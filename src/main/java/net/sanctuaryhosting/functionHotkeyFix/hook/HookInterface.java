package net.sanctuaryhosting.functionHotkeyFix.hook;

import net.sanctuaryhosting.functionHotkeyFix.FunctionHotkeyFixPlugin;

public interface HookInterface{
    String getName();
    
    void register(FunctionHotkeyFixPlugin plugin);
    
    void unregister(FunctionHotkeyFixPlugin plugin);
}