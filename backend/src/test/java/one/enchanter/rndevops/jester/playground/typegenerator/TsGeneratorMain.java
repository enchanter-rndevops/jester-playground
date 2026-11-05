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

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import one.enchanter.rndevops.jester.playground.backend.controller.ApiResponse;

/**
 * ControllerからTs用の型定義を生成。
 * 
 * mavenから実行する。
 * $ ./mvnw exec:java@generate-ts
 * 
 * ネストしたDTOも再帰的に生成する。
 * List<T> のジェネリクス対応。
 * Map は any になる。
 * 同じ名前のDTOが複数ある場合、最初に見つかったものを採用する。
 * 
 */
//@formatter:off
 /* 
mavenから実行するため、pom.xml にこれを追加。
<plugin>
  <groupId>org.codehaus.mojo</groupId>
  <artifactId>exec-maven-plugin</artifactId>
  <version>3.1.0</version>
  <executions>
    <execution>
      <id>generate-ts</id>
        <goals>
          <goal>java</goal>
        </goals>
    </execution>
  </executions>
  <configuration>
    <mainClass>one.enchanter.rndevops.jester.playground.typegenerator.TsGeneratorMain</mainClass>
	<classpathScope>test</classpathScope>
  </configuration>
</plugin>
*/
//@formatter:on

public class TsGeneratorMain {

    public static void main(String[] args) throws Exception {
        TsGeneratorMain generator = new TsGeneratorMain();
        generator.run(
                "one.enchanter.rndevops.jester.playground.backend.controller");
    }

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

    public void run(String basePackage) throws Exception {
        StringBuilder ts = new StringBuilder();

        TsInterfaceGenerator tsInterfaceGenerator = new TsInterfaceGenerator();

        ts.append("""
                // API type generator for TypeScript.
                // Generated at %s

                """.formatted(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)));

        // ApiResponse（共通部分）の生成。
        ts.append(ApiResponseTsGenerator.generate());

        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(
                new AnnotationTypeFilter(RestController.class));
        List<?> controllers = scanner
                .findCandidateComponents(basePackage)
                .stream()
                .map(BeanDefinition::getBeanClassName)
                .map(className -> {
                    try {
                        return Class.forName(className);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();

        for (Object c : controllers) {
            Class<?> clazz = (Class<?>) c;

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
                        System.out.println(reqClass.getSimpleName());
                        ts.append(tsInterfaceGenerator.generate(reqClass));
                    }
                }

                // Response の record
                Class<?> returnType = method.getReturnType();
                if (returnType.equals(ApiResponse.class)) {
                    // ジェネリクス T を取り出す
                    Type genericReturn = method.getGenericReturnType();
                    if (genericReturn instanceof ParameterizedType pt) {
                        Type actualType = pt.getActualTypeArguments()[0];
                        if (actualType instanceof Class<?> dataClass) {
                            ts.append(tsInterfaceGenerator.generate(dataClass));
                        }
                    }
                }
            }
        }

        // 出力先は設定で変更できるようにする。
        Path output = Paths.get("./generated/api-types.ts");
        Files.writeString(output, ts.toString());

        System.out.println("TypeScript definitions generated: " + output.toAbsolutePath());
    }

}