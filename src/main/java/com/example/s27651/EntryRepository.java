package com.example.s27651;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class EntryRepository {
    private List<Entry> wordList = new ArrayList<>();

    public void addTranslation(Entry entry) {
        wordList.add(entry);
    }
    public List<Entry> getWordList(){
        return wordList;
    }
}
