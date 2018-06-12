package com.example.dahaka.mycustomview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import java.util.*

class WatchesView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private lateinit var clockFacePaint: Paint
    private lateinit var arrowsPaint: Paint
    private val padding: Int = 60
    private val displayMetrics = DisplayMetrics()
    private val viewPadding: Int = 10
    private var fontSize: Int = 0
    private var handTruncation: Int = 0
    private var hourHandTruncation: Int = 0
    private var length: Int = 0
    private var isInit: Boolean = false
    private val numbers = intArrayOf(3, 6, 9, 12)
    private val rect = Rect()
    private var arrowsColor: Int = 0
    private var clockFaceColor: Int = 0
    private var backgrColor: Int = 0
    private var arrowWidth: Float = 0f
    private var clockFaceWidth: Float = 0f
    private var secArrowAccuracy: Boolean = false

    init {
        attrs?.let {
            val a = context.theme.obtainStyledAttributes(
                    attrs,
                    R.styleable.WatchesView,
                    0, 0)
            try {
                arrowsColor = a.getColor(R.styleable.WatchesView_arrows_color,
                        ContextCompat.getColor(context, R.color.colorYellow))
                clockFaceColor = a.getColor(R.styleable.WatchesView_clock_face_color,
                        ContextCompat.getColor(context, R.color.colorYellow))
                backgrColor = a.getColor(R.styleable.WatchesView_background_color,
                        ContextCompat.getColor(context, R.color.colorLightBlue))
                arrowWidth = a.getDimension(R.styleable.WatchesView_arrows_width, 3f)
                clockFaceWidth = a.getDimension(R.styleable.WatchesView_arrows_width, 3f)
            } finally {
                a.recycle()
            }
        }
    }

    private fun getArrowsColor() = arrowsColor

    fun setArrowsColor(color: Int) {
        this.arrowsColor = color
    }

    private fun getClockFaceColor() = clockFaceColor

    fun setClockFaceColor(color: Int) {
        clockFaceColor = color
    }

    private fun getBackgrColor() = backgrColor

    fun setBackgrColor(color: Int) {
        backgrColor = color
    }

    fun setArrowsWidth(width: Float) {
        arrowWidth = width
    }

    private fun getArrowsWidth() = arrowWidth

    fun setClockFaceWidth(width: Float) {
        clockFaceWidth = width
    }

    private fun getClockFaceWidth() = clockFaceWidth

    fun setSecArrowSpeed(arrowSpeedAccuracy: Boolean) {
        secArrowAccuracy = arrowSpeedAccuracy
    }

    fun getSecArrowSpeed() = secArrowAccuracy

    private fun initClock() {
        createArrowsPaint()
        createClockFacePaint()
        fontSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14f,
                resources.displayMetrics).toInt()
        val min = Math.min(height, width)
        length = min / 2 - padding
        handTruncation = min / 20
        hourHandTruncation = min / 7
        isInit = true
    }

    override fun onDraw(canvas: Canvas) {
        if (!isInit) {
            initClock()
        }
        canvas.drawColor(getBackgrColor())
        drawCircle(canvas)
        drawNumeral(canvas)
        drawNotches(canvas)
        drawMiniNotches(canvas)
        drawMinutesNotches(canvas)
        drawArrows(canvas, getSecArrowSpeed())
        drawCenter(canvas)
        postInvalidateDelayed(500)
        invalidate()
    }

    private fun createArrowsPaint() {
        arrowsPaint = Paint()
        arrowsPaint.style = Paint.Style.STROKE
        arrowsPaint.strokeCap = Paint.Cap.ROUND
        arrowsPaint.strokeWidth = getArrowsWidth()
        arrowsPaint.color = getArrowsColor()
        arrowsPaint.isAntiAlias = true
    }

    private fun createClockFacePaint() {
        clockFacePaint = Paint()
        clockFacePaint.style = Paint.Style.STROKE
        clockFacePaint.strokeWidth = getClockFaceWidth()
        clockFacePaint.color = getClockFaceColor()
        clockFacePaint.isAntiAlias = true
    }

    private fun drawNotches(canvas: Canvas) {
        val numbers = intArrayOf(1, 2, 4, 5, 7, 8, 10, 11)
        numbers.forEach { i ->
            val drawAngle = Math.PI / 6 * (i - 3)
            val startRadius = Math.min(height, width) / 2 - viewPadding * 2
            val stopRadius = Math.min(height, width) / 2 - (viewPadding * 5)
            canvas.drawLine(
                    (width / 2 + Math.cos(drawAngle) * startRadius).toFloat(),
                    (height / 2 + Math.sin(drawAngle) * startRadius).toFloat(),
                    (width / 2 + Math.cos(drawAngle) * stopRadius).toFloat(),
                    (height / 2 + Math.sin(drawAngle) * stopRadius).toFloat(),
                    clockFacePaint)
        }
    }

    private fun drawMiniNotches(canvas: Canvas) {
        val numbers = intArrayOf(3, 6, 9, 12)
        val startRadius = Math.min(height, width) / 2 - viewPadding
        val stopRadius = Math.min(height, width) / 2 - (viewPadding * 2.5)
        numbers.forEach { i ->
            val drawAngle = Math.PI / 6 * (i - 3)
            canvas.drawLine(
                    (width / 2 + Math.cos(drawAngle) * startRadius).toFloat(),
                    (height / 2 + Math.sin(drawAngle) * startRadius).toFloat(),
                    (width / 2 + Math.cos(drawAngle) * stopRadius).toFloat(),
                    (height / 2 + Math.sin(drawAngle) * stopRadius).toFloat(),
                    clockFacePaint)
        }
    }

    private fun drawMinutesNotches(canvas: Canvas) {
        var numbers = 60
        val startRadius = Math.min(height, width) / 2 - viewPadding * 2
        val stopRadius = Math.min(height, width) / 2 - (viewPadding * 2.5)
        while (numbers > 0) {
            numbers--
            val drawAngle = Math.PI / 30 * (numbers - 5)
            canvas.drawLine(
                    (width / 2 + Math.cos(drawAngle) * startRadius).toFloat(),
                    (height / 2 + Math.sin(drawAngle) * startRadius).toFloat(),
                    (width / 2 + Math.cos(drawAngle) * stopRadius).toFloat(),
                    (height / 2 + Math.sin(drawAngle) * stopRadius).toFloat(),
                    clockFacePaint)
        }
    }

    private fun drawArrow(canvas: Canvas, loc: Double, isHour: Boolean, isMinute: Boolean, isSecond: Boolean) {
        val angle = Math.PI * loc / 30 - Math.PI / 2
        val startRadius = when {
            isHour -> 40
            isMinute -> 38
            else -> length / 20
        }
        val endRadius = when {
            isHour -> length - handTruncation - hourHandTruncation
            isMinute -> length - handTruncation - (hourHandTruncation / 2)
            isSecond -> length - handTruncation
            else -> length / 7
        }
        canvas.drawLine(
                (width / 2 + Math.cos(angle) * startRadius).toFloat(),
                (height / 2 + Math.sin(angle) * startRadius).toFloat(),
                (width / 2 + Math.cos(angle) * endRadius).toFloat(),
                (height / 2 + Math.sin(angle) * endRadius).toFloat(),
                arrowsPaint)
    }

    private fun drawArrows(canvas: Canvas, secondSpeedAcc: Boolean) {
        val c = Calendar.getInstance()
        var hour = c.get(Calendar.HOUR_OF_DAY).toFloat()
        hour = if (hour > 12) {
            hour - 12
        } else {
            hour
        }
        var accuracy = 0.0
        if (secondSpeedAcc) {
            accuracy = c.get(Calendar.MILLISECOND).toDouble() / 1000
        }
        arrowsPaint.strokeWidth = getArrowsWidth() * 4
        drawArrow(canvas, (hour + c.get(Calendar.MINUTE) / 60.0) * 5.0, true, true, false)
        arrowsPaint.strokeWidth = getArrowsWidth() * 3
        drawArrow(canvas, c.get(Calendar.SECOND) / 60.toDouble()
                + c.get(Calendar.MINUTE).toDouble(), false, true, false)
        arrowsPaint.strokeWidth = getArrowsWidth() * 1
        drawArrow(canvas, accuracy + c.get(Calendar.SECOND).toDouble(), false, false, true)
        drawArrow(canvas, accuracy + c.get(Calendar.SECOND) + 30.toDouble(), false, false, false)
    }

    private fun drawNumeral(canvas: Canvas) {
        clockFacePaint.strokeWidth = 4f
        clockFacePaint.textSize = fontSize.toFloat()
        clockFacePaint.color = getClockFaceColor()
        numbers.forEach { i ->
            val tmp = i.toString()
            clockFacePaint.getTextBounds(tmp, 0, tmp.length, rect)
            val angle = Math.PI / 6 * (i - 3)
            val x = (width / 2 + Math.cos(angle) * length - rect.width() / 2).toInt()
            val y = ((height / 2).toDouble() + Math.sin(angle) * length + (rect.height() / 2).toDouble()).toInt()
            canvas.drawText(tmp, x.toFloat(), y.toFloat(), clockFacePaint)
        }
    }

    private fun drawCenter(canvas: Canvas) {
        arrowsPaint.strokeWidth = 24f
        arrowsPaint.color = getArrowsColor()
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), 16f, arrowsPaint)
    }

    private fun drawCircle(canvas: Canvas) {
        clockFacePaint.strokeWidth = 7f
        clockFacePaint.color = getClockFaceColor()
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), (length + padding - viewPadding).toFloat(), clockFacePaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        (context as MainActivity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        val h = displayMetrics.heightPixels
        val w = displayMetrics.widthPixels
        val desiredSize =
                if (h > w) {
                    w / 4 * 3
                } else {
                    h / 4 * 3 - h / 6
                }
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)
        var width = 0
        var height = 0
        when (widthMode) {
            View.MeasureSpec.EXACTLY -> width = widthSize
            View.MeasureSpec.AT_MOST -> width = Math.min(desiredSize, widthSize)
            View.MeasureSpec.UNSPECIFIED -> width = desiredSize
        }
        when (heightMode) {
            View.MeasureSpec.EXACTLY -> height = heightSize
            View.MeasureSpec.AT_MOST -> height = Math.min(desiredSize, heightSize)
            View.MeasureSpec.UNSPECIFIED -> height = desiredSize
        }
        setMeasuredDimension(width, height)
    }
}
