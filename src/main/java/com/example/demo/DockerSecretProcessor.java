package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DockerSecretProcessor implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        String environmentProperty = environment.getProperty("docker-secret.bind-path");

        Map<String, Object> dockerSecrets = new LinkedHashMap<>();
        if (environmentProperty != null) {
            File dockerFile = new File(environmentProperty);

            if (dockerFile.exists()) {
                Optional<File[]> files = Optional.ofNullable(dockerFile.listFiles());

                files.ifPresent(item -> {
                    List<File> itemArray = Arrays.asList(item);
                    itemArray.forEach(secretFile -> {
                        String key = "docker-secret-" + secretFile.getName();
                        byte[] content;
                        try {
                            content = FileCopyUtils.copyToByteArray(secretFile);
                            dockerSecrets.put(key, new String(content));
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                        }
                    });

                });
            }

            MapPropertySource propertySource = new MapPropertySource("docker-secrets", dockerSecrets);

            environment.getPropertySources().addLast(propertySource);

        }
    }
}
