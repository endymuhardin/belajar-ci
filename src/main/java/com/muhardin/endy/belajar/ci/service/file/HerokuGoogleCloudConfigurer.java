package com.muhardin.endy.belajar.ci.service.file;

import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileWriter;
import java.io.IOException;

@Component
@Profile("heroku")
public class HerokuGoogleCloudConfigurer {
    @Value("${GOOGLECLOUD_AUTH_CONTENT}")
    private String googleCredentialFileContent;

    @PostConstruct
    public void writeEnvironmentVariableToFile(){
        try (FileWriter writer = new FileWriter("google-credential.json")) {
            writer.write(googleCredentialFileContent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
}
