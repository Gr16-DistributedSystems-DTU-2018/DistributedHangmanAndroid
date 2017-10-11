package io.inabsentia.superhangman.logic;

import java.util.ArrayList;
import java.util.Random;

public class GameLogic {

    private static GameLogic instance;

    private final Random random = new Random();

    private final String[] words = {"adorable", "liberation", "brawler", "cradle", "badmouth", "damnation", "hearts", "galactic", "astronaut", "android", "cs", "programming", "unix", "linux", "linus", "hearts", "git", "github", "gitlab", "svn", "wire", "blindfold", "noise", "biological", "ear", "man", "woman", "female", "boy", "girl", "dirty", "blur", "bent", "tesla", "elon", "musk", "dark", "light", "horses", "shag", "dozen", "cursed"};

    private ArrayList<Character> usedLettersList;

    private String secretWord = "";
    private String hiddenWord = "";

    private final int MAXIMUM_LIVES = 6;
    private int life = MAXIMUM_LIVES;

    private int rightGuessCount = 0;
    private int wrongGuessCount = 0;

    private int winCount = 0;
    private int lossCount = 0;

    private GameLogic() {
        usedLettersList = new ArrayList<>();
    }

    static {
        try {
            instance = new GameLogic();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized GameLogic getInstance() {
        return instance;
    }

    public void init() throws Exception {
        secretWord = "";
        hiddenWord = "";
        secretWord = getRandomWord().toLowerCase();
        hiddenWord = createHiddenWord().toLowerCase();
        life = MAXIMUM_LIVES;
        rightGuessCount = 0;
        wrongGuessCount = 0;
        usedLettersList.clear();
    }

    private String createHiddenWord() throws Exception {
        if (secretWord == null) throw new Exception("secretWord not initialized");
        for (int i = 0; i < secretWord.length(); i++) hiddenWord += "●";
        return hiddenWord;
    }

    public boolean guess(char letter) {
        useLetter(letter);

        if (secretWord == null) return false;

        if (secretWord.contains(Character.toString(letter))) {
            removeLetter(letter);
            rightGuessCount++;
            return true;
        } else {
            wrongGuessCount++;
            life--;
            return false;
        }
    }

    private void removeLetter(char letter) {
        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) == letter) {
                char[] charArray = hiddenWord.toCharArray();
                charArray[i] = letter;
                hiddenWord = new String(charArray);
            }
        }
    }

    private void useLetter(char letter) {
        if (usedLettersList.size() == 0) {
            usedLettersList.add(letter);
            return;
        }

        for (int i = 0; i < usedLettersList.size(); i++) {
            if (usedLettersList.get(i) == letter) return;
        }
        usedLettersList.add(letter);
    }

    public boolean isWon() {
        if (secretWord == null || hiddenWord == null) return false;

        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) != hiddenWord.charAt(i)) return false;
        }

        winCount++;
        return true;
    }

    public boolean isLost() {
        if (life == 0) {
            lossCount++;
            return true;
        }
        return false;
    }

    private String getRandomWord() {
        return words[random.nextInt(words.length)];
    }

    public String getHiddenWord() {
        return hiddenWord;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public String getUsedLetters() {
        String usedLettersString = "";
        for (int i = 0; i < usedLettersList.size(); i++) {
            if (i + 1 < usedLettersList.size()) {
                usedLettersString += usedLettersList.get(i) + ", ";
            } else {
                usedLettersString += usedLettersList.get(i);
            }
        }
        return usedLettersString;
    }

    public int getLife() {
        return life;
    }

    public int getWinCount() {
        return winCount;
    }

    public int getLossCount() {
        return lossCount;
    }

    public int getRightGuessCount() {
        return rightGuessCount;
    }

    public int getWrongGuessCount() {
        return wrongGuessCount;
    }

    public int getTotalGuessCount() {
        return wrongGuessCount + rightGuessCount;
    }

}