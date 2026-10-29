package one.enchanter.rndevops.jester.playground.typegenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;

/**
 * ControllerからTs用の型定義を生成。
 * 
 * mavenから実行する。
 * $ ./mvnw exec:java@generate-ts
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
  </configuration>
</plugin>
*/
//@formatter:on

@SpringBootApplication(scanBasePackages = {
        "one.enchanter.rndevops.jester.playground.backend.controller",
        "one.enchanter.rndevops.jester.playground.typegenerator"
}, exclude = {
        DataSourceAutoConfiguration.class,
        SecurityAutoConfiguration.class
})
public class TsGeneratorMain {
    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(TsGeneratorMain.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        var ctx = app.run(args);

    }
}