package io.inabsentia.superhangman.helper

import android.content.Context
import io.inabsentia.superhangman.data.dao.MatchDAO
import io.inabsentia.superhangman.data.dto.MatchDTO
import io.inabsentia.superhangman.logic.GameLogic

class Utils private constructor() {

    val MUSIC_ENABLED = true
    val WORD_URL = "https://www.nytimes.com/"

    private val logic = GameLogic.instance
    private val highScoreDAO = MatchDAO.instance

    fun createMatchAndReset(context: Context) {
        val avgRoundTime: Double = if (logic!!.totalGames <= 0)
            logic.timeUsed
        else
            logic.timeUsed / logic.totalGames

        val highScoreDTO = MatchDTO("", logic.secretWord!!, logic.score, logic.winCount, avgRoundTime)

        highScoreDAO!!.add(highScoreDTO)
        highScoreDAO.save(context)

        logic.reset()
    }

    companion object {
        @get:Synchronized
        var instance: Utils? = null
            private set

        init {
            instance = Utils()
        }
    }

}