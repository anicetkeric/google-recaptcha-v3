package com.rad.bootlabs.springreactiverecaptchav3.web;

import com.rad.bootlabs.springreactiverecaptchav3.common.exception.InvalidPasswordException;
import com.rad.bootlabs.springreactiverecaptchav3.model.User;
import com.rad.bootlabs.springreactiverecaptchav3.service.CaptchaService;
import com.rad.bootlabs.springreactiverecaptchav3.service.UserService;
import com.rad.bootlabs.springreactiverecaptchav3.service.mapper.dto.ApiResponseDTO;
import com.rad.bootlabs.springreactiverecaptchav3.service.mapper.dto.UserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * REST controller for managing the current user's account.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final CaptchaService captchaService;

    /**
     * {@code POST  /register} : register the user.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ApiResponseDTO> registerAccount(@Valid @RequestBody UserDTO userDTO) {
        if (!isPasswordMatches(userDTO.getPassword(), userDTO.getPasswordConfirmation())) {
            throw new InvalidPasswordException("Password not matches");
        }

       return captchaService.verify(userDTO.getCaptchaToken())
               .flatMap(u -> userService.createUser(userDTO))
               .map(r -> new ApiResponseDTO(r, "Registration successful"));
    }

    private static boolean isPasswordMatches(String password, String passwordConfirmation) {
        return (StringUtils.hasText(StringUtils.trimAllWhitespace(password)) && StringUtils.hasText(StringUtils.trimAllWhitespace(passwordConfirmation))
                        && password.equals(passwordConfirmation));
    }

}
