package one.enchanter.rndevops.jester.playground.backend.test;

import java.util.Collections;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import one.enchanter.rndevops.jester.playground.backend.UserPrincipal;

public class WithUserPrincipalSecurityContextFactory
        implements WithSecurityContextFactory<WithUserPrincipal> {

    @Override
    public SecurityContext createSecurityContext(
            WithUserPrincipal annotation) {

        UserPrincipal principal = new UserPrincipal(
                annotation.userId(),
                annotation.provider(),
                annotation.providedUserId());

        PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(
                principal,
                null,
                Collections.emptyList());

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        return context;
    }
}
