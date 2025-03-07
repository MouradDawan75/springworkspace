package fr.dawan.spring_rest_api.interceptors;

import fr.dawan.spring_rest_api.controllers.TestController;
import fr.dawan.spring_rest_api.dtos.LogDto;
import fr.dawan.spring_rest_api.exceptions.AuthentificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MyGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /*
    Possibilité de définir une méthode pour chaque type d'exception possible
     */
    private static Logger rootLogger = LoggerFactory.getLogger(MyGlobalExceptionHandler.class);

    @ExceptionHandler(AuthentificationException.class)
    public ResponseEntity<?> handleAuthException(Exception ex, WebRequest request){
        LogDto logDto = new LogDto();
        logDto.setCode(403);
        logDto.setMessage(ex.getMessage());
        logDto.setPath(((ServletWebRequest) request).getRequest().getRequestURI());
        logDto.setLogLevel(LogDto.LogLevel.ERROR);
        logDto.setLogType(LogDto.LogType.ACCESS);
        rootLogger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(logDto);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex, WebRequest request){
        LogDto logDto = new LogDto();
        logDto.setPath(((ServletWebRequest) request).getRequest().getRequestURI());
        logDto.setCode(500);
        logDto.setMessage(ex.getMessage());
        logDto.setLogLevel(LogDto.LogLevel.ERROR);
        logDto.setLogType(LogDto.LogType.EXCEPTION);
        rootLogger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(logDto);
    }

}
