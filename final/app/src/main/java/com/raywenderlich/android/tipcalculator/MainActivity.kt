package com.raywenderlich.android.tipcalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        et_guests.setText(getString(R.string.default_guests))
        et_tip.setText(getString(R.string.default_tip))

        btn_total.setOnClickListener{
            mainViewModel.calculateBillPerPerson()
        }

        //Guests EditTextField
        et_guests.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                et_guests.error = null
                mainViewModel.onGuestChange(text.toString().toInt())
            }

            override fun afterTextChanged(s: Editable?) {
                if(!mainViewModel.validateGuestField()){
                    et_guests.error = getString(R.string.empty_field_error)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })

        //Bill EditTextField
        et_bill.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                et_bill.error = null
                mainViewModel.onBillChange(text.toString().toDouble())
            }

            override fun afterTextChanged(s: Editable?) {
                if(!mainViewModel.validateBillField()){
                    et_bill.error = getString(R.string.empty_field_error)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })

        //Tip EditTextField
        et_tip.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                et_tip.error = null
                mainViewModel.onTipChange(text.toString().toDouble())
            }

            override fun afterTextChanged(s: Editable?) {
                if(!mainViewModel.validateTipField()){
                    et_tip.error = getString(R.string.empty_field_error)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })
    }
}
