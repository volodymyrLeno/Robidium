package com.robidium.demo.log.service;

import com.opencsv.CSVReader;
import com.robidium.demo.log.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class CSVLogService implements LogService {
    private final Path rootLocation;

    @Autowired
    public CSVLogService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String logName) {
        try {
            Path file = load(logName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + logName);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + logName, e);
        }
    }

    @Override
    public void store(MultipartFile log) {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(log.getOriginalFilename()));
        try {
            if (log.isEmpty()) {
                throw new RuntimeException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new RuntimeException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = log.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + filename, e);
        }
    }

    @Override
    public List<String> getLogAttributes(String logName) {
        List<String> attributes = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new FileReader(load(logName).toString()));
            String[] header = reader.readNext();
            if (header != null) {
                attributes.addAll(Arrays.asList(header));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return attributes;
    }
}
