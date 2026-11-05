package one.enchanter.rndevops.jester.playground.typegenerator;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.RecordComponent;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TsInterfaceGenerator {

    private final Set<Class<?>> generated = new LinkedHashSet<>();
    private final Map<String, Class<?>> generatedNames = new java.util.HashMap<>();

    /**
     * rootClassから辿れるDTOを全て収集し、
     * TypeScript interfaceを生成する。
     */
    public String generate(Class<?> rootClass) {

        LinkedHashSet<Class<?>> collected = new LinkedHashSet<>();

        collectTypes(rootClass, collected);

        StringBuilder sb = new StringBuilder();

        for (Class<?> clazz : collected) {
            sb.append(generateSingleInterface(clazz));
        }

        return sb.toString();
    }

    /**
     * DTO依存関係収集
     */
    private void collectTypes(
            Class<?> clazz,
            Set<Class<?>> result) {

        if (clazz == null) {
            return;
        }
        if (isBuiltin(clazz)) {
            return;
        }
        if (clazz.isEnum()) {
            return;
        }
        if (!result.add(clazz)) {
            return;
        }
        if (!generated.add(clazz)) {
            return;
        }

        if (clazz.isRecord()) {
            for (RecordComponent rc : clazz.getRecordComponents()) {

                System.out.println("record="
                        + rc.getName()
                        + " type="
                        + rc.getGenericType());

                collectTypes(rc.getGenericType(), result);
            }

        } else {

            for (Field field : clazz.getDeclaredFields()) {

                System.out.println("field="
                        + field.getName()
                        + " type="
                        + field.getGenericType());

                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }

                collectTypes(field.getGenericType(), result);
            }
        }

    }

    /**
     * Generic対応
     */
    private void collectTypes(
            Type type,
            Set<Class<?>> result) {

        if (type instanceof Class<?> clazz) {
            collectTypes(clazz, result);
            return;
        }

        if (type instanceof ParameterizedType pt) {

            for (Type arg : pt.getActualTypeArguments()) {
                collectTypes(arg, result);
            }
        }
    }

    /**
     * interfaceを1個生成
     */
    private String generateSingleInterface(
            Class<?> clazz) {

        StringBuilder sb = new StringBuilder();

        String name = clazz.getSimpleName();
        Class<?> existing = generatedNames.putIfAbsent(
                name,
                clazz);

        if (existing != null &&
                !existing.equals(clazz)) {

            throw new IllegalStateException(
                    "Duplicate DTO name: "
                            + name
                            + "\n"
                            + existing.getName()
                            + "\n"
                            + clazz.getName());
        }

        sb.append("export interface ")
                .append(clazz.getSimpleName())
                .append(" {\n");

        if (clazz.isRecord()) {

            for (RecordComponent rc : clazz.getRecordComponents()) {

                sb.append("  ")
                        .append(rc.getName())
                        .append(": ")
                        .append(mapType(
                                rc.getGenericType()))
                        .append(";\n");
            }

        } else {

            for (Field field : clazz.getDeclaredFields()) {

                if (Modifier.isStatic(
                        field.getModifiers())) {
                    continue;
                }

                sb.append("  ")
                        .append(field.getName())
                        .append(": ")
                        .append(mapType(
                                field.getGenericType()))
                        .append(";\n");
            }
        }

        sb.append("}\n\n");

        return sb.toString();
    }

    /**
     * Java Type → TypeScript
     */
    private String mapType(Type type) {

        if (type instanceof Class<?> clazz) {

            if (clazz == String.class) {
                return "string";
            }

            if (clazz == Integer.class
                    || clazz == int.class
                    || clazz == Long.class
                    || clazz == long.class
                    || clazz == Double.class
                    || clazz == double.class
                    || clazz == Float.class
                    || clazz == float.class) {
                return "number";
            }

            if (clazz == Boolean.class
                    || clazz == boolean.class) {
                return "boolean";
            }

            if (clazz == LocalDateTime.class) {
                return "string";
            }

            if (clazz == Void.class) {
                return "void";
            }

            if (clazz.isEnum()) {
                return clazz.getSimpleName();
            }

            return clazz.getSimpleName();
        }

        if (type instanceof ParameterizedType pt) {

            Type raw = pt.getRawType();

            if (raw == List.class) {

                Type element = pt.getActualTypeArguments()[0];

                return mapType(element) + "[]";
            }
        }

        return "any";
    }

    /**
     * TS生成対象にしない型
     */
    private boolean isBuiltin(
            Class<?> clazz) {

        return clazz.isPrimitive()
                || clazz == String.class
                || clazz == Integer.class
                || clazz == Long.class
                || clazz == Double.class
                || clazz == Float.class
                || clazz == Boolean.class
                || clazz == LocalDateTime.class
                || clazz == Void.class;
    }
}