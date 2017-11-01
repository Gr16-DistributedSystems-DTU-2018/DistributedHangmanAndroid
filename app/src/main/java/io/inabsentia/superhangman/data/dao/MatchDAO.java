package io.inabsentia.superhangman.data.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.inabsentia.superhangman.data.dto.MatchDTO;

import static android.content.ContentValues.TAG;

public class MatchDAO implements IMatchDAO {

    private static MatchDAO instance = new MatchDAO();

    private List<MatchDTO> matchList;

    private MatchDAO() {
        matchList = new ArrayList<>();
    }

    static {
        try {
            instance = new MatchDAO();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized MatchDAO getInstance() {
        return instance;
    }

    @Override
    public void add(MatchDTO dto) throws DALException {
        matchList.add(dto);
    }

    @Override
    public MatchDTO get(int id) throws DALException {
        for (MatchDTO dto : matchList)
            if (dto.getId() == id) return dto;

        throw new DALException("Id [" + id + "] not found!");
    }

    @Override
    public List<MatchDTO> getAll() throws DALException {
        if (matchList == null) throw new DALException("MatchList is null!");
        return matchList;
    }

    @Override
    public void update(MatchDTO dto) throws DALException {
        MatchDTO existingDTO = get(dto.getId());
        matchList.remove(existingDTO);
        matchList.add(dto);
    }

    @Override
    public void remove(int id) throws DALException {
        MatchDTO highScoreDTO = get(id);
        matchList.remove(highScoreDTO);
    }

    @Override
    public void removeAll() throws DALException {
        matchList = new ArrayList<>();
    }

    @Override
    public void load(Context context) throws DALException {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPrefs.getString(TAG, null);
        Type type = new TypeToken<ArrayList<MatchDTO>>() {
        }.getType();
        matchList = gson.fromJson(json, type);

        if (matchList == null) {
            matchList = new ArrayList<>();
            save(context);
        }
    }

    @Override
    public void save(Context context) throws DALException {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(matchList);
        editor.putString(TAG, json);
        editor.apply();
    }

}