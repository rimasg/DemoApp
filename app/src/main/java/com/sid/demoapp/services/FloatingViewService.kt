package com.sid.demoapp.services

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.*
import android.widget.ImageView
import com.sid.demoapp.MainActivity
import com.sid.demoapp.R


class FloatingViewService : Service() {
    private val TAG = FloatingViewService::class.java.simpleName

    private lateinit var windowManager: WindowManager
    private lateinit var floatingView: View
    private var startId: Int = 0

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        floatingView = LayoutInflater.from(this).inflate(R.layout.service_floating_view, null)

        val params = initViewParams()

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        windowManager.addView(floatingView, params)

        initViews(params)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        this.startId = startId
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        windowManager.removeView(floatingView)
    }

    private fun initViewParams(): WindowManager.LayoutParams {
        val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        )

        params.gravity = Gravity.TOP or Gravity.LEFT
        params.x = 0
        params.y = 100

        return params
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initViews(params: WindowManager.LayoutParams) {
        val ivClose = floatingView.findViewById(R.id.iv_close_button) as ImageView
        ivClose.setOnClickListener { stopSelf(startId) }

        val ivFloatingView = floatingView.findViewById(R.id.iv_floating_view_profile) as ImageView
        ivFloatingView.setOnTouchListener(object : View.OnTouchListener {
            private var lastAction = 0
            private var initialX = 0
            private var initialY = 0
            private var initialTouchX = 0f
            private var initialTouchY = 0f

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        initialX = params.x
                        initialY = params.y

                        initialTouchX = event.rawX
                        initialTouchY = event.rawY

                        lastAction = event.action

                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        if (lastAction == MotionEvent.ACTION_DOWN) {
                            val intent = Intent(this@FloatingViewService, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)

                            stopSelf(startId)
                        }

                        lastAction = event.action

                        return true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        params.x = initialX + (event.rawX - initialTouchX).toInt()
                        params.y = initialY + (event.rawY - initialTouchY).toInt()

                        windowManager.updateViewLayout(floatingView, params)
                        lastAction = event.action

                        return true
                    }
                }
                return false
            }
        })

    }
}
