package com.openclassrooms.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * La classe HttpSecurity est sollicitée pour appliquer la chaîne de filtres de sécurité aux requêtes HTTP.
 * Par défaut, les paramètres de sécurité fonctionnent sur toutes les requêtes.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    /**
     * L’annotation @Bean aura pour conséquence de charger dans le contexte Spring l’objet résultant de la méthode filterChain.
     * Ainsi Spring Security pourra s’en servir.
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception e
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // La chaîne de filtres de sécurité est programmée avec HTTPSecurity.
        return http

                .authorizeHttpRequests(auth -> { // définir les rôles
                    auth.requestMatchers("/admin").hasRole("ADMIN"); // l'association des rôles USER (utilisateur) et ADMIN (administrateur) avec des pages.
                    auth.requestMatchers("/user").hasRole("USER");
                    auth.anyRequest().authenticated(); // permettre d’utiliser le formulaire ci-dessous pour l’authentification.
                })
                .formLogin(Customizer.withDefaults())// Démarrer par le formulaire Spring Security par défaut, en utilisant la méthode `formLogin (Customizer.withDefaults())`
                .oauth2Login(Customizer.withDefaults())
                .build(); // envoyer une implémentation de SecurityFilterChain
    }


    // créer une chaîne de filtres de sécurité pour l'étape d’authentification
    // L’objet UserDetailsService permet d’accéder à des utilisateurs.
    @Bean
    public UserDetailsService users() {
        String userPassword = passwordEncoder().encode("12345");
        String adminPassword = passwordEncoder().encode("12345");
        UserDetails user = User.builder()
                .username("user")
                .password(userPassword)
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(adminPassword)
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }


    // BCrypt est l’un des algorithmes d’encodage les plus reconnus en ce qui concerne les mots de passe.
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
