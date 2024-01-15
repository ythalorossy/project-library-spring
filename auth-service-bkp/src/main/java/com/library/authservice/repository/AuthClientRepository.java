package com.library.authservice.repository;

import java.util.Optional;

import com.library.authservice.domain.AuthClientDetails;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthClientRepository extends MongoRepository<AuthClientDetails, String> {
    Optional<AuthClientDetails> findByClientId(String clientId);
}