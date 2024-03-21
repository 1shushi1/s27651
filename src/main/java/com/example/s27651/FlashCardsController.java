package com.example.s27651;

import org.springframework.stereotype.Controller;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

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
            System.out.println("4. Delete a word");
            System.out.println("5. Sorting");
            System.out.println("6. Modifying");
            System.out.println("7. Searching");
            System.out.println("8. Exit");
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
                    delete();
                    break;
                case 5:
                    sort();
                    break;
                case 6:
                    modify();
                    break;
                case 7:
                    search();
                    break;
                case 8:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid selection. Try again.");
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
    public void search() {
        System.out.print("Enter a keyword to search for: ");
        String keyword = s.nextLine().trim().toLowerCase();

        List<Entry> matchingEntries = entryRepository.getWordList()
                .stream()
                .filter(entry ->
                        entry.getEnglishWord().toLowerCase().contains(keyword) ||
                                entry.getPolishWord().toLowerCase().contains(keyword) ||
                                entry.getGermanWord().toLowerCase().contains(keyword)
                )
                .collect(Collectors.toList());

        if (!matchingEntries.isEmpty()) {
            System.out.println("Matching entries:");
            matchingEntries.forEach(System.out::println);
        } else {
            System.out.println("No matching entries found for the keyword: " + keyword);
        }
    }

    public void sort() {
        System.out.println("Select a language for sorting:");
        System.out.println("1. English");
        System.out.println("2. Polish");
        System.out.println("3. German");
        int languageChoice = s.nextInt();
        s.nextLine();

        System.out.println("Select sorting order:");
        System.out.println("1. Ascending");
        System.out.println("2. Descending");
        int sortOrderChoice = s.nextInt();
        s.nextLine();

        // Get the appropriate comparator based on language choice and sorting order
        Comparator<Entry> comparator = null;
        switch (languageChoice) {
            case 1:
                comparator = Comparator.comparing(entry -> entry.getEnglishWord().toLowerCase());
                break;
            case 2:
                comparator = Comparator.comparing(entry -> entry.getPolishWord().toLowerCase());
                break;
            case 3:
                comparator = Comparator.comparing(entry -> entry.getGermanWord().toLowerCase());
                break;
            default:
                System.out.println("Invalid language choice.");
                return;
        }

        // Reverse comparator for descending order
        if (sortOrderChoice == 2) {
            comparator = comparator.reversed();
        }

        // Sort the list of entries
        entryRepository.getWordList().sort(comparator);

        // Display sorted results
        System.out.println("Sorted list of words:");
        entryRepository.getWordList().forEach(e -> System.out.println(e));
    }

    public void delete(){
        System.out.println("Select a language in order to find an entry of words you would like to delete : ");
        System.out.println("1 - English, 2 - Polish, 3 - German");
        String word = "";
        int sel = s.nextInt();
        s.nextLine();
        switch (sel){
            case 1 :
                System.out.println("Now, enter a word you would like to delete");
                word = s.nextLine();
                deleteByEnglishWord(word);
                break;
            case 2 :
                System.out.println("Now, enter a word you would like to delete");
                word = s.nextLine();
                deleteByPolishWord(word);
                break;
            case 3 :
                System.out.println("Now, enter a word you would like to delete");
                word = s.nextLine();
                deleteByGeramnWord(word);
                break;
        }
    }
    public void deleteByEnglishWord(String word) {
        Iterator<Entry> iterator = entryRepository.getWordList().iterator();
        while (iterator.hasNext()) {
            Entry entry = iterator.next();
            if (entry.getEnglishWord().equalsIgnoreCase(word)) {
                iterator.remove();
                System.out.println("Entry with English word: " + word + " was successfully removed.");
                return;
            }
        }
        System.out.println("Entry with English word: " + word + " not found.");
    }
    public void deleteByPolishWord(String word) {
        Iterator<Entry> iterator = entryRepository.getWordList().iterator();
        while (iterator.hasNext()) {
            Entry entry = iterator.next();
            if (entry.getPolishWord().equalsIgnoreCase(word)) {
                iterator.remove();
                System.out.println("Entry with Polish word: " + word + " was successfully removed.");
                return;
            }
        }
        System.out.println("Entry with Polish word: " + word + " not found.");
    }
    public void deleteByGeramnWord(String word) {
        Iterator<Entry> iterator = entryRepository.getWordList().iterator();
        while (iterator.hasNext()) {
            Entry entry = iterator.next();
            if (entry.getGermanWord().equalsIgnoreCase(word)) {
                iterator.remove();
                System.out.println("Entry with German word: " + word + " was successfully removed.");
                return;
            }
        }
        System.out.println("Entry with German word: " + word + " not found.");
    }

    public void modify() {
        System.out.print("Enter the English word to modify: ");
        String englishWordToModify = s.nextLine().trim().toLowerCase();

        Optional<Entry> optionalEntry = entryRepository.getWordList().stream()
                .filter(entry -> entry.getEnglishWord().toLowerCase().equals(englishWordToModify))
                .findFirst();

        if (optionalEntry.isPresent()) {

            Entry entryToModify = optionalEntry.get();
            System.out.println("Current details of the word:");
            System.out.println("English: " + entryToModify.getEnglishWord());
            System.out.println("Polish: " + entryToModify.getPolishWord());
            System.out.println("German: " + entryToModify.getGermanWord());

            System.out.println("Enter new translations:");
            System.out.print("English: ");
            String newEnglishTranslation = s.nextLine();
            System.out.print("Polish: ");
            String newPolishTranslation = s.nextLine();
            System.out.print("German: ");
            String newGermanTranslation = s.nextLine();

            entryToModify.setEnglishWord(newEnglishTranslation);
            entryToModify.setPolishWord(newPolishTranslation);
            entryToModify.setGermanWord(newGermanTranslation);

            System.out.println("Word modified successfully.");
        } else {
            System.out.println("Word not found in the dictionary.");
        }
    }
}
