package br.com.glojura.demo_spring_security.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @GetMapping
    @PreAuthorize("@custom.decide(#operator) || hasAuthority('SCOPE_user.read')")
    public ResponseEntity<List<String>> listUsers(@RequestParam String operator) {
        return ResponseEntity.ok(List.of("user1", "user2"));
    }

}
