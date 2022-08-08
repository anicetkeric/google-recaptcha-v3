package com.rad.bootlabs.springreactiverecaptchav3.repository;

import com.rad.bootlabs.springreactiverecaptchav3.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Spring Data MongoDB repository for the {@link User} entity.
 */
@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findOneByEmailIgnoreCase(String email);

    Mono<User> findOneByUsernameIgnoreCase(String login);
}
