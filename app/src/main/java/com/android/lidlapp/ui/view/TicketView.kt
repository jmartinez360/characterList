package com.android.lidlapp.ui.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.lidlapp.R
import com.android.lidlapp.model.DiscountModel

class TicketView constructor(context: Context, private val attrs: AttributeSet)
    : ConstraintLayout(context, attrs) {

    private var ticketPath: Path? = null
    private var fillColor: Int? = null
    private var cutSize: Float? = null
    private val backgroundPaint: Paint = Paint()

    init {
        getAttrs()
        setWillNotDraw(false)
        setupBackgroundPaint()
    }

    private fun setupBackgroundPaint() {
        backgroundPaint.color = fillColor!!
        backgroundPaint.style = Paint.Style.FILL
        backgroundPaint.isAntiAlias = true
    }

    private fun getAttrs() {
        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.TicketView, 0, 0)
        try {
            fillColor = typedArray.getColor(R.styleable.TicketView_ticketColor, DEF_COLOR)
            cutSize = typedArray.getDimension(R.styleable.TicketView_cutSize, DEF_CUT_SIZE)
        } catch (exception: Exception) {
            /* no-op */
        } finally {
            typedArray.recycle()
        }
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        ticketPath = getPath(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        ticketPath?.let { canvas.drawPath(it, backgroundPaint) }
        super.onDraw(canvas)
    }

    private fun getPath(width: Int, height: Int): Path {
        val path = Path()
        val cutoutRadius = cutSize!!
        val cutoutWidth = if (cutoutRadius != 0f) cutoutRadius else width / 8f
        val cutoutHeight = cutoutWidth


        val padding = 0f
        val pathRight = width - padding
        val pathTop = padding

        val cutoutXStart = width / 2 - cutoutWidth
        val cutoutXEnd = cutoutXStart + cutoutWidth * 2
        val topCutoutYStart = pathTop - cutoutHeight
        val topCutoutYEnd = pathTop + cutoutHeight

        path.fillType = Path.FillType.WINDING

        path.moveTo(padding, pathTop)
        path.lineTo(cutoutXStart, pathTop)

        path.arcTo(RectF(cutoutXStart, topCutoutYStart, cutoutXEnd, topCutoutYEnd), 180f, -180f)
        path.lineTo(pathRight, pathTop)

        path.lineTo(pathRight, height.toFloat())
        path.lineTo(padding, height.toFloat())
        path.lineTo(padding, pathTop)

        path.close()
        return path
    }

    fun setTicketColorDependingDiscount(discountType: Int) {
        fillColor = context.getColor(getDiscountColor(discountType))
        setupBackgroundPaint()
        invalidate()
    }

    private fun getDiscountColor(discountType: Int): Int {
        return if(discountType == DiscountModel.LOW_DISCOUNT) {
            R.color.yellowTicket
        } else {
            R.color.redTicket
        }
    }

    companion object {
        const val DEF_COLOR = Color.WHITE
        const val DEF_CUT_SIZE = 0f
    }
}