package com.sheypoor.sobhan.materialshowcaseview.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import com.sheypoor.sobhan.materialshowcaseview.target.Target
import kotlin.math.max

class OvalShape : Shape {
    var radius: Int
    var isAdjustToTarget: Boolean
    private var padding = 0

    constructor() {
        this.radius = 200
        this.isAdjustToTarget = true
    }

    constructor(radius: Int) {
        this.radius = 200
        this.isAdjustToTarget = true
        this.radius = radius
    }

    constructor(bounds: Rect) : this(getPreferredRadius(bounds))

    constructor(target: Target) : this(target.bounds)

    override fun draw(canvas: Canvas, paint: Paint, x: Int, y: Int) {
        if (this.radius > 0) {
            val rad = (this.radius + padding).toFloat()
            val rectF = RectF(x - rad, y - rad / 2, x + rad, y + rad / 2)
            canvas.drawOval(rectF, paint)
        }
    }

    override fun updateTarget(target: Target) {
        if (this.isAdjustToTarget) {
            this.radius = getPreferredRadius(target.bounds)
        }
    }

    override val totalRadius: Int
        get() {
            return height/2 + padding
        }

    override fun setPadding(padding: Int) {
        this.padding = padding
    }

    override val width: Int
        get() {
            return this.radius * 2
        }

    override val height: Int
        get() {
            return this.radius
        }

    companion object {
        fun getPreferredRadius(bounds: Rect): Int {
            return max(bounds.width(), bounds.height()) / 2
        }
    }
}

