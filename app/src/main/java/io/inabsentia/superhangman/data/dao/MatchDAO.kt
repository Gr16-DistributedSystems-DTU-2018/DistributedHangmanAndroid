package io.inabsentia.superhangman.data.dao

import android.content.ContentValues.TAG
import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.inabsentia.superhangman.data.dto.MatchDTO
import java.util.*

class MatchDAO private constructor() : IMatchDAO {

    private var matchList: MutableList<MatchDTO>? = null

    override val all: List<MatchDTO>
        @Throws(DALException::class)
        get() {
            if (matchList == null) throw DALException("MatchList is null!")
            return matchList as MutableList<MatchDTO>
        }

    init {
        matchList = ArrayList()
    }

    @Throws(DALException::class)
    override fun add(dto: MatchDTO) {
        matchList!!.add(dto)
    }

    @Throws(DALException::class)
    override fun get(id: Int): MatchDTO {
        matchList!!.filter { it.id == id }.forEach { return it }
        throw DALException("Id [$id] not found!")
    }

    @Throws(DALException::class)
    override fun update(dto: MatchDTO) {
        val existingDTO = get(dto.id)
        matchList!!.remove(existingDTO)
        matchList!!.add(dto)
    }

    @Throws(DALException::class)
    override fun remove(id: Int) {
        val highScoreDTO = get(id)
        matchList!!.remove(highScoreDTO)
    }

    @Throws(DALException::class)
    override fun removeAll() {
        matchList = ArrayList()
    }

    @Throws(DALException::class)
    override fun load(context: Context) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val gson = Gson()
        val json = sharedPrefs.getString(TAG, null)
        val type = object : TypeToken<ArrayList<MatchDTO>>() {}.type

        matchList = gson.fromJson<List<MatchDTO>>(json, type) as MutableList<MatchDTO>?

        if (matchList == null) {
            matchList = ArrayList()
            save(context)
        }
    }

    @Throws(DALException::class)
    override fun save(context: Context) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPrefs.edit()
        val gson = Gson()
        val json = gson.toJson(matchList)
        editor.putString(TAG, json)
        editor.apply()
    }

    companion object {
        @get:Synchronized
        var instance: IMatchDAO? = null
            private set

        init {
            instance = MatchDAO()
        }
    }

}