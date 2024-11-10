package br.com.glojura.demo_spring_security.configurations.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
@ConditionalOnProperty(name = "spring.security.strategy", havingValue = "JWT_SCOPE")
public class SecurityConfigJwtScopeBased {

    public static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/generate-token/**", "/public/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/admins/**").hasAuthority("SCOPE_admin.read")
//                        .requestMatchers(HttpMethod.GET,"/api/users/**").hasAuthority("SCOPE_user.read")
                        .requestMatchers(HttpMethod.POST, "/api/admins/**").hasAuthority("SCOPE_admin.write")
                        .requestMatchers(HttpMethod.POST,"/api/users/**").hasAuthority("SCOPE_user.write")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    static RoleHierarchy roleHierarchy() {

//        return RoleHierarchyImpl.withRolePrefix("SCOPE_")
//                .role("admin.read").implies("user.read")
//                .role("admin.write").implies("user.write")
//                .build();

        return RoleHierarchyImpl.fromHierarchy(
                """
                        SCOPE_admin.read > SCOPE_user.read
                        SCOPE_admin.write > SCOPE_user.write
                """
        );
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }

}