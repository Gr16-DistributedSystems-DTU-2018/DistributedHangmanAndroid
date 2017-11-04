package io.inabsentia.superhangman.data.dao

import android.content.Context

import io.inabsentia.superhangman.data.dto.MatchDTO

interface IMatchDAO {
    val all: List<MatchDTO>

    @Throws(DALException::class)
    fun add(dto: MatchDTO)

    @Throws(DALException::class)
    fun get(id: Int): MatchDTO

    @Throws(DALException::class)
    fun update(dto: MatchDTO)

    @Throws(DALException::class)
    fun remove(id: Int)

    @Throws(DALException::class)
    fun removeAll(context: Context)

    @Throws(DALException::class)
    fun load(context: Context)

    @Throws(DALException::class)
    fun save(context: Context)
}