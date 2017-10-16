package io.inabsentia.superhangman.logic;

import java.util.Random;

public class GameLogic {

    private final int MAXIMUM_LIVES = 6;
    private final int SINGLE_LETTER_SCORE = 1;
    private int roundScore = 0;

    private static GameLogic instance;

    private final Random random = new Random();

    private final String[] words = {"adorable", "liberation", "brawler", "cradle", "badmouth", "damnation", "hearts", "galactic", "astronaut", "android", "cs", "programming", "unix", "linux", "linus", "hearts", "git", "github", "gitlab", "svn", "wire", "blindfold", "noise", "biological", "ear", "man", "woman", "female", "boy", "girl", "dirty", "blur", "bent", "tesla", "elon", "musk", "dark", "light", "horses", "shag", "dozen", "cursed"};

    private String secretWord = "";
    private String hiddenWord = "";

    private int life = MAXIMUM_LIVES;

    private int score = 0;
    private int winCount = 0;
    private int lossCount = 0;
    private int rounds = 1;
    private double timeUsed = 0;

    private GameLogic() {

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
        roundScore = secretWord.length();
        life = MAXIMUM_LIVES;
        rounds = 1;
    }

    public void reset() throws Exception {
        init();
        score = 0;
        winCount = 0;
        lossCount = 0;
        timeUsed = 0;
    }

    private String createHiddenWord() throws Exception {
        if (secretWord == null) throw new Exception("secretWord not initialized");

        for (int i = 0; i < secretWord.length(); i++)
            hiddenWord += "●";

        return hiddenWord;
    }

    public boolean guess(char letter) {
        rounds++;

        if (secretWord == null) return false;

        if (secretWord.contains(Character.toString(letter))) {
            removeLetter(letter);
            score += SINGLE_LETTER_SCORE;
            return true;
        } else {
            life--;
            decrementScore();
            return false;
        }
    }

    public void giveHint() {
        for (int i = 0; i < hiddenWord.length(); i++) {
            if (hiddenWord.charAt(i) == '●') {
                char[] charArray = hiddenWord.toCharArray();
                charArray[i] = secretWord.charAt(i);
                hiddenWord = new String(charArray);
                decrementScore();
                break;
            }
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

    private void decrementScore() {
        if (score > 0) score--;
    }

    public boolean isWon() {
        if (secretWord == null || hiddenWord == null)
            return false;

        for (int i = 0; i < secretWord.length(); i++)
            if (secretWord.charAt(i) != hiddenWord.charAt(i)) return false;

        score += roundScore;
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

    public int getLife() {
        return life;
    }

    public int getScore() {
        return score;
    }

    public int getWinCount() {
        return winCount;
    }

    public int getLossCount() {
        return lossCount;
    }

    public int getRounds() {
        return rounds;
    }

    public double getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(double timeUsed) {
        this.timeUsed = timeUsed;
    }

    public int getTotalGames() {
        return winCount + lossCount;
    }

}