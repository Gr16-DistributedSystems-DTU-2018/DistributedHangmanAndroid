package io.inabsentia.superhangman.data.dao;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.inabsentia.superhangman.data.dto.HighScoreDTO;

public class HighScoreDAO implements IHighScoreDAO {

    private static IHighScoreDAO instance;

    private List<HighScoreDTO> highScoreList;

    private HighScoreDAO() {
        highScoreList = new ArrayList<>();
    }

    static {
        try {
            instance = new HighScoreDAO();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized IHighScoreDAO getInstance() {
        return instance;
    }

    @Override
    public void add(HighScoreDTO dto) throws DALException {
        highScoreList.add(dto);
    }

    @Override
    public HighScoreDTO get(int id) throws DALException {
        return null;
    }

    @Override
    public List<HighScoreDTO> getAll() throws DALException {
        return null;
    }

    @Override
    public void update(HighScoreDTO dto) throws DALException {

    }

    @Override
    public void remove(int id) throws DALException {

    }

    @Override
    public void removeAll() throws DALException {

    }

    @Override
    public void load(Context context) throws DALException {

    }

    @Override
    public void save(Context context) throws DALException {

    }

}