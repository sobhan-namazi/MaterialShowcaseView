package uk.co.deanwild.materialshowcaseviewsample

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView.Companion.resetSingleUse
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig
import uk.co.deanwild.materialshowcaseview.ShowcaseTooltip
import uk.co.deanwild.materialshowcaseview.ShowcaseTooltip.Companion.build

class TooltipExample : Activity(), View.OnClickListener {
    private var mButtonShow: Button? = null
    private var mButtonReset: Button? = null
    private var fab: FloatingActionButton? = null
    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tooltip_example)
        mButtonShow = findViewById<Button>(R.id.btn_show)
        mButtonShow!!.setOnClickListener(this)

        mButtonReset = findViewById<Button>(R.id.btn_reset)
        mButtonReset!!.setOnClickListener(this)

        fab = findViewById<FloatingActionButton>(R.id.fab)
        fab!!.setOnClickListener(this)

        toolbar = findViewById<Toolbar?>(R.id.toolbar)

        presentShowcaseView() // one second delay
    }

    override fun onClick(v: View) {
        if (v.getId() == R.id.btn_show) {
            presentShowcaseView()
        } else if (v.getId() == R.id.btn_reset) {
            resetSingleUse(this, SHOWCASE_ID)
            Toast.makeText(this, "Showcase reset", Toast.LENGTH_SHORT).show()
        } else if (v.getId() == R.id.fab) {
        }
    }

    fun presentShowcaseView() {
        val config = ShowcaseConfig()
        config.delay = 500

        val sequence = MaterialShowcaseSequence(this, SHOWCASE_ID)

        //sequence.setConfig(config);
        val toolTip1 = build(this)
            .corner(30)
            .customView(LayoutInflater.from(this).inflate(R.layout.bubble_custom_view, null))
            .textColor(Color.parseColor("#007686"))
            .text("This is a <b>very funky</b> tooltip<br><br>This is a very long sentence to test how this tooltip behaves with longer strings. <br><br>Tap anywhere to continue")
            .position(ShowcaseTooltip.Position.LEFT)


        sequence.addSequenceItem(
            MaterialShowcaseView.Builder(this, R.layout.custom_showcase_layout)
                .setTarget(toolbar!!.findViewById(R.id.btn_show))
                .setToolTip(toolTip1)
                .withRectangleShape(radius = 30f)
                .setTooltipMargin(30)
//                .setShapePadding(50)
                .setDismissOnTouch(true)
                .setMaskColour(resources.getColor(R.color.tooltip_mask))
                .setDismissText("GOT IT")
                .build()
        )


        val toolTip2 = build(this)
            .corner(30)
            .textColor(Color.parseColor("#007686"))
            .text("This is another <b>very funky</b> tooltip")

        sequence.addSequenceItem(
            MaterialShowcaseView.Builder(this)
                .setTarget(fab)
                .setToolTip(toolTip2)
                .setTooltipMargin(30)
                .setShapePadding(50)
                .setDismissText("GOT IT")
                .setMaskColour(resources.getColor(R.color.tooltip_mask))
                .build()
        )

        sequence.start()
    }

    companion object {
        private const val SHOWCASE_ID = "tooltip example"
    }
}
