package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.UserDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDomain, Integer>, JpaSpecificationExecutor<UserDomain> {
    Optional<UserDomain> findByUsername(String username); // Méthode pour trouver un utilisateur par son nom d'utilisateur
}
