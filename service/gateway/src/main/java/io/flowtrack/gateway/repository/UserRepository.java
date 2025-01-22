package io.flowtrack.gateway.repository;

import io.flowtrack.gateway.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select * from {h-schema}user u where u.username = :username", nativeQuery = true)
    Optional<User> findByUsername(@Param("username") String username);

}
