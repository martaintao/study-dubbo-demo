package com.martain.provide;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@EnableAutoConfiguration
public class ProviderServiceNacos {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ProviderServiceNacos.class).run(args);
    }
}
