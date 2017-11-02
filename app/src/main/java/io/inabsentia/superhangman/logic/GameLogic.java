package io.inabsentia.superhangman.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLogic {

    private final int MAXIMUM_LIVES = 7;
    private final int SINGLE_LETTER_SCORE = 1;
    private int roundScore = 0;

    private static GameLogic instance;

    private final Random random = new Random();
    private List<String> words;

    private String secretWord = "";
    private String hiddenWord = "";

    private int life = MAXIMUM_LIVES;

    private int score = 0;
    private int winCount = 0;
    private int lossCount = 0;
    private int rounds = 0;
    private double timeUsed = 0;

    private GameLogic() {
        words = new ArrayList<>();
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

    public String getUrl(String url) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
            sb.append(line).append("\n");
            line = br.readLine();
        }

        return sb.toString();
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    private String getRandomWord() throws Exception {
        if (words.size() <= 0) throw new Exception("No words found!");
        return words.get(random.nextInt(words.size()));
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

    public List<String> getWords() {
        return words;
    }

}