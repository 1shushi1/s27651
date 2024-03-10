package com.example.s27651;

import org.springframework.stereotype.Controller;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Scanner;
@Controller
public class FlashCardsController {
    private final FileService fileService;
    private final EntryRepository entryRepository;
    private Scanner s = new Scanner(System.in);
    public FlashCardsController(FileService fileService, EntryRepository entryRepository){
        this.fileService = fileService;
        this.entryRepository = entryRepository;
    }
    public void start(){
        fileService.fileReader();
        displayMenu();
    }
    public void displayFlashCards(){
        entryRepository.getWordList().forEach(e -> System.out.println(e));
    }
    private void displayMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("========== Dictionary Menu ==========");
            System.out.println("1. Add a new word to the dictionary");
            System.out.println("2. Display all words from the dictionary");
            System.out.println("3. Take a test");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = s.nextInt();
            s.nextLine();

            switch (choice) {
                case 1:
                    addWord();
                    break;
                case 2:
                    displayFlashCards();
                    break;
                case 3:
                    interactiveTest();
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        System.out.println("Exiting the program. Goodbye!");
    }
    public void addWord(){
        System.out.println("Enter an English word : ");
        String englishWord = s.nextLine();
        System.out.println("Now enter a Polish translation to your English word : ");
        String polishWord = s.nextLine();
        System.out.println("And a German translation : ");
        String germanWord = s.nextLine();

        Entry entry = new Entry();
        entry.setEnglishWord(englishWord);
        entry.setPolishWord(polishWord);
        entry.setGermanWord(germanWord);

        entryRepository.addTranslation(entry);

        System.out.println("Your word was successfully added!");
    }
    public void interactiveTest(){
        Collections.shuffle(entryRepository.getWordList());
        int score = 0;
        for (Entry flashCard : entryRepository.getWordList()) {
            System.out.println("Translate an English word : " + flashCard.getEnglishWord());
            String translation = s.nextLine();
            if (translation.equalsIgnoreCase(flashCard.getPolishWord()) ||
                translation.equalsIgnoreCase(flashCard.getGermanWord())){
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Incorrect. The correct translations are: " +
                        flashCard.getPolishWord() + " (Polish) and " +
                        flashCard.getGermanWord() + " (German)");
            }
        }
        System.out.println("Test completed! Your score: " + score + "/" + entryRepository.getWordList().size());
    }
}
