package uk.co.deanwild.materialshowcaseview

import android.graphics.Color
import android.graphics.Typeface
import uk.co.deanwild.materialshowcaseview.shape.Shape

class ShowcaseConfig {
    var delay: Long = -1
    var maskColor: Int = Color.parseColor(DEFAULT_MASK_COLOUR)
    var dismissTextStyle: Typeface? = null

    var contentTextColor: Int = Color.parseColor("#ffffff")
    var dismissTextColor: Int = Color.parseColor("#ffffff")
    var fadeDuration: Long = -1
    var shape: Shape? = null
    var shapePadding: Int = -1
    private var renderOverNav: Boolean? = null

    var renderOverNavigationBar: Boolean?
        get() = renderOverNav
        set(renderOverNav) {
            this.renderOverNav = renderOverNav
        }

    companion object {
        const val DEFAULT_MASK_COLOUR: String = "#dd335075"
    }
}
