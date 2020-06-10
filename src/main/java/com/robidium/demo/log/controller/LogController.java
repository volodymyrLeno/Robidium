package com.robidium.demo.log.controller;

import com.robidium.demo.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class LogController {
    private final LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/logs/{logName:.+}")
    public ResponseEntity<Resource> serveLog(@PathVariable String logName) {
        Resource log = logService.loadAsResource(logName);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                String.format("attachment; filename=\"%s\"", log.getFilename())).body(log);
    }

    @PostMapping("/logs")
    public ResponseEntity<List<String>> handleLogUpload(@RequestParam("file") MultipartFile log) {
        logService.store(log);
        List<String> attributes = logService.getLogAttributes(log.getOriginalFilename());
        return new ResponseEntity<>(attributes, HttpStatus.OK);
    }
}
