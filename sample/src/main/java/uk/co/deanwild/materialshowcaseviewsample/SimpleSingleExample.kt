package uk.co.deanwild.materialshowcaseviewsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.Toast
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView.Companion.resetSingleUse
import uk.co.deanwild.materialshowcaseview.shape.OvalShape

class SimpleSingleExample : AppCompatActivity(), View.OnClickListener {
    private var mButtonShow: Button? = null
    private var mButtonReset: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_single_example)
        mButtonShow = findViewById(R.id.btn_show)
        mButtonShow!!.setOnClickListener(this)

        mButtonReset = findViewById(R.id.btn_reset)
        mButtonReset!!.setOnClickListener(this)

        presentShowcaseView(1000) // one second delay
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_show) {
            presentShowcaseView(0)
        } else if (v.id == R.id.btn_reset) {
            resetSingleUse(this, SHOWCASE_ID)
            Toast.makeText(this, "Showcase reset", Toast.LENGTH_SHORT).show()
        }
    }

    private fun presentShowcaseView(withDelay: Int) {
        MaterialShowcaseView.Builder(this)
            .setTarget(mButtonShow)
            .setShape(OvalShape())
            .setTitleText("Hello")
            .setDismissText("GOT IT")
            .setContentText("This is some amazing feature you should know about")
            .setDelay(withDelay) // optional but starting animations immediately in onCreate can make them choppy
            .singleUse(SHOWCASE_ID) // provide a unique ID used to ensure it is only shown once
            //                .useFadeAnimation() // remove comment if you want to use fade animations for Lollipop & up
            .show()
    }


    companion object {
        private const val SHOWCASE_ID = "simple example"
    }
}
