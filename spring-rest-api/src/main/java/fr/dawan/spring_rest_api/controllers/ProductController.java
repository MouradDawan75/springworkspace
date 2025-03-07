package fr.dawan.spring_rest_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.dawan.spring_rest_api.apidocs.ProductApi;
import fr.dawan.spring_rest_api.dtos.CountDto;
import fr.dawan.spring_rest_api.dtos.ProductDto;
import fr.dawan.spring_rest_api.services.IProductService;
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
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/products")
//@CrossOrigin(value = "*")
public class ProductController implements ProductApi {

    @Autowired
    private IProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${storage.folder}")
    private String storageFolder;


    @GetMapping(value = {"/{page}/{size}/{description}", "/{page}/{size}"}, produces = "application/json")
    @Override
    public List<ProductDto> getAllBy(@PathVariable("page") int page, @PathVariable("size") int size,
                                     @PathVariable(value = "description", required = false) Optional<String> description) throws Exception{
        if(description.isPresent()){
            return productService.getAllBy(description.get(), page, size);
        }else{
            return productService.getAllBy("", page, size);
        }

    }

    @GetMapping(value = {"/count/{description}", "/count"}, produces = "application/json")
    @Override
    public CountDto countBy(@PathVariable(value = "description", required = false) Optional<String> description) throws Exception{
        if(description.isPresent()){
            return productService.countBy(description.get());
        }else{
            return productService.countBy("");
        }
    }

    @DeleteMapping(value = "/delete/{id}", produces = "text/plain")
    @Override
    public ResponseEntity<String> delete(@PathVariable("id") long id) throws Exception{
        ProductDto prod = productService.getById(id);
        if(prod == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with id = "+id+" not found.");
        }else{
            productService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Product with id = "+id+" deleted.");
        }
    }

    @GetMapping(value = "/find/{id}")
    @Override
    public ResponseEntity<Object> getById(@PathVariable("id") long id) throws Exception{
        ProductDto prod = productService.getById(id);
        if(prod == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with id = "+id+" not found.");
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(prod);
        }
    }

    @PostMapping(value = "/save", produces = "application/json", consumes = "multipart/form-data")
    @Override
    public ResponseEntity<ProductDto> addProduct(@RequestParam("productString") String productString, @RequestParam("file") MultipartFile file) throws Exception{

        //Gestion du param productString: conversion en Product
        ProductDto p = objectMapper.readValue(productString, ProductDto.class);

        //objectMapper.writeValueAsString(p); -> conversion d'un objet en str

        //Construction du chemin du fichier + personnalisation du fichier
        String filePath = "/"+p.getDescription()+"-"+file.getOriginalFilename();
        File f = new File(storageFolder+filePath);
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(file.getBytes());
        fos.close();

        p.setImagePath(filePath);

        ProductDto savedProduct = productService.saveOrUpdate(p);

        return ResponseEntity.status(HttpStatus.OK).body(savedProduct);
    }

    @GetMapping(value = "/image/{productId}", produces = "application/octet-stream")
    @Override
    public ResponseEntity<Resource> getProductImage(@PathVariable("productId") long id) throws Exception{

        ProductDto p = productService.getById(id);
        // Paths.get("."): renvoie la racine
        Path path = Paths.get(".").resolve(storageFolder+p.getImagePath());
        //Path path = Paths.get(storageFolder).resolve(p.getImagePath());
        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+resource.getFilename())
                .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(resource.getFile().toPath())) //Files.probeContentType: renvoie le type effectif de la ressource
                .body(resource);

    }

}
