package com.rad.bootlabs.springreactiverecaptchav3.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * google recaptcha configuration properties
 */
@ConfigurationProperties(prefix = "google.recaptcha")
public record CaptchaProperties(String secret, String url, double scoreThreshold) {
}
