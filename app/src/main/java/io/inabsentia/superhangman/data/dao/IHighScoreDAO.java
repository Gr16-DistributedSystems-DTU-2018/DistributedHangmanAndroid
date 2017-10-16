package io.inabsentia.superhangman.data.dao;

import android.content.Context;

import java.util.List;

import io.inabsentia.superhangman.data.dto.HighScoreDTO;

public interface IHighScoreDAO {
    void add(HighScoreDTO highScoreDTO) throws DALException;
    HighScoreDTO get(int id) throws DALException;
    List<HighScoreDTO> getAll() throws DALException;
    List<HighScoreDTO> getAll(int score) throws DALException;
    void update(HighScoreDTO highScoreDTO) throws DALException;
    void remove(int id) throws DALException;
    void removeAll() throws DALException;
    void load(Context context) throws DALException;
    void save(Context context) throws DALException;
    class DALException extends Exception {

        public DALException(String msg, Throwable e) {
            super(msg, e);
        }

        public DALException(String msg) {
            super(msg);
        }
    }

}