package com.sheypoor.sobhan.materialshowcaseview.shape

import android.graphics.Canvas
import android.graphics.Paint
import com.sheypoor.sobhan.materialshowcaseview.target.Target

/**
 * A Shape implementation that draws nothing.
 */
class NoShape : Shape {
    override fun updateTarget(target: Target) {
        // do nothing
    }

    override val totalRadius: Int = 0

    override fun setPadding(padding: Int) {
        // do nothing
    }

    override fun draw(canvas: Canvas, paint: Paint, x: Int, y: Int) {
        // do nothing
    }

    override val width: Int = 0

    override val height: Int = 0
}
