package uk.co.deanwild.materialshowcaseview.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import uk.co.deanwild.materialshowcaseview.target.Target
import kotlin.math.max

/**
 * Circular shape for target.
 */
class CircleShape : Shape {
    var radius: Int = 200
    var isAdjustToTarget: Boolean = true
    private var padding = 0

    constructor(radius: Int) {
        this.radius = radius
    }

    constructor(bounds: Rect) : this(getPreferredRadius(bounds))

    constructor(target: Target) : this(target.bounds)

    override fun draw(canvas: Canvas, paint: Paint, x: Int, y: Int) {
        if (radius > 0) {
            canvas.drawCircle(x.toFloat(), y.toFloat(), (radius + padding).toFloat(), paint)
        }
    }

    override fun updateTarget(target: Target) {
        if (this.isAdjustToTarget) radius = getPreferredRadius(target.bounds)
    }

    override val totalRadius: Int
        get() {
            return radius + padding
        }

    override fun setPadding(padding: Int) {
        this.padding = padding
    }

    override val width: Int
        get() {
            return radius * 2
        }

    override val height: Int
        get() {
            return radius * 2
        }

    companion object {
        fun getPreferredRadius(bounds: Rect): Int {
            return max(bounds.width(), bounds.height()) / 2
        }
    }
}
