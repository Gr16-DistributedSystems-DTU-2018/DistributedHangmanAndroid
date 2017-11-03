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

    /*
     * ViewHolder that encapsulates the objects
     * that are used in the list view.
     */
    private class ViewHolder {
        internal var tvPos: TextView? = null
        internal var tvWord: TextView? = null
        internal var image: ImageView? = null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        val view: View
        val viewHolder: ViewHolder

        val word = words[position]

        if (convertView == null) {
            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(context)
            convertView = inflater.inflate(R.layout.pick_word_item, parent, false)

            viewHolder.tvPos = convertView!!.findViewById(R.id.position_view)
            viewHolder.tvWord = convertView.findViewById(R.id.word_view)
            viewHolder.image = convertView.findViewById(R.id.image_view)

            view = convertView
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
            view = convertView
        }

        val animation = AnimationUtils.loadAnimation(mContext, if (position > lastPosition) R.anim.up_from_bottom else R.anim.down_from_top)
        view.startAnimation(animation)
        lastPosition = position

        viewHolder.tvPos!!.text = position.toString()
        viewHolder.tvWord!!.text = word

        return convertView
    }

}