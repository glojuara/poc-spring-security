package br.com.glojura.demo_spring_security.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {


    @GetMapping
    public ResponseEntity<List<String>> listUsers() {
        return ResponseEntity.ok(List.of("admin1", "admin2"));
    }

}
