package com.task.discounts.authentication.service;

import com.task.discounts.authentication.jwt.JwtUtils;
import com.task.discounts.authentication.model.UserDetailsImpl;
import com.task.discounts.authentication.payload.JwtResponse;
import com.task.discounts.common.exception.ServiceException;
import com.task.discounts.persistence.Role;
import com.task.discounts.persistence.RoleRepository;
import com.task.discounts.persistence.User;
import com.task.discounts.persistence.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * AuthService.
 *
 * @author AbdulMuhsin J. Al-Kandari
 */
@Service
@AllArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    public JwtResponse login(String username, String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = jwtUtils.generateJwtToken(auth);

        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles);
    }

    @Transactional
    public String register(String username, String password, Set<String> roles) {
        if(userRepository.existsByUsername(username)) {
            throw new ServiceException("Username is already taken.");
        }

        User user = new User(username, encoder.encode(password));
        final Set<Role> daoRoles = new HashSet<>();

        /*
            Add user role directly to requests that contain no roleSet, this is a design decision that eases usage.
            Some may argue that it introduces an unexpected sideEffect, but I believe that the tradeoff here is
            reasonable and should be taken unless there is a valid business/regulatory argument against it.
         */
        if(roles == null || roles.size() == 0) {
            roles = new HashSet<>();
            roles.add("user");
        }

        /* Decision point here: do we provide an API that successfully creates users
            that provide a roleSet which contains valid, and invalid roles with only the valid roles
            being given to the user? Or do we fail the whole request on any invalid roles?
            I prefer failing the request if even a single invalid role is provided, as I believe
            it produces the best user experience, especially if this API is utilized by developers
            outside your direct team. */
        roles.forEach(role -> {
                Role roleToAdd = roleRepository.findByName(role)
                        .orElseThrow(() -> new ServiceException("Role requested does not exist"));
                daoRoles.add(roleToAdd);
        });

        user.setRoles(daoRoles);
        return userRepository.save(user).getUsername();
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
