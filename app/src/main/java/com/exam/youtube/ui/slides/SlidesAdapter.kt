package com.exam.youtube.ui.slides

import android.view.View
import com.exam.youtube.R
import com.exam.youtube.utils.DisplayAdapter
import com.exam.youtube.utils.DisplayItem
import com.exam.youtube.utils.DisplayViewHolder
import kotlinx.android.synthetic.main.item_slide.view.*

class SlidesAdapter: DisplayAdapter() {
    override fun createViewHolder(view: View, viewType: Int) = when (viewType) {
        R.layout.item_slide -> ConfirmationItemDisplay.ViewHolder(view)
        else -> throw RuntimeException("Unknown type $viewType, you should modify createViewHolder")
    }
}

class ConfirmationItemDisplay(
    val title: String?,
    val path: String?,
    val article: String?,
    val click: (ConfirmationItemDisplay) -> Unit
) : DisplayItem(R.layout.item_slide) {
    class ViewHolder(val view: View) : DisplayViewHolder<ConfirmationItemDisplay>(view) {
        override fun bind(item: ConfirmationItemDisplay) {
            view.tv_value.text = item.article
            view.tv_sec.text = item.title
            itemView.setOnClickListener {
                item.click(item)
            }
        }
    }
}

