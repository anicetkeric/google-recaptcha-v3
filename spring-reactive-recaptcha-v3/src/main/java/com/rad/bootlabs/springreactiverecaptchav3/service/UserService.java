package com.rad.bootlabs.springreactiverecaptchav3.service;

import com.rad.bootlabs.springreactiverecaptchav3.model.User;
import com.rad.bootlabs.springreactiverecaptchav3.service.mapper.dto.UserDTO;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> createUser(UserDTO userPasswordDTO);
}
