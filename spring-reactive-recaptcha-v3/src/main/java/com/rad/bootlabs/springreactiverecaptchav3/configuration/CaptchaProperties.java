package com.rad.bootlabs.springreactiverecaptchav3.configuration;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * google recaptcha configuration properties
 */
@Data
@ToString
@Configuration
@ConfigurationProperties(prefix = "google.recaptcha")
public class CaptchaProperties {

    private String secret;

    private String url;

    private double scoreThreshold;
}
