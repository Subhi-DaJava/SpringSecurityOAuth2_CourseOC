package com.openclassrooms.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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

        return http.build(); // envoyer une implémentation de SecurityFilterChain
    }
}
