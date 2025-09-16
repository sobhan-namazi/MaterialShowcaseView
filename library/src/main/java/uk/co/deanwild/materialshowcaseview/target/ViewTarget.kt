package uk.co.deanwild.materialshowcaseview.target

import android.app.Activity
import android.graphics.Point
import android.graphics.Rect
import android.view.View

class ViewTarget : Target {
    val view: View?

    constructor(view: View?) {
        this.view = view
    }

    constructor(viewId: Int, activity: Activity) {
        this.view = activity.findViewById<View>(viewId)
    }

    override val point: Point
        get() {
            val location = IntArray(2)
            view?.getLocationInWindow(location)
            val x = location[0] + (view?.width ?: 0) / 2
            val y = location[1] + (view?.height ?: 0) / 2
            return Point(x, y)
        }

    override val bounds: Rect
        get() {
            val location = IntArray(2)
            view?.getLocationInWindow(location)
            return Rect(
                location[0],
                location[1],
                location[0] + (view?.measuredWidth ?: 0),
                location[1] + (view?.measuredHeight ?: 0)
            )
        }
}
