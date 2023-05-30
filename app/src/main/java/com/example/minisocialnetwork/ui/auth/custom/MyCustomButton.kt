package com.example.minisocialnetwork.ui.auth.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import com.example.minisocialnetwork.R
import kotlin.properties.Delegates


class MyCustomButton(context: Context, attrs: AttributeSet) :
    androidx.appcompat.widget.AppCompatButton(context, attrs) {

    private var icon: Drawable? = null
        set(value) {
            field = value
            invalidate()
        }
    private var iconPadding: Float = 0f
        set(value) {
            field = value
            invalidate()
        }
    private var cornerRadius: Float = 0f
        set(value) {
            field = value
            invalidate()
        }
    private var textAllCaps = true
        set(value) {
            field = value
            invalidate()
        }

    private val paint = Paint()
    private val textBounds = Rect()
    private var textPositionX by Delegates.notNull<Float>()
    private var textPositionY by Delegates.notNull<Float>()

    init {
        initAttributes(context,attrs)
        initPaint()

    }

    private fun initPaint() {
        with(paint) {
            textSize = this@MyCustomButton.textSize
            typeface = this@MyCustomButton.typeface
            color = currentTextColor
            style = Paint.Style.FILL
            getTextBounds(text.toString(), 0, text.length, textBounds)
        }
    }

    private fun initAttributes(context: Context, attrs: AttributeSet) {
        val styledTypeArray = context.obtainStyledAttributes(
            attrs, R.styleable.MyCustomButton, 0, 0
        )
        with(styledTypeArray)
        {
            cornerRadius = getDimension(R.styleable.MyCustomButton_cornerRadius, 0f)
            icon = getDrawable(R.styleable.MyCustomButton_icon)
            iconPadding = getDimension(R.styleable.MyCustomButton_iconPadding, 0f)
            textAllCaps =
                getBoolean(R.styleable.MyCustomButton_android_textAllCaps, true)
            recycle()
        }
        val attrTypedArray = context.obtainStyledAttributes(
            attrs, intArrayOf(android.R.attr.background), 0, 0)
        with(attrTypedArray) {
            background = getDrawable(0)
            recycle()
        }

    }

    override fun setBackground(background: Drawable?) {
        when (background) {
            is ColorDrawable -> {
                val roundedCorner = RoundRectShape(
                    FloatArray(8) { cornerRadius }, null, null
                )
                val shapeDrawable = ShapeDrawable(roundedCorner)
                shapeDrawable.paint.color = background.color
                super.setBackground(shapeDrawable)
            }

            else -> super.setBackground(background)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        text = if (textAllCaps) text.toString().uppercase() else text

        val textWidth = textBounds.width()
        val textHeight = textBounds.height()
        val iconHeight = icon?.intrinsicWidth ?: 0
        val iconWidth = icon?.intrinsicHeight ?: 0

        setIconBounds(textWidth, iconWidth, iconHeight)
        calculateTextPositions(textWidth, textHeight, iconWidth)
    }

    private fun calculateTextPositions(textWidth: Int, textHeight: Int, iconWidth: Int) {
        if (text == null) return
        textPositionX = (measuredWidth - textWidth + iconWidth + iconPadding) / 2f - textBounds.left
        textPositionY = (measuredHeight + textHeight) / 2f - textBounds.bottom / 2
    }

    private fun setIconBounds(textWidth: Int, iconWidth: Int, iconHeight: Int) {
        if (icon == null) return
        val combinedWidth = textWidth + iconWidth + iconPadding.toInt()
        icon?.setBounds(
            (measuredWidth - combinedWidth) / 2,
            (measuredHeight - iconHeight) / 2,
            (measuredWidth - combinedWidth) / 2 + iconWidth,
            (measuredHeight + iconHeight) / 2
        )
    }

    override fun onDraw(canvas: Canvas?) {
        if (canvas == null) return
        icon?.draw(canvas)
        canvas.drawText(text.toString(), textPositionX, textPositionY, paint)
    }
}


