package io.inabsentia.superhangman.data.dao

import android.content.Context
import io.inabsentia.superhangman.data.dto.HighScoreDTO
import java.util.*

class HighScoreDAO private constructor() : IHighScoreDAO {

    private val highScoreList: MutableList<HighScoreDTO>

    override val all: List<HighScoreDTO>?
        @Throws(DALException::class)
        get() = null

    init {
        highScoreList = ArrayList()
    }

    @Throws(DALException::class)
    override fun add(dto: HighScoreDTO) {
        highScoreList.add(dto)
    }

    @Throws(DALException::class)
    override fun get(id: Int): HighScoreDTO? {
        return null
    }

    @Throws(DALException::class)
    override fun update(dto: HighScoreDTO) {

    }

    @Throws(DALException::class)
    override fun remove(id: Int) {

    }

    @Throws(DALException::class)
    override fun removeAll() {

    }

    @Throws(DALException::class)
    override fun load(context: Context) {

    }

    @Throws(DALException::class)
    override fun save(context: Context) {

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