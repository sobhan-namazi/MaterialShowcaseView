package com.sheypoor.sobhan.materialshowcaseview.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import com.sheypoor.sobhan.materialshowcaseview.target.Target


class RectangleShape : Shape {
    private var fullWidth = false

    var computedWidth = 0
    var computedHeight = 0

    var radius = 0f
    var isAdjustToTarget: Boolean = true

    private lateinit var rect: Rect
    private var padding = 0

    constructor(width: Int, height: Int, radius: Float = 0f) {
        this.computedWidth = width
        this.computedHeight = height
        this.radius = radius
        init()
    }

    @JvmOverloads
    constructor(bounds: Rect, fullWidth: Boolean = false, radius: Float = 0f) {
        this.fullWidth = fullWidth
        computedHeight = bounds.height()
        if (fullWidth) computedHeight = Int.Companion.MAX_VALUE
        else computedHeight = bounds.width()
        this.radius = radius
        init()
    }

    private fun init() {
        rect = Rect(-computedWidth / 2, -computedHeight / 2, computedWidth / 2, computedHeight / 2)
    }

    override fun draw(canvas: Canvas, paint: Paint, x: Int, y: Int) {
        if (!rect.isEmpty) {
            canvas.drawRoundRect(
                (rect.left + x - padding).toFloat(),
                (rect.top + y - padding).toFloat(),
                (rect.right + x + padding).toFloat(),
                (rect.bottom + y + padding).toFloat(),
                radius, radius,
                paint
            )
        }
    }

    override fun updateTarget(target: Target) {
        if (this.isAdjustToTarget) {
            val bounds = target.bounds
            computedHeight = bounds.height()
            computedWidth = if (fullWidth) Int.Companion.MAX_VALUE
            else bounds.width()
            init()
        }
    }

    override val totalRadius: Int
        get() {
            return (computedHeight / 2) + padding
        }

    override fun setPadding(padding: Int) {
        this.padding = padding
    }

    override val width: Int
        get() {
            return computedWidth
        }

    override val height: Int
        get() {
            return computedHeight
        }
}