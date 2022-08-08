package com.rad.bootlabs.springreactiverecaptchav3.common.exception;

import com.rad.bootlabs.springreactiverecaptchav3.service.mapper.dto.ApiResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ApiResponseDTO> handleRequestBodyError(WebExchangeBindException ex, ServerWebExchange exchange){
        log.error("Exception caught in handleRequestBodyError :  {} " ,ex.getMessage(),  ex);
        var error = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .sorted()
                .collect(Collectors.joining(","));
        log.error("errorList : {}", error);
        return Mono.just(new ApiResponseDTO(error, ex.getMessage()));
    }

    @ExceptionHandler({InvalidPasswordException.class, InvalidCaptchaException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ApiResponseDTO> handleBadRequestException(Exception ex, ServerWebExchange exchange) {
        log.debug(ex.getMessage(), ex.getCause());
        return Mono.just(new ApiResponseDTO(null, ex.getMessage()));

    }
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ApiResponseDTO> handleException(Exception ex, ServerWebExchange exchange) {
        log.debug(ex.getMessage(), ex.getCause());
        return Mono.just(new ApiResponseDTO(null, ex.getMessage()));

    }

}
