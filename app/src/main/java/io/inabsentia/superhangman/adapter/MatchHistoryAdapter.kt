package io.inabsentia.superhangman.adapter

import android.content.Context
import android.widget.ArrayAdapter

import io.inabsentia.superhangman.R
import io.inabsentia.superhangman.data.dto.MatchDTO

class MatchHistoryAdapter(private val matchList: List<MatchDTO>, private val mContext: Context) : ArrayAdapter<MatchDTO>(mContext, R.layout.match_history_item, matchList)