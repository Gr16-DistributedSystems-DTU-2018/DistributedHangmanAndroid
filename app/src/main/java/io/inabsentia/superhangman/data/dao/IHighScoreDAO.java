package io.inabsentia.superhangman.data.dao;

import android.content.Context;

import java.util.List;

import io.inabsentia.superhangman.data.dto.HighScoreDTO;

public interface IHighScoreDAO {
    void add(HighScoreDTO dto) throws DALException;

    HighScoreDTO get(int id) throws DALException;

    List<HighScoreDTO> getAll() throws DALException;

    void update(HighScoreDTO dto) throws DALException;

    void remove(int id) throws DALException;

    void removeAll() throws DALException;

    void load(Context context) throws DALException;

    void save(Context context) throws DALException;
}