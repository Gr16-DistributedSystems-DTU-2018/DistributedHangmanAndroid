package io.inabsentia.superhangman.data.dao;

import android.content.Context;

import java.util.List;

import io.inabsentia.superhangman.data.dto.MatchDTO;

public interface IMatchDAO {
    void add(MatchDTO dto) throws DALException;
    MatchDTO get(int id) throws DALException;
    List<MatchDTO> getAll() throws DALException;
    void update(MatchDTO dto) throws DALException;
    void remove(int id) throws DALException;
    void removeAll() throws DALException;
    void load(Context context) throws DALException;
    void save(Context context) throws DALException;
}