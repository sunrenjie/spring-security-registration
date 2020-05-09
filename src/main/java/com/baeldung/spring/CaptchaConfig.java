package com.baeldung.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Configuration
@ComponentScan(basePackages = { "com.baeldung.captcha" })
public class CaptchaConfig {
    // Proxy configuration copied from https://stackoverflow.com/questions/3687670/
    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory(
            @Value("${google.recaptcha.proxyHost:}") String proxyHost,
            @Value("${google.recaptcha.proxyPort:0}") int proxyPort) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        if (!proxyHost.equals("") && proxyPort > 0 && proxyPort <= 65535) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
            factory.setProxy(proxy);
        }
        factory.setConnectTimeout(3 * 1000);
        factory.setReadTimeout(7 * 1000);
        return factory;
    }

    @Bean
    public RestOperations restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        return restTemplate;
    }
}
