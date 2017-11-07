package io.inabsentia.superhangman.data.dao

import android.content.Context

import io.inabsentia.superhangman.data.dto.HighScoreDTO

interface IHighScoreDAO {
    val all: List<HighScoreDTO>?

    @Throws(DALException::class)
    fun add(dto: HighScoreDTO)

    @Throws(DALException::class)
    operator fun get(id: Int): HighScoreDTO?

    @Throws(DALException::class)
    fun getCurrentHighScore(name: String): Int

    @Throws(DALException::class)
    fun update(dto: HighScoreDTO)

    @Throws(DALException::class)
    fun remove(id: Int)

    @Throws(DALException::class)
    fun removeAll(context: Context)

    @Throws(DALException::class)
    fun load(context: Context)

    @Throws(DALException::class)
    fun save(context: Context)
}