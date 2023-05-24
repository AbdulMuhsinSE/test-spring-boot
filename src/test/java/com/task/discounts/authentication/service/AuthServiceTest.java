package com.task.discounts.authentication.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.task.discounts.authentication.jwt.JwtUtils;
import com.task.discounts.authentication.model.UserDetailsImpl;
import com.task.discounts.authentication.payload.JwtResponse;
import com.task.discounts.common.exception.ServiceException;
import com.task.discounts.persistence.Role;
import com.task.discounts.persistence.RoleRepository;
import com.task.discounts.persistence.User;
import com.task.discounts.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

class AuthServiceTest {

    AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    RoleRepository roleRepository = Mockito.mock(RoleRepository.class);
    PasswordEncoder encoder = Mockito.spy(PasswordEncoder.class);
    JwtUtils jwtUtils = Mockito.spy(JwtUtils.class);
    AuthService service = Mockito.spy(new AuthService(authenticationManager, userRepository,
            roleRepository, encoder, jwtUtils));

    @Test
    void login() {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhamprIiwiaWF0IjoxNjg0OTk2Mjk5LCJleHAiOjE2ODQ5OTc0OTl9.qMZ1I3CUtd9ESVNtBgFTdyhZqad0odRq5fiIf9GQBbDYL-i11EZ84ffRmz4p3jre7SMMQDQkGBAr6G2VwZL2cg";
        Authentication auth = Mockito.mock(Authentication.class);

        GrantedAuthority grantedAuthority = Mockito.mock(GrantedAuthority.class);
        doReturn("user").when(grantedAuthority).getAuthority();

        UserDetailsImpl userDetails = new UserDetailsImpl("646efda5c49b6c540a0ce209",
                "ajjk",
                "12345678",
                LocalDate.now(), Set.of(grantedAuthority));

        doReturn(userDetails).when(auth).getPrincipal();
        doReturn(auth).when(authenticationManager).authenticate(any());
        doReturn(token).when(jwtUtils).generateJwtToken(any());

        List<String> roles = List.of("user");
        JwtResponse response = new JwtResponse(token, "646efda5c49b6c540a0ce209",
                "ajjk", roles);

        assertEquals(response, service.login("ajjk", "12345678"));
    }

    @Test
    void register_success() {
        doReturn(Boolean.FALSE).when(userRepository).existsByUsername(any());

        Role userRole = new Role();
        userRole.setName("user");
        userRole.setDescription("user role");

        doReturn(Optional.of(userRole)).when(roleRepository).findByName(any());

        User user = new User("alkandari", "12345678");
        user.setRoles(Set.of(userRole));

        doReturn(user).when(userRepository).save(any());

        assertEquals("alkandari",service
                .register("alkandandari", "12345678", null));
    }

    @Test
    void register_fail_duplicate_user() {
        doReturn(Boolean.TRUE).when(userRepository).existsByUsername(any());
        assertThrows(ServiceException.class, () -> service.register("ajk", "12345678", null));
    }

    @Test
    void register_fail_role_does_not_exist() {
        doReturn(Boolean.FALSE).when(userRepository).existsByUsername(any());
        doReturn(Optional.empty()).when(roleRepository).findByName(any());
        assertThrows(ServiceException.class, () -> service.register("ajk", "12345678", new HashSet<>()));
    }

    @Test
    void getRoles() {
        doReturn(List.of(new Role())).when(roleRepository).findAll();
        assertEquals(new Role(), service.getRoles().get(0));
    }
}