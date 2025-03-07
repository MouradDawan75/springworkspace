package fr.dawan.spring_rest_api.dtos;

import java.time.LocalDateTime;

public class LogDto {

    private int code;
    private String message;
    private LocalDateTime timesTamp;
    private String path;

    public enum LogLevel{
        DEBUG,INFO,WARNING,ERROR
    }

    private LogLevel logLevel;

    public enum LogType{
        ACCESS, CLIENT_APP,EXCEPTION
    }

    private LogType logType;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public LocalDateTime getTimesTamp() {
        return timesTamp;
    }

    public void setTimesTamp(LocalDateTime timesTamp) {
        this.timesTamp = timesTamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }
}
