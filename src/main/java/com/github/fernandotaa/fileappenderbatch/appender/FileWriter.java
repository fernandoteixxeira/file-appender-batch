package com.github.fernandotaa.fileappenderbatch.appender;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Slf4j
@Component
public class FileWriter extends AbstractFileWriter {

    private final Path path;

    public FileWriter(@Value("${app.file}") Path path) {
        this.path = path;
    }

    public void append(final String text) {
        boolean processed = false;
        do {
            try {
                Files.writeString(path, text + System.lineSeparator(), StandardOpenOption.APPEND);
                processed = true;
            } catch (IOException e) {
                log.error("ERROR to append file", e);
            }
        } while (!processed);
    }

}
