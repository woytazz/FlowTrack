package io.flowtrack.gateway.security.service;

import io.flowtrack.gateway.entity.Role;
import io.flowtrack.gateway.entity.User;
import io.flowtrack.gateway.repository.UserRepository;
import io.flowtrack.shared.security.SecurityAuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceProvider implements UserDetailsService {

    private static final String MESSAGE = "Username %s not found";

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(MESSAGE, username)));

        List<String> roles = user.getRoles().stream()
                .map(Role::getValue)
                .toList();

        List<SimpleGrantedAuthority> simpleGrantedAuthorities =
                SecurityAuthenticationUtil.getSimpleGrantedAuthorities(roles);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getEncryptedPassword(), simpleGrantedAuthorities
        );
    }

}
