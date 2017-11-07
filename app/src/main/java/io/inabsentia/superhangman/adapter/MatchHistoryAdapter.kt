package io.inabsentia.superhangman.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import io.inabsentia.superhangman.R
import io.inabsentia.superhangman.data.dto.MatchDTO

class MatchHistoryAdapter(private val matchList: List<MatchDTO>, private val mContext: Context) : ArrayAdapter<MatchDTO>(mContext, R.layout.match_history_item, matchList) {

    private var lastPosition = -1

    /*
     * ViewHolder that encapsulates the objects
     * that are used in the list view.
     */
    private class ViewHolder {
        internal var tvNameLabel: TextView? = null
        internal var tvName: TextView? = null
        internal var tvScoreLabel: TextView? = null
        internal var tvScore: TextView? = null
        internal var tvTimeLabel: TextView? = null
        internal var tvTime: TextView? = null
        internal var tvLastWordLabel: TextView? = null
        internal var tvLastWord: TextView? = null
        internal var ivStatus: ImageView? = null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var mConvertView = convertView

        val view: View
        val viewHolder: ViewHolder

        val matchDTO = matchList[position]

        if (mConvertView == null) {
            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(context)
            mConvertView = inflater.inflate(R.layout.match_history_item, parent, false)

            viewHolder.tvNameLabel = mConvertView.findViewById(R.id.match_user_name_label)
            viewHolder.tvName = mConvertView.findViewById(R.id.match_user_name)
            viewHolder.tvScoreLabel = mConvertView.findViewById(R.id.match_user_score_label)
            viewHolder.tvScore = mConvertView.findViewById(R.id.match_user_score)
            viewHolder.tvTimeLabel = mConvertView.findViewById(R.id.match_user_time_label)
            viewHolder.tvTime = mConvertView.findViewById(R.id.match_user_time)
            viewHolder.tvLastWordLabel = mConvertView.findViewById(R.id.match_last_word_label)
            viewHolder.tvLastWord = mConvertView.findViewById(R.id.match_last_word)
            viewHolder.ivStatus = mConvertView.findViewById(R.id.match_status_image)

            view = mConvertView
            mConvertView.tag = viewHolder
        } else {
            viewHolder = mConvertView.tag as ViewHolder
            view = mConvertView
        }

        val animation = AnimationUtils.loadAnimation(mContext, if (position > lastPosition) R.anim.up_from_bottom else R.anim.down_from_top)
        view.startAnimation(animation)
        lastPosition = position

        viewHolder.tvName!!.text = matchDTO.name
        viewHolder.tvScore!!.text = matchDTO.score.toString()
        viewHolder.tvTime!!.text = matchDTO.time.toString()
        viewHolder.tvLastWord!!.text = matchDTO.lastWord

        if (matchDTO.status)
            viewHolder.ivStatus!!.setImageResource(R.drawable.ic_happy_smile_icon)
        else
            viewHolder.ivStatus!!.setImageResource(R.drawable.ic_sad_smile_icon)

        return mConvertView!!
    }

}