package com.muhardin.endy.belajar.ci.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/api")
public class FileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    @Value("${upload.location}")
    private String uploadLocation;

    @PostMapping("/file")
    public ResponseEntity uploadFile(@RequestParam("uploadfile") MultipartFile upload){
        Map<String, String> hasil = new TreeMap<>();
        try {
            File folderTujuan = new File(uploadLocation);
            if(!folderTujuan.exists()) {
                Files.createDirectories(folderTujuan.toPath());
            }
            if(!folderTujuan.canWrite()){
                LOGGER.error("Folder [{}] tidak bisa ditulis", folderTujuan.getAbsolutePath());
                hasil.put("err", "Folder tujuan readonly");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(hasil);
            }

            upload.transferTo(new File(folderTujuan.getAbsolutePath() + File.separator + upload.getOriginalFilename()));
            hasil.put("success", "File tersimpan di " + uploadLocation + "/" + upload.getOriginalFilename());
            return ResponseEntity.ok(hasil);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            hasil.put("err", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(hasil);
        }
    }

    @GetMapping("/file")
    public List<Map<String, Object>> daftarFile(){
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

    @GetMapping("/file/{nama:.+}")
    public ResponseEntity<Object> getFile(@PathVariable String nama){
        File file = new File(uploadLocation + File.separator + nama);
        Path filePath = Paths.get(file.getAbsolutePath());
        try {
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(filePath));
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+nama);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }

    }

    @DeleteMapping("/file/{nama}")
    public void delete(@PathVariable String nama){
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
}
