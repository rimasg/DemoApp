package com.sid.demoapp.views

import android.content.Context
import android.graphics.Camera
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.FrameLayout
import android.widget.ImageView
import com.sid.demoapp.R

class FlipView : FrameLayout {
    private lateinit var ivTopView: ImageView
    private lateinit var ivBackView: ImageView
    private var topView: Drawable? = null
    private var backView: Drawable? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        val root = LayoutInflater.from(context).inflate(R.layout.flip_view, this)
        ivTopView = root.findViewById(R.id.top_view)
        ivBackView = root.findViewById(R.id.back_view)
        //
        val a = context.obtainStyledAttributes(attrs, R.styleable.FlipView, defStyle, 0)
        if (a.hasValue(R.styleable.FlipView_top_view_drawable)) {
            topView = a.getDrawable(R.styleable.FlipView_top_view_drawable)
        }
        if (a.hasValue(R.styleable.FlipView_back_view_drawable)) {
            backView = a.getDrawable(R.styleable.FlipView_back_view_drawable)
        }
        a.recycle()
        //
        if (topView != null) ivTopView.setImageDrawable(topView)
        if (backView != null) ivBackView.setImageDrawable(backView)
    }

    fun flip() {
        val flipAnimation = FlipAnimation(ivTopView, ivBackView)
        if (ivTopView.visibility == View.GONE) {
            flipAnimation.reverse()
        }
        startAnimation(flipAnimation)
    }

    class FlipAnimation(private var fromView: View, private var toView: View) : Animation() {
        private lateinit var camera: Camera

        private var centerX = 0.0f
        private var centerY = 0.0f

        private var forward = true

        init {
            duration = 800
            fillAfter = false
            interpolator = AccelerateDecelerateInterpolator()
        }

        override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
            super.initialize(width, height, parentWidth, parentHeight)
            centerX = (width / 2).toFloat()
            centerY = (height / 2).toFloat()
            camera = Camera()
        }

        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            super.applyTransformation(interpolatedTime, t)
            var degrees: Float = 180f * interpolatedTime

            if (interpolatedTime >= 0.5f) {
                degrees -= 180f
                fromView.visibility = View.GONE
                toView.visibility = View.VISIBLE
            }

            if (forward) {
                degrees = -degrees
            }

            val matrix = t?.matrix
            camera.save()
            camera.rotateY(degrees)
            camera.getMatrix(matrix)
            camera.restore()
            matrix?.preTranslate(-centerX, -centerY)
            matrix?.postTranslate(centerX, centerY)
        }

        fun reverse() {
            forward = false
            val switchView = toView
            toView = fromView
            fromView = switchView
        }
    }

    companion object {
        val TAG: String = FlipView::class.java.simpleName
    }
}