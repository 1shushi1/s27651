package com.example.s27651;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
public class FileService {
    private final EntryRepository entryRepository;

    @Value("${pl.edu.pja.tpo02.filename}")
    private String dictionaryFilename;

    public FileService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    public void fileReader() {
        try (BufferedReader br = new BufferedReader(new FileReader(new File(dictionaryFilename)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(" ");
                Entry entry = new Entry();
                entry.setEnglishWord(words[0]);
                entry.setPolishWord(words[1]);
                entry.setGermanWord(words[2]);
                entryRepository.addTranslation(entry);
            }
        } catch (IOException e) {
        }
    }
}