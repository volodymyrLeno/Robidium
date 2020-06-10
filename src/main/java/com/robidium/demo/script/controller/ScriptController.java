package com.robidium.demo.script.controller;

import com.robidium.demo.main.RoutineIdentification.data.Pattern;
import com.robidium.demo.pattern.service.PatternService;
import com.robidium.demo.script.service.ScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ScriptController {
    private final PatternService patternService;
    private final ScriptService scriptService;

    @Autowired
    public ScriptController(PatternService patternService, ScriptService scriptService) {
        this.patternService = patternService;
        this.scriptService = scriptService;
    }

    @PostMapping("/scripts")
    public ResponseEntity<Resource> generateScript(@RequestParam String patternId) throws IOException {
        Pattern pattern = patternService.getById(patternId);
        scriptService.generateScript(pattern);
        File file = new File("scripts/script_test.xaml");
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
