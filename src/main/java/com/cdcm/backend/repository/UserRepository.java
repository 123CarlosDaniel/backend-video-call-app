package com.cdcm.backend.repository;

import com.cdcm.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findUserEntityById(UUID id);
    Optional<UserEntity> findUserEntityByUsername(String username);
}
