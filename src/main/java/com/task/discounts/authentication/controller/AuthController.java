package com.task.discounts.authentication.controller;

import com.task.discounts.authentication.payload.AuthRequest;
import com.task.discounts.authentication.payload.JwtResponse;
import com.task.discounts.authentication.payload.RegisterRequest;
import com.task.discounts.authentication.service.AuthService;
import com.task.discounts.persistence.Role;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;
import java.util.List;

/**
 * AuthController.
 *
 * @author AbdulMuhsin J. Al-Kandari
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Validated
public class AuthController {

    /*
        Ideal case scenario you would make the service as an interface instead of an implementation to allow for
        multiple service implementations. I believe such an abstraction while beneficial IF you have multiple
        implementations does more harm than good when you only have a single concrete implementation, especially
        given an IDEs ability to generate an interface dynamically from a concrete implementation should the need
        arise.
    */
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request.getUsername(), request.getPassword()));
    }

    /*
        In real-world scenarios the regular registration endpoint would define standard roles
        and there would be administrative endpoints that would allow role access managers to
        assign roles dynamically. For the task we believe it is safe to ignore such a requirement
        in favor of time, as it does not showcase any skills related to the task.
    */
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.created(URI.create(
                authService.register(request.getUsername(), request.getPassword(), request.getRoles())
        )).build();
    }

    @GetMapping("/role")
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok(authService.getRoles());
    }
}
