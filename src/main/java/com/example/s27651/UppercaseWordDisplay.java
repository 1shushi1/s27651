package com.example.s27651;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("uppercase")

public class UppercaseWordDisplay implements WordDisplay{
    public void displayWord(Entry entry){
        System.out.println(entry.getEnglishWord().toUpperCase() + " " +
                entry.getPolishWord().toUpperCase() + " " +
                entry.getGermanWord().toUpperCase());
    }
}
