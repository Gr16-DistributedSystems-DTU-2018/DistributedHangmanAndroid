package io.inabsentia.superhangman.data.dao;

import java.util.ArrayList;
import java.util.List;

import io.inabsentia.superhangman.data.dto.ScoreDTO;

public class ScoreDAO implements IScoreDAO {

    private static ScoreDAO instance;

    private List<ScoreDTO> scoreList;

    private ScoreDAO() {
        scoreList = new ArrayList<>();
    }

    static {
        try {
            instance = new ScoreDAO();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized ScoreDAO getInstance() {
        return instance;
    }

    @Override
    public void addScore(ScoreDTO scoreDTO) {
        scoreList.add(scoreDTO);
    }

    @Override
    public List<ScoreDTO> getScores() {
        return scoreList;
    }

}