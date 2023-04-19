package de.redgames.f3nperm.provider;

import de.redgames.f3nperm.reflection.ReflectionException;
import de.redgames.f3nperm.reflection.Reflections;

public class ReflectionProvider_1_19_4 extends ReflectionProvider_1_19 {
    public Object getPlayerConnection(Object entityPlayer) throws ReflectionException {
        return Reflections.get(entityPlayer, "b");
    }
}
