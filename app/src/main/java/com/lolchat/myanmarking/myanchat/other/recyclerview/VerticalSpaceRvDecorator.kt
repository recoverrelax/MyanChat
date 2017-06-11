package com.lolchat.myanmarking.myanchat.other.recyclerview

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View


@Suppress("JoinDeclarationAndAssignment")
class VerticalSpaceRvDecorator(
        val verticalSpace: Int
): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.getChildAdapterPosition(view) != parent.adapter.itemCount - 1) {
            outRect.bottom = verticalSpace
        }
    }
}