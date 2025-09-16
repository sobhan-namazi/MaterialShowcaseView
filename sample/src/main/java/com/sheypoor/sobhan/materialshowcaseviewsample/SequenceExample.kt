package com.sheypoor.sobhan.materialshowcaseviewsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.sheypoor.sobhan.materialshowcaseview.MaterialShowcaseSequence
import com.sheypoor.sobhan.materialshowcaseview.MaterialShowcaseSequence.OnSequenceItemShownListener
import com.sheypoor.sobhan.materialshowcaseview.MaterialShowcaseView
import com.sheypoor.sobhan.materialshowcaseview.MaterialShowcaseView.Companion.resetSingleUse
import com.sheypoor.sobhan.materialshowcaseview.ShowcaseConfig

class SequenceExample : AppCompatActivity(), View.OnClickListener {
    private var mButtonOne: Button? = null
    private var mButtonTwo: Button? = null
    private var mButtonThree: Button? = null

    private var mButtonReset: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sequence_example)
        mButtonOne = findViewById(R.id.btn_one)
        mButtonOne!!.setOnClickListener(this)

        mButtonTwo = findViewById(R.id.btn_two)
        mButtonTwo!!.setOnClickListener(this)

        mButtonThree = findViewById(R.id.btn_three)
        mButtonThree!!.setOnClickListener(this)

        mButtonReset = findViewById(R.id.btn_reset)
        mButtonReset!!.setOnClickListener(this)

        presentShowcaseSequence() // one second delay
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_one || v.id == R.id.btn_two || v.id == R.id.btn_three) {
            presentShowcaseSequence()
        } else if (v.id == R.id.btn_reset) {
            resetSingleUse(this, SHOWCASE_ID)
            Toast.makeText(this, "Showcase reset", Toast.LENGTH_SHORT).show()
        }
    }

    private fun presentShowcaseSequence() {
        val config = ShowcaseConfig()
        config.delay = 500 // half second between each showcase view

        val sequence = MaterialShowcaseSequence(this, SHOWCASE_ID)

        sequence.setOnItemShownListener(object : OnSequenceItemShownListener {
            override fun onShow(itemView: MaterialShowcaseView?, position: Int) {
                Toast.makeText(itemView?.context, "Item #$position", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        sequence.setConfig(config)

        sequence.addSequenceItem(mButtonOne, "This is button one", "GOT IT")

        sequence.addSequenceItem(
            MaterialShowcaseView.Builder(this)
                .setSkipText("SKIP")
                .setTarget(mButtonTwo)
                .setDismissText("GOT IT")
                .setContentText("This is button two")
                .withRectangleShape(true)
                .build()
        )

        sequence.addSequenceItem(
            MaterialShowcaseView.Builder(this)
                .setTarget(mButtonThree)
                .setDismissText("GOT IT")
                .setContentText("This is button three")
                .withRectangleShape()
                .build()
        )

        sequence.start()
    }

    companion object {
        private const val SHOWCASE_ID = "sequence example"
    }
}
