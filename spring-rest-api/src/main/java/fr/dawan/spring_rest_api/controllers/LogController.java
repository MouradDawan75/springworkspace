package fr.dawan.spring_rest_api.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import fr.dawan.spring_rest_api.dtos.LogDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/logs")
public class LogController {
    /*
    Controller destiné aux admins
     */

    private static Logger rootLogger = LoggerFactory.getLogger(LogController.class);
    private static Logger debugLogger = LoggerFactory.getLogger("debugLogger");

    @Value("${storage.folder}")
    private String storageFolder;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(value = "/{fileName}", produces = "application/octet-stream")
    public ResponseEntity<Resource> getLogFile(@PathVariable("fileName") String fileName) throws Exception {
        File f = new File(storageFolder + "/" + fileName);

        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(Paths.get(f.getAbsolutePath())));
        /*
        Soit afficher le résultat dans une page web
        Soit afficher une boite de dialogue de téléchargement du fichier, en ajoutant un param header dans la réponse
        header: CONTENT-DISPOSITION, attachment;filename=app.log
         */
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);
        return ResponseEntity.ok()
                .headers(headers) // nécessaire pour le téléchargement
                .contentLength(f.length()) //optionnel
                .contentType(MediaType.APPLICATION_OCTET_STREAM) //optionnel
                .body(resource);
    }

    //Méthode pour insérer un log dans le fichiers des logs
    @PostMapping(consumes = "application/json", produces = "text/plain")
    public ResponseEntity<String> addLog(@RequestBody LogDto logDto) throws Exception {
        /*
        Conversion de logDto en String
        ObjectMapper (fait partie de la dépendance Jackson): permet la conversion d'un objet en String et vice versa
        ModelMapper: permet la conversion d'entity en DTO et vice versa
         */

        String logDtoString = objectMapper.writeValueAsString(logDto);
        switch (logDto.getLogLevel()) {
            case INFO:
                rootLogger.info(logDtoString);
                break;

            case WARNING:
                rootLogger.warn(logDtoString);
                break;

            case ERROR:
                rootLogger.error(logDtoString);
                break;

            case DEBUG:
                debugLogger.debug(logDtoString);
                break;
            default:
                break;

        }

        return ResponseEntity.status(HttpStatus.OK).body("Log inséré dans le fichier.....");
    }
}
