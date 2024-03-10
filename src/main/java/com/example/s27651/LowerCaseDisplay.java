package com.example.s27651;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("lowercase")
public class LowerCaseDisplay implements WordDisplay{
    @Override
    public void displayWord(Entry entry){
        System.out.println(entry.getEnglishWord().toLowerCase() + " " +
                entry.getPolishWord().toLowerCase() + " " +
                entry.getGermanWord().toLowerCase());
    }
}
