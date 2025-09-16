package com.sheypoor.sobhan.materialshowcaseview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.app.DialogFragment
import android.app.Fragment
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Typeface
import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.Window
import android.widget.FrameLayout
import android.widget.TextView


/**
 * Base on original code by florentchampigny
 * https://github.com/florent37/ViewTooltip
 */
class ShowcaseTooltip private constructor(context: Context?) {
    private var rootView: View? = null
    private var view: View? = null
    private val tooltipView: TooltipView


    init {
        val myContext: MyContext = MyContext(getActivityContext(context))
        this.tooltipView = TooltipView(myContext.getContext()!!)
    }

    fun configureTarget(rootView: ViewGroup?, view: View?) {
        this.rootView = rootView
        this.view = view
    }

    fun position(position: Position): ShowcaseTooltip {
        this.tooltipView.setPosition(position)
        return this
    }

    fun customView(customView: View): ShowcaseTooltip {
        this.tooltipView.setCustomView(customView)
        return this
    }

    fun customView(viewId: Int): ShowcaseTooltip {
        this.tooltipView.setCustomView((view!!.context as Activity).findViewById(viewId))
        return this
    }

    fun arrowWidth(arrowWidth: Int): ShowcaseTooltip {
        this.tooltipView.setArrowWidth(arrowWidth)
        return this
    }

    fun arrowHeight(arrowHeight: Int): ShowcaseTooltip {
        this.tooltipView.setArrowHeight(arrowHeight)
        return this
    }

    fun arrowSourceMargin(arrowSourceMargin: Int): ShowcaseTooltip {
        this.tooltipView.setArrowSourceMargin(arrowSourceMargin)
        return this
    }

    fun arrowTargetMargin(arrowTargetMargin: Int): ShowcaseTooltip {
        this.tooltipView.setArrowTargetMargin(arrowTargetMargin)
        return this
    }

    fun align(align: ALIGN): ShowcaseTooltip {
        this.tooltipView.setAlign(align)
        return this
    }

    fun show(margin: Int): TooltipView {
        val activityContext = tooltipView.context
        if (activityContext != null && activityContext is Activity) {
            val decorView =
                if (rootView != null) rootView as ViewGroup else activityContext.window
                    .decorView as ViewGroup

            view!!.postDelayed(object : Runnable {
                override fun run() {
                    val rect = Rect()
                    view!!.getGlobalVisibleRect(rect)

                    val rootGlobalRect = Rect()
                    val rootGlobalOffset = Point()
                    decorView.getGlobalVisibleRect(rootGlobalRect, rootGlobalOffset)

                    val location = IntArray(2)
                    view!!.getLocationOnScreen(location)
                    rect.left = location[0]
                    rect.top -= rootGlobalOffset.y
                    rect.bottom -= rootGlobalOffset.y
                    rect.left -= rootGlobalOffset.x
                    rect.right -= rootGlobalOffset.x

                    // fixes bottom mode
                    rect.top -= margin

                    // fixes top mode
                    rect.bottom += margin

                    decorView.addView(
                        tooltipView,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )

                    tooltipView.viewTreeObserver
                        .addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                            override fun onPreDraw(): Boolean {
                                tooltipView.setup(rect, decorView.width)

                                tooltipView.viewTreeObserver.removeOnPreDrawListener(this)

                                return false
                            }
                        })
                }
            }, 100)
        }
        return tooltipView
    }

    fun color(color: Int): ShowcaseTooltip {
        this.tooltipView.setColor(color)
        return this
    }

    fun color(paint: Paint): ShowcaseTooltip {
        this.tooltipView.setPaint(paint)
        return this
    }

    fun onDisplay(listener: ListenerDisplay?): ShowcaseTooltip {
        this.tooltipView.setListenerDisplay(listener)
        return this
    }

    fun padding(left: Int, top: Int, right: Int, bottom: Int): ShowcaseTooltip {
        this.tooltipView.setPadding(left, top, right, bottom)
        return this
    }

    fun animation(tooltipAnimation: TooltipAnimation): ShowcaseTooltip {
        this.tooltipView.setTooltipAnimation(tooltipAnimation)
        return this
    }

    fun text(text: String?): ShowcaseTooltip {
        this.tooltipView.setText(text)
        return this
    }

    fun text(text: Int): ShowcaseTooltip {
        this.tooltipView.setText(text)
        return this
    }

    fun corner(corner: Int): ShowcaseTooltip {
        this.tooltipView.setCorner(corner)
        return this
    }

    fun textColor(textColor: Int): ShowcaseTooltip {
        this.tooltipView.setTextColor(textColor)
        return this
    }

    fun textTypeFace(typeface: Typeface?): ShowcaseTooltip {
        this.tooltipView.setTextTypeFace(typeface)
        return this
    }

    fun textSize(unit: Int, textSize: Float): ShowcaseTooltip {
        this.tooltipView.setTextSize(unit, textSize)
        return this
    }

    fun setTextGravity(textGravity: Int): ShowcaseTooltip {
        this.tooltipView.setTextGravity(textGravity)
        return this
    }

    fun distanceWithView(distance: Int): ShowcaseTooltip {
        this.tooltipView.setDistanceWithView(distance)
        return this
    }

    fun border(color: Int, width: Float): ShowcaseTooltip {
        val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        borderPaint.color = color
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = width
        this.tooltipView.setBorderPaint(borderPaint)
        return this
    }

    enum class Position {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM,
    }

    enum class ALIGN {
        START,
        CENTER,
        END
    }

    interface TooltipAnimation {
        fun animateEnter(view: View?, animatorListener: Animator.AnimatorListener?)

        fun animateExit(view: View?, animatorListener: Animator.AnimatorListener?)
    }

    interface ListenerDisplay {
        fun onDisplay(view: View?)
    }

    class FadeTooltipAnimation : TooltipAnimation {
        private var fadeDuration: Long = 400

        constructor()

        constructor(fadeDuration: Long) {
            this.fadeDuration = fadeDuration
        }

        override fun animateEnter(view: View?, animatorListener: Animator.AnimatorListener?) {
            view?.alpha = 0f
            view?.animate()?.alpha(1f)?.setDuration(fadeDuration)?.setListener(animatorListener)
        }

        override fun animateExit(view: View?, animatorListener: Animator.AnimatorListener?) {
            view?.animate()?.alpha(0f)?.setDuration(fadeDuration)?.setListener(animatorListener)
        }
    }

    class TooltipView(context: Context) : FrameLayout(context) {
        private var arrowHeight = 15
        private var arrowWidth = 15
        private var arrowSourceMargin = 0
        private var arrowTargetMargin = 0
        protected var childView: View
        private var color = Color.parseColor("#FFFFFF")
        private var bubblePath: Path? = null
        private var bubblePaint: Paint
        private var borderPaint: Paint?
        private var position: Position? = Position.BOTTOM
        private var align = ALIGN.CENTER

        private var listenerDisplay: ListenerDisplay? = null

        private var tooltipAnimation: TooltipAnimation = FadeTooltipAnimation()

        private var corner = 30

        private var paddingTop = 20
        private var paddingBottom = 30
        private var paddingRight = 60
        private var paddingLeft = 60

        private var viewRect: Rect? = null
        private var distanceWithView = 0

        init {
            setWillNotDraw(false)

            this.childView = TextView(context)
            (childView as TextView).setTextColor(Color.BLACK)
            addView(childView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            childView.setPadding(0, 0, 0, 0)

            bubblePaint = Paint(Paint.ANTI_ALIAS_FLAG)
            bubblePaint.setColor(color)
            bubblePaint.setStyle(Paint.Style.FILL)

            borderPaint = null

            setLayerType(LAYER_TYPE_SOFTWARE, bubblePaint)
        }

        fun setCustomView(customView: View) {
            this.removeView(childView)
            this.childView = customView
            addView(childView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        }

        fun setColor(color: Int) {
            this.color = color
            bubblePaint.setColor(color)
            postInvalidate()
        }

        fun setPaint(paint: Paint) {
            bubblePaint = paint
            setLayerType(LAYER_TYPE_SOFTWARE, paint)
            postInvalidate()
        }

        fun setPosition(position: Position) {
            this.position = position
            when (position) {
                Position.TOP -> setPadding(
                    paddingLeft,
                    paddingTop,
                    paddingRight,
                    paddingBottom + arrowHeight
                )

                Position.BOTTOM -> setPadding(
                    paddingLeft,
                    paddingTop + arrowHeight,
                    paddingRight,
                    paddingBottom
                )

                Position.LEFT -> setPadding(
                    paddingLeft,
                    paddingTop,
                    paddingRight + arrowHeight,
                    paddingBottom
                )

                Position.RIGHT -> setPadding(
                    paddingLeft + arrowHeight,
                    paddingTop,
                    paddingRight,
                    paddingBottom
                )
            }
            postInvalidate()
        }

        fun setAlign(align: ALIGN) {
            this.align = align
            postInvalidate()
        }

        fun setText(text: String?) {
            if (childView is TextView) {
                (this.childView as TextView).text = Html.fromHtml(text)
            }
            postInvalidate()
        }

        fun setText(text: Int) {
            if (childView is TextView) {
                (this.childView as TextView).setText(text)
            }
            postInvalidate()
        }

        fun setTextColor(textColor: Int) {
            if (childView is TextView) {
                (this.childView as TextView).setTextColor(textColor)
            }
            postInvalidate()
        }

        fun getArrowHeight(): Int {
            return arrowHeight
        }

        fun setArrowHeight(arrowHeight: Int) {
            this.arrowHeight = arrowHeight
            postInvalidate()
        }

        fun getArrowWidth(): Int {
            return arrowWidth
        }

        fun setArrowWidth(arrowWidth: Int) {
            this.arrowWidth = arrowWidth
            postInvalidate()
        }

        fun getArrowSourceMargin(): Int {
            return arrowSourceMargin
        }

        fun setArrowSourceMargin(arrowSourceMargin: Int) {
            this.arrowSourceMargin = arrowSourceMargin
            postInvalidate()
        }

        fun getArrowTargetMargin(): Int {
            return arrowTargetMargin
        }

        fun setArrowTargetMargin(arrowTargetMargin: Int) {
            this.arrowTargetMargin = arrowTargetMargin
            postInvalidate()
        }

        fun setTextTypeFace(textTypeFace: Typeface?) {
            if (childView is TextView) {
                (this.childView as TextView).setTypeface(textTypeFace)
            }
            postInvalidate()
        }

        fun setTextSize(unit: Int, size: Float) {
            if (childView is TextView) {
                (this.childView as TextView).setTextSize(unit, size)
            }
            postInvalidate()
        }

        fun setTextGravity(textGravity: Int) {
            if (childView is TextView) {
                (this.childView as TextView).setGravity(textGravity)
            }
            postInvalidate()
        }

        fun setCorner(corner: Int) {
            this.corner = corner
        }

        override fun onSizeChanged(width: Int, height: Int, oldw: Int, oldh: Int) {
            super.onSizeChanged(width, height, oldw, oldh)

            bubblePath = drawBubble(
                RectF(0f, 0f, width.toFloat(), height.toFloat()),
                corner.toFloat(),
                corner.toFloat(),
                corner.toFloat(),
                corner.toFloat()
            )
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)

            if (bubblePath != null) {
                canvas.drawPath(bubblePath!!, bubblePaint)
                if (borderPaint != null) {
                    canvas.drawPath(bubblePath!!, borderPaint!!)
                }
            }
        }

        fun setListenerDisplay(listener: ListenerDisplay?) {
            this.listenerDisplay = listener
        }

        fun setTooltipAnimation(tooltipAnimation: TooltipAnimation) {
            this.tooltipAnimation = tooltipAnimation
        }

        protected fun startEnterAnimation() {
            tooltipAnimation.animateEnter(this, object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    if (listenerDisplay != null) {
                        listenerDisplay!!.onDisplay(this@TooltipView)
                    }
                }
            })
        }

        fun setupPosition(rect: Rect) {
            val x: Int
            val y: Int

            if (position == Position.LEFT || position == Position.RIGHT) {
                x = if (position == Position.LEFT) {
                    rect.left - width - distanceWithView
                } else {
                    rect.right + distanceWithView
                }
                y = rect.top + getAlignOffset(height, rect.height())
            } else {
                y = if (position == Position.BOTTOM) {
                    rect.bottom + distanceWithView
                } else { // top
                    rect.top - height - distanceWithView
                }
                x = rect.left + getAlignOffset(width, rect.width())
            }

            translationX = x.toFloat()
            translationY = y.toFloat()
        }

        private fun getAlignOffset(myLength: Int, hisLength: Int): Int {
            return when (align) {
                ALIGN.END -> hisLength - myLength
                ALIGN.CENTER -> (hisLength - myLength) / 2
                else -> 0
            }
        }

        private fun drawBubble(
            myRect: RectF,
            topLeftDiameter: Float,
            topRightDiameter: Float,
            bottomRightDiameter: Float,
            bottomLeftDiameter: Float
        ): Path {
            var topLeftDiameter = topLeftDiameter
            var topRightDiameter = topRightDiameter
            var bottomRightDiameter = bottomRightDiameter
            var bottomLeftDiameter = bottomLeftDiameter
            val path = Path()

            if (viewRect == null) return path

            topLeftDiameter = if (topLeftDiameter < 0) 0f else topLeftDiameter
            topRightDiameter = if (topRightDiameter < 0) 0f else topRightDiameter
            bottomLeftDiameter = if (bottomLeftDiameter < 0) 0f else bottomLeftDiameter
            bottomRightDiameter = if (bottomRightDiameter < 0) 0f else bottomRightDiameter

            val spacingLeft = 30f
            val spacingTop = (if (this.position == Position.BOTTOM) arrowHeight else 0).toFloat()
            val spacingRight = 30f
            val spacingBottom = (if (this.position == Position.TOP) arrowHeight else 0).toFloat()

            val left = spacingLeft + myRect.left
            val top = spacingTop + myRect.top
            val right = myRect.right - spacingRight
            val bottom = myRect.bottom - spacingBottom
            val centerX = viewRect!!.centerX() - x

            val arrowSourceX =
                if (listOf<Position?>(Position.TOP, Position.BOTTOM).contains(this.position))
                    centerX + arrowSourceMargin
                else
                    centerX
            val arrowTargetX =
                if (listOf<Position?>(Position.TOP, Position.BOTTOM).contains(this.position))
                    centerX + arrowTargetMargin
                else
                    centerX
            val arrowSourceY =
                if (listOf<Position?>(Position.RIGHT, Position.LEFT).contains(this.position))
                    bottom / 2f - arrowSourceMargin
                else
                    bottom / 2f
            val arrowTargetY =
                if (listOf<Position?>(Position.RIGHT, Position.LEFT).contains(this.position))
                    bottom / 2f - arrowTargetMargin
                else
                    bottom / 2f

            path.moveTo(left + topLeftDiameter / 2f, top)

            //LEFT, TOP
            if (position == Position.BOTTOM) {
                path.lineTo(arrowSourceX - arrowWidth, top)
                path.lineTo(arrowTargetX, myRect.top)
                path.lineTo(arrowSourceX + arrowWidth, top)
            }
            path.lineTo(right - topRightDiameter / 2f, top)

            path.quadTo(right, top, right, top + topRightDiameter / 2)

            //RIGHT, TOP
            if (position == Position.LEFT) {
                path.lineTo(right, arrowSourceY - arrowWidth)
                path.lineTo(myRect.right, arrowTargetY)
                path.lineTo(right, arrowSourceY + arrowWidth)
            }
            path.lineTo(right, bottom - bottomRightDiameter / 2)

            path.quadTo(right, bottom, right - bottomRightDiameter / 2, bottom)

            //RIGHT, BOTTOM
            if (position == Position.TOP) {
                path.lineTo(arrowSourceX + arrowWidth, bottom)
                path.lineTo(arrowTargetX, myRect.bottom)
                path.lineTo(arrowSourceX - arrowWidth, bottom)
            }
            path.lineTo(left + bottomLeftDiameter / 2, bottom)

            path.quadTo(left, bottom, left, bottom - bottomLeftDiameter / 2)

            //LEFT, BOTTOM
            if (position == Position.RIGHT) {
                path.lineTo(left, arrowSourceY + arrowWidth)
                path.lineTo(myRect.left, arrowTargetY)
                path.lineTo(left, arrowSourceY - arrowWidth)
            }
            path.lineTo(left, top + topLeftDiameter / 2)

            path.quadTo(left, top, left + topLeftDiameter / 2, top)

            path.close()

            return path
        }

        fun adjustSize(rect: Rect, screenWidth: Int): Boolean {
            val r = Rect()
            getGlobalVisibleRect(r)

            var changed = false
            val layoutParams = getLayoutParams()
            if (position == Position.LEFT && width > rect.left) {
                layoutParams.width = rect.left - MARGIN_SCREEN_BORDER_TOOLTIP - distanceWithView
                changed = true
            } else if (position == Position.RIGHT && rect.right + width > screenWidth) {
                layoutParams.width =
                    screenWidth - rect.right - MARGIN_SCREEN_BORDER_TOOLTIP - distanceWithView
                changed = true
            } else if (position == Position.TOP || position == Position.BOTTOM) {
                var adjustedLeft = rect.left
                var adjustedRight = rect.right

                if ((rect.centerX() + width / 2f) > screenWidth) {
                    val diff = (rect.centerX() + width / 2f) - screenWidth

                    adjustedLeft = (adjustedLeft - diff).toInt()
                    adjustedRight = (adjustedRight - diff).toInt()

                    setAlign(ALIGN.CENTER)
                    changed = true
                } else if ((rect.centerX() - width / 2f) < 0) {
                    val diff = -(rect.centerX() - width / 2f)

                    adjustedLeft = (adjustedLeft + diff).toInt()
                    adjustedRight = (adjustedRight + diff).toInt()

                    setAlign(ALIGN.CENTER)
                    changed = true
                }

                if (adjustedLeft < 0) {
                    adjustedLeft = 0
                }

                if (adjustedRight > screenWidth) {
                    adjustedRight = screenWidth
                }

                rect.left = adjustedLeft
                rect.right = adjustedRight
            }

            setLayoutParams(layoutParams)
            postInvalidate()
            return changed
        }

        private fun onSetup(myRect: Rect) {
            setupPosition(myRect)
            bubblePath = drawBubble(
                RectF(0f, 0f, width.toFloat(), height.toFloat()),
                corner.toFloat(),
                corner.toFloat(),
                corner.toFloat(),
                corner.toFloat()
            )
            startEnterAnimation()
        }

        fun setup(viewRect: Rect?, screenWidth: Int) {
            this.viewRect = Rect(viewRect)
            val myRect = Rect(viewRect)

            val changed = adjustSize(myRect, screenWidth)
            if (!changed) {
                onSetup(myRect)
            } else {
                getViewTreeObserver().addOnPreDrawListener(object :
                    ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        onSetup(myRect)
                        getViewTreeObserver().removeOnPreDrawListener(this)
                        return false
                    }
                })
            }
        }

        fun removeNow() {
            if (parent != null) {
                val parent = (parent as ViewGroup)
                parent.removeView(this@TooltipView)
            }
        }

        fun closeNow() {
            removeNow()
        }

        fun setDistanceWithView(distanceWithView: Int) {
            this.distanceWithView = distanceWithView
        }

        fun setBorderPaint(borderPaint: Paint?) {
            this.borderPaint = borderPaint
            postInvalidate()
        }

        companion object {
            private const val MARGIN_SCREEN_BORDER_TOOLTIP = 30
        }
    }

    class MyContext {
        private var fragment: Fragment? = null
        private var context: Context? = null
        private var activity: Activity? = null

        constructor(activity: Activity?) {
            this.activity = activity
        }

        constructor(fragment: Fragment) {
            this.fragment = fragment
        }

        constructor(context: Context?) {
            this.context = context
        }

        fun getContext(): Context? {
            return if (activity != null) {
                activity
            } else {
                (fragment!!.activity as Context)
            }
        }

        fun getActivity(): Activity? {
            if (activity != null) {
                return activity
            } else {
                return fragment!!.activity
            }
        }


        val window: Window?
            get() {
                if (activity != null) {
                    return activity!!.window
                } else {
                    if (fragment is DialogFragment) {
                        return (fragment as DialogFragment).dialog.window
                    }
                    return fragment!!.activity.window
                }
            }
    }

    companion object {
        @JvmStatic
        fun build(context: Context?): ShowcaseTooltip {
            return ShowcaseTooltip(context)
        }

        private fun getActivityContext(context: Context?): Activity? {
            var context = context
            while (context is ContextWrapper) {
                if (context is Activity) {
                    return context
                }
                context = context.baseContext
            }
            return null
        }
    }
}

