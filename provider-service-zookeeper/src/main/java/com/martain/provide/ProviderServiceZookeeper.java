package com.martain.provide;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@EnableAutoConfiguration
public class ProviderServiceZookeeper {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ProviderServiceZookeeper.class).run(args);
    }
}
