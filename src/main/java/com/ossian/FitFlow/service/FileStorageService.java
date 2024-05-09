package com.ossian.FitFlow.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    void init();
    String storeFile(MultipartFile file);
    Resource LoadAsResource(String fileName );

}
