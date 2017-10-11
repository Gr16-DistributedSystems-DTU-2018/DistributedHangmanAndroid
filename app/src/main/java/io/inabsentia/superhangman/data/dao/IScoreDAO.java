package io.inabsentia.superhangman.data.dao;

import java.util.List;

import io.inabsentia.superhangman.data.dto.ScoreDTO;

public interface IScoreDAO {
    void addScore(ScoreDTO scoreDTO);
    List<ScoreDTO> getScores();
}