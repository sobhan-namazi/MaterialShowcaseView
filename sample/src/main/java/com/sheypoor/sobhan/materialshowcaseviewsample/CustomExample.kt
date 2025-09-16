package com.sheypoor.sobhan.materialshowcaseviewsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.sheypoor.sobhan.materialshowcaseview.MaterialShowcaseView
import com.sheypoor.sobhan.materialshowcaseview.MaterialShowcaseView.Companion.resetSingleUse

class CustomExample : AppCompatActivity(), View.OnClickListener {
    private var mButtonShow: Button? = null
    private var mButtonReset: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_example)
        mButtonShow = findViewById<Button>(R.id.btn_show)
        mButtonShow!!.setOnClickListener(this)

        mButtonReset = findViewById<Button>(R.id.btn_reset)
        mButtonReset!!.setOnClickListener(this)

        presentShowcaseView(1000) // one second delay
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_custom_example, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_sample_action) {
            val view = findViewById<View?>(R.id.menu_sample_action)
            MaterialShowcaseView.Builder(this, R.layout.custom_showcase_layout)
                .setTarget(view)
                .setShapePadding(96)
                .setDismissText("GOT IT")
                .setContentText("Example of how to setup a MaterialShowcaseView for menu items in action bar.")
                .setContentTextColor(resources.getColor(R.color.green))
                .setMaskColour(resources.getColor(R.color.purple))
                .show()
        }

        return super.onOptionsItemSelected(item)
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
            .setContentText("This is some amazing feature you should know about")
            .setDismissText("GOT IT")
            .setDismissOnTouch(true)
            .setContentTextColor(resources.getColor(R.color.green))
            .setMaskColour(resources.getColor(R.color.purple))
            .setDelay(withDelay) // optional but starting animations immediately in onCreate can make them choppy
            .singleUse(SHOWCASE_ID) // provide a unique ID used to ensure it is only shown once
            .show()
    }

    companion object {
        private const val SHOWCASE_ID = "custom example"
    }
}
