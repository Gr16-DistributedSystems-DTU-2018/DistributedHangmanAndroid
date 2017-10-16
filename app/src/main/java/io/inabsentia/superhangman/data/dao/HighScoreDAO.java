package io.inabsentia.superhangman.data.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.inabsentia.superhangman.data.dto.HighScoreDTO;

import static android.content.ContentValues.TAG;

public class HighScoreDAO implements IHighScoreDAO {

    private static HighScoreDAO instance = new HighScoreDAO();

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

    public static synchronized HighScoreDAO getInstance() {
        return instance;
    }

    @Override
    public void add(HighScoreDTO highScoreDTO) throws DALException {
        highScoreList.add(highScoreDTO);
        Collections.sort(highScoreList);
    }

    @Override
    public HighScoreDTO get(int id) throws DALException {
        for (HighScoreDTO dto : highScoreList)
            if (dto.getId() == id) return dto;

        throw new DALException("Id [" + id + "] not found!");
    }

    @Override
    public List<HighScoreDTO> getAll() throws DALException {
        return highScoreList;
    }

    @Override
    public List<HighScoreDTO> getAll(int score) throws DALException {
        List<HighScoreDTO> newHighScoreList = new ArrayList<>();

        for (HighScoreDTO dto : highScoreList)
            if (dto.getScore() >= score) newHighScoreList.add(dto);

        return newHighScoreList;
    }

    @Override
    public void update(HighScoreDTO highScoreDTO) throws DALException {
        HighScoreDTO existingDTO = get(highScoreDTO.getId());
        highScoreList.remove(existingDTO);
        highScoreList.add(highScoreDTO);
        Collections.sort(highScoreList);
    }

    @Override
    public void remove(int id) throws DALException {
        HighScoreDTO highScoreDTO = get(id);
        highScoreList.remove(highScoreDTO);
        Collections.sort(highScoreList);
    }

    @Override
    public void removeAll() throws DALException {
        highScoreList = new ArrayList<>();
    }

    @Override
    public void load(Context context) throws DALException {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPrefs.getString(TAG, null);
        Type type = new TypeToken<ArrayList<HighScoreDTO>>() {}.getType();
        highScoreList = gson.fromJson(json, type);

        if (highScoreList == null) {
            highScoreList = new ArrayList<>();
            save(context);
        }
    }

    @Override
    public void save(Context context) throws DALException {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(highScoreList);
        editor.putString(TAG, json);
        editor.apply();
    }

}