package br.com.glojura.demo_spring_security.controllers;

import br.com.glojura.demo_spring_security.services.TokenGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @GetMapping("/generate-token")
    public Map<String,String> generateToken(@RequestParam String scopes) {
        Map<String, String> response = new HashMap<>();
        response.put("access_token", TokenGenerator.generateToken("user", scopes));
        return response;
    }

}
