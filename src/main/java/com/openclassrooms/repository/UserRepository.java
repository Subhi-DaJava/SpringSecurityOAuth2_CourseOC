package com.openclassrooms.repository;

import com.openclassrooms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * À noter que le prototype findByUsername permet de définir une requête dérivée.
     * @param username Username
     * @return User
     */
    public User findByUsername(String username);
}
