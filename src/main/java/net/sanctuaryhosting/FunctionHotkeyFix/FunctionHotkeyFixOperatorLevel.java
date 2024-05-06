package net.sanctuaryhosting.FunctionHotkeyFix;

public enum FunctionHotkeyFixOperatorLevel{
    NO_PERMISSIONS(0), ACCESS_SPAWN(1), WORLD_COMMANDS(2), PLAYER_COMMANDS(3), ADMIN_COMMANDS(4);
    private final int level;
    
    FunctionHotkeyFixOperatorLevel(int level) {
        this.level = level;
    }
    
    public byte toStatusByte() {
        final int baseOffset = 24;
        return (byte) (level + baseOffset);
    }
    
    private static final FunctionHotkeyFixOperatorLevel[] values = values();
    
    public static FunctionHotkeyFixOperatorLevel fromStatusByte(byte statusByte) {
        for (FunctionHotkeyFixOperatorLevel value : values) {
            if (value.toStatusByte() == statusByte) {
                return value;
            }
        }
        
        return null;
    }
}