package io.inabsentia.superhangman.logic

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class GameLogic private constructor() {

    private val MAXIMUM_LIVES = 7
    private val SINGLE_LETTER_SCORE = 1
    private var roundScore = 0

    private val random = Random()
    var words: ArrayList<String> = ArrayList()

    var secretWord: String? = ""
        private set
    var hiddenWord: String? = ""
        private set

    var life = MAXIMUM_LIVES
        private set

    var latestGameStatus = false
        private set
    private var games = 0
    var score = 0
        private set
    var rounds = 0
        private set
    var timeUsed = 0.0

    val isWon: Boolean
        get() {
            if (secretWord == null || hiddenWord == null) return false

            (0 until secretWord!!.length).filter { secretWord!![it] != hiddenWord!![it] }.forEach { return false }

            games++
            score += roundScore
            latestGameStatus = true
            return true
        }

    val isLost: Boolean
        get() {
            if (life == 0) {
                games++
                latestGameStatus = false
                return true
            }
            return false
        }

    private val randomWord: String
        @Throws(Exception::class)
        get() {
            if (words.isEmpty()) throw Exception("No words found!")
            return words[random.nextInt(words.size)]
        }

    init {
        words.add("no")
        words.add("internet")
        words.add("connection")
        words.add("on")
        words.add("your")
        words.add("device")
    }

    @Throws(Exception::class)
    fun init() {
        secretWord = ""
        hiddenWord = ""
        secretWord = randomWord.toLowerCase()
        hiddenWord = createHiddenWord()!!.toLowerCase()
        roundScore = secretWord!!.length
        life = MAXIMUM_LIVES
        rounds = 0
        timeUsed = 0.0
        latestGameStatus = false
    }

    @Throws(Exception::class)
    fun init(newSecretWord: String) {
        secretWord = ""
        hiddenWord = ""
        secretWord = newSecretWord
        hiddenWord = createHiddenWord()!!.toLowerCase()
        roundScore = secretWord!!.length
        life = MAXIMUM_LIVES
        rounds = 0
        timeUsed = 0.0
        latestGameStatus = false
    }

    @Throws(Exception::class)
    fun reset() {
        init()
        score = 0
        games = 0
    }

    @Throws(Exception::class)
    private fun createHiddenWord(): String? {
        if (secretWord == null) throw Exception("secretWord not initialized")

        for (i in 0 until secretWord!!.length)
            hiddenWord += "●"

        return hiddenWord
    }

    fun guess(letter: Char): Boolean {
        rounds++

        if (secretWord == null) return false

        return if (secretWord!!.contains(Character.toString(letter))) {
            removeLetter(letter)
            score += SINGLE_LETTER_SCORE
            true
        } else {
            life--
            decrementScore()
            false
        }
    }

    fun giveHint() {
        rounds++
        for (i in 0 until hiddenWord!!.length) {
            if (hiddenWord!![i] == '●') {
                val charArray = hiddenWord!!.toCharArray()
                charArray[i] = secretWord!![i]
                hiddenWord = String(charArray)
                decrementScore()
                break
            }
        }
    }

    private fun removeLetter(letter: Char) {
        for (i in 0 until secretWord!!.length) {
            if (secretWord!![i] == letter) {
                val charArray = hiddenWord!!.toCharArray()
                charArray[i] = letter
                hiddenWord = String(charArray)
            }
        }
    }

    private fun decrementScore() {
        if (score > 0) score--
    }

    @Throws(IOException::class)
    fun getData(url: String): String {
        val br = BufferedReader(InputStreamReader(URL(url).openStream()))
        val sb = StringBuilder()
        var line: String? = br.readLine()

        while (line != null) {
            sb.append(line).append("\n")
            line = br.readLine()
        }

        return sb.toString()
    }

    companion object {
        @get:Synchronized
        var instance: GameLogic? = null
            private set

        init {
            instance = GameLogic()
        }
    }

}