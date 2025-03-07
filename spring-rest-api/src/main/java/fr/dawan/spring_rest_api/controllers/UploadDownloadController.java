package fr.dawan.spring_rest_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.dawan.spring_rest_api.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class UploadDownloadController {

    @Value("${storage.folder}")
    private String storageFolder;

    @Autowired
    private ObjectMapper objectMapper;

    //Upload

    @PostMapping(value = "/upload-image/{productId}", produces = "application/json", consumes = "multipart/form-data")
    public ResponseEntity<Product> addProductImage(@PathVariable("productId") long id, @RequestParam("file")MultipartFile file) throws Exception{

        //Construction du chemin du fichier + personnalisation du fichier
        String filePath = "/"+id+"-"+file.getOriginalFilename();
        File f = new File(storageFolder+filePath);
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(file.getBytes());
        fos.close();

        //Récupérer le produit via son id depuis la BD
        Product p = new Product();
        p.setId(id);
        p.setImagePath(filePath);

        //Insérer le produit en BD

        return ResponseEntity.status(HttpStatus.OK).body(p);
    }

    @PostMapping(value = "/save", produces = "application/json", consumes = "multipart/form-data")
    public ResponseEntity<Product> addProduct(@RequestParam("productString") String productString, @RequestParam("file")MultipartFile file) throws Exception{

        //Gestion du param productString: conversion en Product
        Product p = objectMapper.readValue(productString, Product.class);

        //objectMapper.writeValueAsString(p); -> conversion d'un objet en str

        //Construction du chemin du fichier + personnalisation du fichier
        String filePath = "/"+p.getDescription()+"-"+file.getOriginalFilename();
        File f = new File(storageFolder+filePath);
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(file.getBytes());
        fos.close();

        //Récupérer le produit via son id depuis la BD

        p.setImagePath(filePath);

        //Insérer le produit en BD

        return ResponseEntity.status(HttpStatus.OK).body(p);
    }

    //Download

    @GetMapping(value = "/image/{productId}", produces = "application/octet-stream")
    public ResponseEntity<Resource> getProductImage(@PathVariable("productId") long id) throws Exception{

        //appelle de getById(id) pour récupérer le product depuis la bd
        Product p = new Product();
        p.setId(id);
        p.setImagePath("5-dell.png");
        Path path = Paths.get(storageFolder).resolve(p.getImagePath());
        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+resource.getFilename())
                .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(resource.getFile().toPath())) //Files.probeContentType: renvoie le type effectif de la ressource
                .body(resource);

    }
}
