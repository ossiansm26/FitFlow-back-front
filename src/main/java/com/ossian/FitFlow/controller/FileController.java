package com.ossian.FitFlow.controller;


import com.ossian.FitFlow.service.FileStorageService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.nio.file.Files;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("api/file")
@CrossOrigin(origins = "http://localhost:8080")
public class FileController {
    private final FileStorageService StorageService;
    private final HttpServletRequest request;

    @PostMapping("/upload")
    public Map<String,String> uploadFile(@RequestParam("file") MultipartFile file){
        String path = StorageService.storeFile(file);
        String host = request.getRequestURL().toString().replace(request.getRequestURI(),"");
        String url = ServletUriComponentsBuilder
                .fromHttpUrl(host)
                .path("/api/file/")
                .path(path)
                .toUriString();

        return Map.of("url" , url);
    }
    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<?> getFile(@PathVariable String fileName){
        try {
            Resource file = StorageService.LoadAsResource(fileName);
            String contentType = Files.probeContentType(file.getFile().toPath());
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .body(file);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
        }

    }




}