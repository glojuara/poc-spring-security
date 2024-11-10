package br.com.glojura.demo_spring_security.configurations.security;

import org.springframework.stereotype.Component;

@Component("custom")
public class CustomSecurity {

    public boolean decide(String operator) {
        return "admin".equalsIgnoreCase(operator);
    }

}
