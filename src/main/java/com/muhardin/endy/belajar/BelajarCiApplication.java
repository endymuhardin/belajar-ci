package com.muhardin.endy.belajar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

import java.io.File;
import java.nio.file.Files;

@SpringBootApplication
@EnableJdbcHttpSession
public class BelajarCiApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(BelajarCiApplication.class, args);
	}

    private static final Logger LOGGER = LoggerFactory.getLogger(BelajarCiApplication.class);

    @Value("${upload.location}")
    private String uploadLocation;

    @Override
    public void run(String... args) throws Exception {
        File uploadFolder = new File(uploadLocation);
        LOGGER.info("Creating upload folder [{}] at [{}]", uploadFolder.toPath(), uploadFolder.getAbsolutePath());
        Files.createDirectories(uploadFolder.toPath());
    }
}
