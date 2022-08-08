package com.rad.bootlabs.springreactiverecaptchav3.service;

import com.rad.bootlabs.springreactiverecaptchav3.service.mapper.dto.RecaptchaResponse;
import reactor.core.publisher.Mono;

public interface CaptchaService {

    Mono<RecaptchaResponse> verify(String response);
}
