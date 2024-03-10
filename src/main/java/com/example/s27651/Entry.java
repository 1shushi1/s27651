package com.example.s27651;

import org.springframework.stereotype.Component;
@Component
public class Entry {
    private String polishWord;
    private String englishWord;
    private String germanWord;

    public Entry() {
    }

    public void setPolishWord(String polishWord) {
        this.polishWord = polishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public void setGermanWord(String germanWord) {
        this.germanWord = germanWord;
    }
    public String getEnglishWord(){
        return englishWord;
    }
    public String getPolishWord(){
        return polishWord;
    }
    public String getGermanWord(){
        return germanWord;
    }
    @Override
    public String toString(){
        return "English word : " + englishWord + ". Polish word : " + polishWord + ". German word : " + germanWord;
    }
}