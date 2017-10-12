package io.inabsentia.superhangman.data.dao;

import java.util.ArrayList;
import java.util.List;

import io.inabsentia.superhangman.data.dto.HighScoreDTO;

public class HighScoreDAO implements IHighScoreDAO {

    private static HighScoreDAO instance;

    private List<HighScoreDTO> scoreList;

    private HighScoreDAO() {
        scoreList = new ArrayList<>();
    }

    static {
        try {
            instance = new HighScoreDAO();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized HighScoreDAO getInstance() {
        return instance;
    }

    @Override
    public void addScore(HighScoreDTO highScoreDTO) {
        scoreList.add(highScoreDTO);
    }

    @Override
    public List<HighScoreDTO> getScores() {
        return scoreList;
    }

}