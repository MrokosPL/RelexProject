package com.mrokos.RelexProject.repositories;

import com.mrokos.RelexProject.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername (String username);
    Optional<User> findByEmail (String email);

}
