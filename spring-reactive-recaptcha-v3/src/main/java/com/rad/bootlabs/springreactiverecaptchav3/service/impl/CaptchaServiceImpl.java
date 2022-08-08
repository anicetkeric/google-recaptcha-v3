package com.rad.bootlabs.springreactiverecaptchav3.service.impl;

import com.rad.bootlabs.springreactiverecaptchav3.common.exception.InvalidCaptchaException;
import com.rad.bootlabs.springreactiverecaptchav3.configuration.CaptchaProperties;
import com.rad.bootlabs.springreactiverecaptchav3.service.CaptchaService;
import com.rad.bootlabs.springreactiverecaptchav3.service.mapper.dto.RecaptchaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class CaptchaServiceImpl implements CaptchaService {

    public final CaptchaProperties captchaProperties;
    private final WebClient.Builder webclientBuilder;

    @Override
    public Mono<RecaptchaResponse> verify(String tokenResponse) {

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("secret", captchaProperties.getSecret());
        formData.add("response", tokenResponse);

        return webclientBuilder.build().post()
                .uri(captchaProperties.getUrl())
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(RecaptchaResponse.class)
                .doOnSuccess(recaptchaResponse -> {
                    log.info("response verify captcha: {}", recaptchaResponse.toString());

                    if (!recaptchaResponse.isSuccess()){
                        throw new InvalidCaptchaException("reCaptcha v3 was not successfully validated");
                    }

                    if(recaptchaResponse.isSuccess() &&
                            recaptchaResponse.getScore() < captchaProperties.getScoreThreshold()){
                        throw new InvalidCaptchaException("Low score for reCaptcha v3");
                    }
                })
                .doOnError(e -> {
                    log.error("error verify captcha : {}", e.getMessage());
                    throw new InvalidCaptchaException(e.getMessage());
                });
    }
}
