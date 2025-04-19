package com.proje.fitnesapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomLoginSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/css/**", "/js/**", "/images/**", "/login.css", "/register.css").permitAll()

                        // Admin paneli sadece ADMIN rolüne açık
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Bu yollar hem USER hem ADMIN için açık
                        .requestMatchers("/profile", "/profile/edit", "/profile/change-password", "/profile/picture")
                        .hasAnyRole("USER", "ADMIN")

                        // Üyelik satın alma gibi işlemler sadece USER'a açık
                        .requestMatchers("/membership/**").hasAnyRole("USER", "ADMIN")


                        // Diğer tüm istekler sadece giriş yapmış kullanıcılara açık
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .successHandler(successHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


