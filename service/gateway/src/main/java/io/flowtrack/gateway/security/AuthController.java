package io.flowtrack.gateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    public static final String LOGIN_URL = "/auth/login";

    @GetMapping("/1")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String one() {
        return "One";
    }
}
