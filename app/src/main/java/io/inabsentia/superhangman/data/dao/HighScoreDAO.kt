package io.inabsentia.superhangman.data.dao

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.inabsentia.superhangman.data.dto.HighScoreDTO
import java.util.*

class HighScoreDAO private constructor() : IHighScoreDAO {

    private var highScoreList: MutableList<HighScoreDTO>? = null

    override val all: List<HighScoreDTO>?
        @Throws(DALException::class)
        get() {
            if (highScoreList == null) throw DALException("HighScoreList is null!")
            return highScoreList as MutableList<HighScoreDTO>
        }

    init {
        highScoreList = ArrayList()
    }

    @Throws(DALException::class)
    override fun add(dto: HighScoreDTO) {
        highScoreList!!.add(dto)
        Collections.sort(highScoreList)
    }

    @Throws(DALException::class)
    override fun get(id: Int): HighScoreDTO? {
        highScoreList!!.filter { it.id == id }.forEach { return it }
        throw DALException("Id [$id] not found!")
    }

    @Throws(DALException::class)
    override fun getCurrentHighScore(name: String): Int {
        for (i in 0 until highScoreList!!.size - 1) {
            val dto = highScoreList!![i]
            return if (dto.name != name) {
                continue
            } else {
                dto.highscore
            }
        }
        throw DALException("No HighScore found!")
    }

    @Throws(DALException::class)
    override fun update(dto: HighScoreDTO) {
        val existingDTO = get(dto.id)
        highScoreList!!.remove(existingDTO)
        highScoreList!!.add(dto)
        Collections.sort(highScoreList)
    }

    @Throws(DALException::class)
    override fun remove(id: Int) {
        val highScoreDTO = get(id)
        highScoreList!!.remove(highScoreDTO)
        Collections.sort(highScoreList)
    }

    @Throws(DALException::class)
    override fun removeAll(context: Context) {
        highScoreList = ArrayList()
        save(context)
    }

    @Throws(DALException::class)
    override fun load(context: Context) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val gson = Gson()
        val json = sharedPrefs.getString("high_scores", null)
        val type = object : TypeToken<ArrayList<HighScoreDTO>>() {}.type

        highScoreList = gson.fromJson<List<HighScoreDTO>>(json, type) as MutableList<HighScoreDTO>?

        if (highScoreList == null) {
            highScoreList = ArrayList()
            save(context)
        }
    }

    @Throws(DALException::class)
    override fun save(context: Context) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPrefs.edit()
        val gson = Gson()
        val json = gson.toJson(highScoreList)
        editor.putString("high_scores", json)
        editor.apply()
    }

    companion object {
        @get:Synchronized
        var instance: IHighScoreDAO? = null
            private set

        init {
            instance = HighScoreDAO()
        }
    }

}