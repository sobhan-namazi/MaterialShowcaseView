package com.sheypoor.sobhan.materialshowcaseviewsample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.sheypoor.sobhan.materialshowcaseview.MaterialShowcaseView.Companion.resetAll


class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var button = findViewById<Button>(R.id.btn_simple_example)
        button.setOnClickListener(this)
        button = findViewById(R.id.btn_custom_example)
        button.setOnClickListener(this)
        button = findViewById(R.id.btn_sequence_example)
        button.setOnClickListener(this)
        button = findViewById(R.id.btn_tooltip_example)
        button.setOnClickListener(this)
        button = findViewById(R.id.btn_reset_all)
        button.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null

        when (v.id) {
            R.id.btn_simple_example -> intent = Intent(this, SimpleSingleExample::class.java)
            R.id.btn_custom_example -> intent = Intent(this, CustomExample::class.java)
            R.id.btn_sequence_example -> intent = Intent(this, SequenceExample::class.java)
            R.id.btn_tooltip_example -> intent = Intent(this, TooltipExample::class.java)
            R.id.btn_reset_all -> {
                resetAll(this)
                Toast.makeText(this, "All Showcases reset", Toast.LENGTH_SHORT).show()
            }
        }

        if (intent != null) {
            startActivity(intent)
        }
    }
}
