package com.muhardin.endy.belajar.ci.service.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
@Profile({ "default", "localstorage" })
public class LocalFileService implements FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalFileService.class);

    @Value("${upload.location}")
    private String uploadLocation;

    @Override
    public void simpan(String path, InputStream contentStream) {
        File folderTujuan = new File(uploadLocation);
        if(!folderTujuan.exists()) {
            try {
                Files.createDirectories(folderTujuan.toPath());
            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.error("Folder [{}] tidak bisa dibuat", folderTujuan.getAbsolutePath());
                throw new IllegalStateException("Folder tujuan tidak bisa dibuat");
            }
        }
        if(!folderTujuan.canWrite()){
            LOGGER.error("Folder [{}] tidak bisa ditulis", folderTujuan.getAbsolutePath());
            throw new IllegalStateException("Folder tujuan tidak bisa ditulis");
        }
        Path fileTujuan = folderTujuan.toPath().resolve(path);
        try {
            Files.copy(contentStream, fileTujuan);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("File [{}] tidak bisa ditulis", fileTujuan.toAbsolutePath().toString());
            throw new IllegalStateException("File tujuan tidak bisa ditulis");
        }
    }

    @Override
    public List<Map<String, Object>> daftarFile() {
        List<Map<String, Object>> hasil = new ArrayList<>();
        try {
            Files.find(Paths.get(uploadLocation), 2, (path, fileAttr) -> fileAttr.isRegularFile())
                    .forEach(p -> {
                        try {
                            Map<String, Object> file = new TreeMap<>();
                            file.put("nama", p.getFileName().toString());
                            file.put("ukuran", Files.size(p));
                            hasil.add(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hasil;
    }

    @Override
    public void hapus(String nama) {
        try {
            Files.find(Paths.get(uploadLocation), 2,
                    (path, fileAttr) -> path.toString().contains(nama))
                    .forEach(p -> {
                        try {
                            Files.delete(p);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InputStream ambil(String nama) {
        File file = new File(uploadLocation + File.separator + nama);
        Path filePath = Paths.get(file.getAbsolutePath());
        try {
            return new ByteArrayResource(Files.readAllBytes(filePath)).getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
