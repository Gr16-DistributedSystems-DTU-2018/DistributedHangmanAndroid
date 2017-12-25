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

class PickWordAdapter(private val words: List<String>, private val mContext: Context) : ArrayAdapter<String>(mContext, R.layout.pick_word_item, words) {

    private var lastPosition = -1

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var mConvertView = convertView
        val view: View
        val viewHolder: ViewHolder

        val word = words[position]

        if (mConvertView == null) {
            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(context)
            mConvertView = inflater.inflate(R.layout.pick_word_item, parent, false)

            viewHolder.tvPos = mConvertView!!.findViewById(R.id.position_view)
            viewHolder.tvWord = mConvertView.findViewById(R.id.word_view)
            viewHolder.image = mConvertView.findViewById(R.id.image_view)

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
        viewHolder.tvWord!!.text = word

        return mConvertView
    }

    /*
     * ViewHolder that encapsulates the objects
     * that are used in the list view.
     */
    private class ViewHolder {
        internal var tvPos: TextView? = null
        internal var tvWord: TextView? = null
        internal var image: ImageView? = null
    }

}