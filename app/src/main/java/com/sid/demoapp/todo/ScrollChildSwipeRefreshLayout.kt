package com.sid.demoapp.todo

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet
import android.view.View

class ScrollChildSwipeRefreshLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : SwipeRefreshLayout(context, attrs) {

    var scrollUpChild: View? = null

    override fun canChildScrollUp(): Boolean =
        scrollUpChild?.canScrollVertically(-1) ?: super.canChildScrollUp()
}