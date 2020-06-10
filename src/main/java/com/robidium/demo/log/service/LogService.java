package com.robidium.demo.log.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

public interface LogService {

    void init();

    Path load(String logName);

    Resource loadAsResource(String logName);

    void store(MultipartFile log);

    List<String> getLogAttributes(String logName);
}

