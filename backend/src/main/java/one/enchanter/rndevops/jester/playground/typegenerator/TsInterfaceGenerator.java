package one.enchanter.rndevops.jester.playground.typegenerator;

import java.lang.reflect.RecordComponent;

public class TsInterfaceGenerator {

    public static String generateTsInterface(Class<?> recordClass) {
        if (!recordClass.isRecord()) {
            throw new IllegalArgumentException("Not a record: " + recordClass.getName());
        }

        StringBuilder sb = new StringBuilder();
        sb.append("export interface ").append(recordClass.getSimpleName()).append(" {\n");

        for (RecordComponent rc : recordClass.getRecordComponents()) {
            String tsType = TsTypeMapper.mapType(rc.getType());
            sb.append("  ").append(rc.getName()).append(": ").append(tsType).append(";\n");
        }

        sb.append("}\n\n");
        return sb.toString();
    }
}
