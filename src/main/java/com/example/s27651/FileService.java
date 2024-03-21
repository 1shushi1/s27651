package com.example.s27651;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
public class FileService {
    private final EntryRepository entryRepository;
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    @Value("${pl.edu.pja.tpo02.filename}")
    private String dictionaryFilename;

    public FileService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    @PostConstruct
    public void fileReader() {
        logger.info("Reading file: {}", dictionaryFilename);
        try (BufferedReader br = new BufferedReader(new FileReader(new File(dictionaryFilename)))) {
            String line;
            while ((line = br.readLine()) != null) {
                logger.debug("Read line: {}", line);
                String[] words = line.split(";");
                Entry entry = new Entry();
                entry.setEnglishWord(words[0]);
                entry.setGermanWord(words[1]);
                entry.setPolishWord(words[2]);
                entryRepository.addTranslation(entry);
            }
        } catch (IOException e) {
            logger.error("Error reading file: {}", e.getMessage());
        }
    }
}
