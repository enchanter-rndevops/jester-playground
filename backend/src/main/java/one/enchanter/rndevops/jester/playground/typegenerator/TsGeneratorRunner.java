package one.enchanter.rndevops.jester.playground.typegenerator;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import one.enchanter.rndevops.jester.playground.backend.controller.ApiResponse;

/**
 * TsGeneratorRunner
 * 
 * JavaのController(RestController)から、TypeScriptの型定義ファイルを作成する。
 * 
 */
@Component
public class TsGeneratorRunner implements ApplicationRunner {

    @Autowired
    private ApplicationContext context;

    public List<String> getRequestMappings(Class<?> clazz) {
        RequestMapping mapping = clazz.getAnnotation(RequestMapping.class);
        if (mapping.value() != null) {
            return Arrays.asList(mapping.value());
        }
        return List.of();
    }

    public List<String> getPostMappings(Method method) {
        PostMapping mapping = method.getAnnotation(PostMapping.class);
        if (mapping == null) {
            return List.of();
        }
        if (mapping.value() != null) {
            return Arrays.asList(mapping.value());
        }
        return List.of();
    }

    public List<String> getGetMappings(Method method) {
        GetMapping mapping = method.getAnnotation(GetMapping.class);
        if (mapping == null) {
            return List.of();
        }
        if (mapping.value() != null) {
            return Arrays.asList(mapping.value());
        }
        return List.of();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        StringBuilder ts = new StringBuilder();

        ts.append("""
                // API type generator for TypeScript.
                // Generated at %s

                """.formatted(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)));

        // ApiResponse（共通部分）の生成。
        ts.append(ApiResponseTsGenerator.generate());

        // Controller の Bean を走査
        Map<String, Object> controllers = context.getBeansWithAnnotation(RestController.class);

        for (Object controller : controllers.values()) {
            Class<?> clazz = controller.getClass();

            List<String> mappings = getRequestMappings(clazz);

            for (Method method : clazz.getDeclaredMethods()) {
                if (!method.isAnnotationPresent(PostMapping.class)
                        && !method.isAnnotationPresent(GetMapping.class)) {
                    continue;
                }

                ts.append("\n// --------\n");
                ts.append("// ");
                ts.append(clazz.getCanonicalName());
                ts.append("\n");

                for (String rm : getRequestMappings(clazz)) {
                    for (String m : getPostMappings(method)) {
                        ts.append("// POST:");
                        ts.append(rm + m);
                        ts.append("\n");
                    }
                    for (String m : getGetMappings(method)) {
                        ts.append("// GET:");
                        ts.append(rm + m);
                        ts.append("\n");
                    }
                }

                // RequestBody の record
                for (Parameter param : method.getParameters()) {
                    if (param.isAnnotationPresent(RequestBody.class)) {
                        Class<?> reqClass = param.getType();
                        if (reqClass.isRecord()) {
                            ts.append(TsInterfaceGenerator.generateTsInterface(reqClass));
                        }
                    }
                }

                // Response の record
                Class<?> returnType = method.getReturnType();
                if (returnType.equals(ApiResponse.class)) {
                    // ジェネリクス T を取り出す
                    Type genericReturn = method.getGenericReturnType();
                    if (genericReturn instanceof ParameterizedType pt) {
                        Type actualType = pt.getActualTypeArguments()[0];
                        if (actualType instanceof Class<?> dataClass && dataClass.isRecord()) {
                            ts.append(TsInterfaceGenerator.generateTsInterface(dataClass));
                        }
                    }
                }
            }
        }

        // 出力先は変更できるようにする。
        Path output = Paths.get("generated/api-types.ts");
        Files.writeString(output, ts.toString());

        System.out.println("TypeScript definitions generated: " + output.toAbsolutePath());
    }
}
