package net.sanctuaryhosting.functionHotkeyFix.reflections;

public class ReflectionException extends Exception{
    public ReflectionException(String message) {
        super(message);
    }
    
    public ReflectionException(String message, Throwable cause) {
        super(message, cause);
    }
}