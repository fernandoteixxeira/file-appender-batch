package com.github.fernandotaa.fileappenderbatch.config;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
public class FileConfiguration {

    @SneakyThrows
    @Bean
    public boolean initFile(@Value("${app.file}") Path path) {
        if (Files.notExists(path)) {
            Files.createFile(path);
            return true;
        }
        return false;
    }

}
