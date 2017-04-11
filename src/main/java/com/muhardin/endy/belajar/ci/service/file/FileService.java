package com.muhardin.endy.belajar.ci.service.file;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface FileService {
    public void simpan(String nama, InputStream contentStream);
    public List<Map<String, Object>> daftarFile();
    public void hapus(String nama);
    public InputStream ambil(String nama);
}
