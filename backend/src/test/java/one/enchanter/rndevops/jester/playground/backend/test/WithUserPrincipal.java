package one.enchanter.rndevops.jester.playground.backend.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.test.context.support.WithSecurityContext;

@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithUserPrincipalSecurityContextFactory.class)
public @interface WithUserPrincipal {

    long userId() default 1L;

    String provider() default "test";

    String providedUserId() default "test-user";
}