package com.ossian.FitFlow.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/api/file")
@CrossOrigin(origins = "http://localhost:8080")
public class FileUploadController {
    private final String uploadDir = "./uploads";

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Verificar si el archivo está vacío
            if (file.isEmpty()) {
                return new ResponseEntity<>("El archivo está vacío", HttpStatus.BAD_REQUEST);
            }

            // Obtener el nombre original del archivo
            String fileName = file.getOriginalFilename();

            // Crear el directorio si no existe
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs(); // Use mkdirs() to create parent directories if they don't exist
            }

            // Guardar el archivo en el directorio del servidor
            Path filePath = Paths.get(uploadDir + File.separator + fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Devolver la URL donde se guardó el archivo
            String fileUrl = "http://localhost:3001/uploads/" + fileName;
            return new ResponseEntity<>(fileUrl, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al cargar el archivo: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}