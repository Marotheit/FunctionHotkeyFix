package net.sanctuaryhosting.FunctionHotkeyFix.reflections;

public class ReflectionException extends Exception{
    public ReflectionException(String message) {
        super(message);
    }
    
    public ReflectionException(String message, Throwable cause) {
        super(message, cause);
    }
}