package io.inabsentia.superhangman.data.dao;

import java.util.List;

import io.inabsentia.superhangman.data.dto.HighScoreDTO;

public interface IHighScoreDAO {
    void addScore(HighScoreDTO highScoreDTO);
    List<HighScoreDTO> getScores();
}