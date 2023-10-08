package com.openclassrooms.configuration;

import com.openclassrooms.model.User;
import com.openclassrooms.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe est annotée @Service pour être détectée par Spring et elle implémente l’interface UserDetailsService.
 * La création d’une classe implémentant l’interface UserDetailsService permet d’authentifier des utilisateurs
 * sur la base des informations contenues dans une base de données.
 * La configuration de Spring Security doit prendre en compte cette classe via un AuthenticationManager.
 */
@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Cette méthode est capitale car elle sera automatiquement appelé par Spring Security lors de l’authentification de l’utilisateur.
     * Elle renvoie un objet UserDetails, autrement dit la représentation d’un utilisateur.
     * @param username Username
     * @return UserDetails
     * @throws UsernameNotFoundException ex
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user.getRole()));
    }

    private List<GrantedAuthority> getGrantedAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }

}
