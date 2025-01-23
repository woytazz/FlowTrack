package io.flowtrack.shared.security;

import io.flowtrack.shared.common.CommonConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityAuthenticationUtil {

    public static List<SimpleGrantedAuthority> getSimpleGrantedAuthorities(List<String> roles) {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public static String getAuthenticationName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (checkIsAuthenticated(authentication)) {
            return authentication.getName();
        }

        if (checkIsAnonymous(authentication)) {
            return CommonConstants.ANONYMOUS;
        }

        if (checkIsUnauthenticated(authentication)) {
            return CommonConstants.UNAUTHENTICATED;
        }

        return CommonConstants.SYSTEM;
    }

    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return checkIsAuthenticated(authentication);
    }

    public static boolean isAnonymous() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return checkIsAnonymous(authentication);
    }

    public static boolean isUnauthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return checkIsUnauthenticated(authentication);
    }

    private static boolean checkIsAuthenticated(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken);
    }

    private static boolean checkIsAnonymous(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated() &&
                authentication instanceof AnonymousAuthenticationToken;
    }

    private static boolean checkIsUnauthenticated(Authentication authentication) {
        return authentication != null && !authentication.isAuthenticated();
    }

}
