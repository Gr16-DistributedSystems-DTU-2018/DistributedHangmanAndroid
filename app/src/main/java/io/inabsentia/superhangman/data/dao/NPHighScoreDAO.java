package io.inabsentia.superhangman.data.dao;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.inabsentia.superhangman.data.dto.HighScoreDTO;

public class NPHighScoreDAO implements IHighScoreDAO {

    private static NPHighScoreDAO instance;

    private List<HighScoreDTO> highScoreList;

    private NPHighScoreDAO() {
        highScoreList = new ArrayList<>();
    }

    static {
        try {
            instance = new NPHighScoreDAO();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized NPHighScoreDAO getInstance() {
        return instance;
    }

    @Override
    public void add(HighScoreDTO highScoreDTO) throws DALException {
        highScoreList.add(highScoreDTO);
        Collections.sort(highScoreList);
    }

    @Override
    public HighScoreDTO get(int id) throws DALException {
        if (highScoreList.size() <= 0) throw new DALException("No scores found!");
        for (HighScoreDTO dto : highScoreList)
            if (dto.getId() == id) return dto;
        throw new DALException("Id [" + id + "] does not exist!");
    }

    @Override
    public List<HighScoreDTO> getAll() throws DALException {
        return highScoreList;
    }

    @Override
    public List<HighScoreDTO> getAll(int score) throws DALException {
        List<HighScoreDTO> newHighScoreList = new ArrayList<>();

        for (HighScoreDTO dto : highScoreList) {
            if (dto.getScore() >= score) newHighScoreList.add(dto);
        }

        return newHighScoreList;
    }

    @Override
    public void update(HighScoreDTO highScoreDTO) throws DALException {
        if (highScoreList.size() <= 0) throw new DALException("No scores found!");

        int id = highScoreDTO.getId();

        for (HighScoreDTO dto : highScoreList) {
            if (dto.getId() == id) highScoreList.remove(dto);
        }

        highScoreList.add(highScoreDTO);
    }

    @Override
    public void remove(int id) throws DALException {
        for (HighScoreDTO dto : highScoreList) {
            if (dto.getId() == id) highScoreList.remove(dto);
        }
    }

    @Override
    public void removeAll() throws DALException {
        highScoreList = new ArrayList<>();
    }

    @Override
    public void load(Context context) throws DALException {

    }

    @Override
    public void save(Context context) throws DALException {

    }

}