package io.inabsentia.superhangman.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.TextView
import io.inabsentia.superhangman.R
import io.inabsentia.superhangman.data.dto.HighScoreDTO

class HighScoreAdapter(private val highScoreList: List<HighScoreDTO>, private val mContext: Context) : ArrayAdapter<HighScoreDTO>(mContext, R.layout.high_score_item, highScoreList) {

    private var lastPosition = -1

    /*
     * ViewHolder that encapsulates the objects
     * that are used in the list view.
     */
    private class ViewHolder {
        internal var tvPos: TextView? = null
        internal var tvNameLabel: TextView? = null
        internal var tvName: TextView? = null
        internal var tvHighScoreLabel: TextView? = null
        internal var tvHighScore: TextView? = null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var mConvertView = convertView

        val view: View
        val viewHolder: ViewHolder

        val highScoreDTO = highScoreList[position]

        if (mConvertView == null) {
            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(context)
            mConvertView = inflater.inflate(R.layout.high_score_item, parent, false)

            viewHolder.tvPos = mConvertView.findViewById(R.id.position_view)
            viewHolder.tvNameLabel = mConvertView.findViewById(R.id.high_score_user_name_label)
            viewHolder.tvName = mConvertView.findViewById(R.id.high_score_user_name)
            viewHolder.tvHighScoreLabel = mConvertView.findViewById(R.id.high_score_user_name_label)
            viewHolder.tvHighScore = mConvertView.findViewById(R.id.high_score)

            view = mConvertView
            mConvertView.tag = viewHolder
        } else {
            viewHolder = mConvertView.tag as ViewHolder
            view = mConvertView
        }

        val animation = AnimationUtils.loadAnimation(mContext, if (position > lastPosition) R.anim.up_from_bottom else R.anim.down_from_top)
        view.startAnimation(animation)
        lastPosition = position

        viewHolder.tvPos!!.text = (position + 1).toString()
        viewHolder.tvName!!.text = highScoreDTO.name
        viewHolder.tvHighScore!!.text = highScoreDTO.highscore.toString()

        return mConvertView!!
    }

}