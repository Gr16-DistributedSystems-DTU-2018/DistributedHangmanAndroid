package io.inabsentia.superhangman.adapter

import android.content.Context
import android.widget.ArrayAdapter

import io.inabsentia.superhangman.R
import io.inabsentia.superhangman.data.dto.HighScoreDTO

class HighScoreAdapter(private val highScoreList: List<HighScoreDTO>, private val mContext: Context) : ArrayAdapter<HighScoreDTO>(mContext, R.layout.high_score_item, highScoreList)