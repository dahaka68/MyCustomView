package com.example.dahaka.mycustomview

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.CompoundButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bckgrColorSwitch.setOnCheckedChangeListener(this)
        cFColorSwitch.setOnCheckedChangeListener(this)
        arrowsColorSwitch.setOnCheckedChangeListener(this)
        arrowsWidthSwitch.setOnCheckedChangeListener(this)
        custom_view.setOnClickListener({
            if (!custom_view.getSecArrowSpeed()) {
                custom_view.setSecArrowSpeed(true)
            } else {
                custom_view.setSecArrowSpeed(false)
            }
        })
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (bckgrColorSwitch.isChecked) {
            custom_view.setBackgrColor(ContextCompat.getColor(applicationContext, R.color.colorTeal))
            container.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorTeal))
            textView.setTextColor(ContextCompat.getColor(applicationContext, android.R.color.white))
            textView2.setTextColor(ContextCompat.getColor(applicationContext, android.R.color.white))
            textView3.setTextColor(ContextCompat.getColor(applicationContext, android.R.color.white))
            textView4.setTextColor(ContextCompat.getColor(applicationContext, android.R.color.white))
        } else {
            custom_view.setBackgrColor(ContextCompat.getColor(applicationContext, R.color.colorLightBlue))
            container.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorLightBlue))
            textView.setTextColor(ContextCompat.getColor(applicationContext, android.R.color.black))
            textView2.setTextColor(ContextCompat.getColor(applicationContext, android.R.color.black))
            textView3.setTextColor(ContextCompat.getColor(applicationContext, android.R.color.black))
            textView4.setTextColor(ContextCompat.getColor(applicationContext, android.R.color.black))
        }
        if (cFColorSwitch.isChecked) {
            custom_view.setClockFaceColor(ContextCompat.getColor(applicationContext, android.R.color.black))
        } else {
            custom_view.setClockFaceColor(ContextCompat.getColor(applicationContext, R.color.colorYellow))
        }
        if (arrowsColorSwitch.isChecked) {
            custom_view.setArrowsColor(ContextCompat.getColor(applicationContext, android.R.color.black))
        } else {
            custom_view.setArrowsColor(ContextCompat.getColor(applicationContext, R.color.colorYellow))
        }
        if (arrowsWidthSwitch.isChecked) {
            custom_view.setArrowsWidth(5f)
        } else {
            custom_view.setArrowsWidth(3f)
        }
    }
}