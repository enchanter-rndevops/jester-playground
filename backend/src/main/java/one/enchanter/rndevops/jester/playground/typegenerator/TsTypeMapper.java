package one.enchanter.rndevops.jester.playground.typegenerator;

import java.time.LocalDateTime;
import java.util.List;

public class TsTypeMapper {
    public static String mapType(Class<?> clazz) {
        if (clazz.equals(String.class))
            return "string";
        if (clazz.equals(Integer.class) || clazz.equals(int.class))
            return "number";
        if (clazz.equals(Long.class) || clazz.equals(long.class))
            return "number";
        if (clazz.equals(Boolean.class) || clazz.equals(boolean.class))
            return "boolean";
        if (clazz.equals(LocalDateTime.class))
            return "string"; // ISO datetime
        if (clazz.equals(List.class))
            return "any[]";
        if (clazz.isEnum())
            return clazz.getSimpleName();
        if (clazz.isRecord())
            return clazz.getSimpleName();
        return "any";
    }
}
