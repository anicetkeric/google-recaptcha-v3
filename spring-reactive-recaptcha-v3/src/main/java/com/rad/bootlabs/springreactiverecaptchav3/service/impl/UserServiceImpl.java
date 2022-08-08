package com.rad.bootlabs.springreactiverecaptchav3.service.impl;

import com.rad.bootlabs.springreactiverecaptchav3.common.exception.EmailAlreadyUsedException;
import com.rad.bootlabs.springreactiverecaptchav3.common.exception.UsernameAlreadyUsedException;
import com.rad.bootlabs.springreactiverecaptchav3.model.User;
import com.rad.bootlabs.springreactiverecaptchav3.repository.UserRepository;
import com.rad.bootlabs.springreactiverecaptchav3.service.UserService;
import com.rad.bootlabs.springreactiverecaptchav3.service.mapper.UserMapper;
import com.rad.bootlabs.springreactiverecaptchav3.service.mapper.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;
    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<User> createUser(UserDTO dto) {

        return userRepository.findOneByUsernameIgnoreCase(dto.getUsername().toLowerCase())
                .flatMap(existingUser -> Mono.error(new UsernameAlreadyUsedException()))
                .cast(User.class)

                .switchIfEmpty(Mono.defer(() -> userRepository.findOneByEmailIgnoreCase(dto.getEmail()))
                        .flatMap(existingUser -> Mono.error(new EmailAlreadyUsedException()))
                        .cast(User.class)

                        .switchIfEmpty(Mono.defer(() -> {

                                    dto.setUsername(dto.getUsername().toLowerCase());
                                    User newUser = userMapper.userDTOToUser(dto);

                                    String encryptedPassword = passwordEncoder.encode(dto.getPassword());
                                    newUser.setPassword(encryptedPassword);
                                    //return userRepository.save(newUser);
                                    return Mono.just(newUser);
                                })
                        )
                );
    }
}
