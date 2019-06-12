/*
 * Copyright (c) 2017 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.raywenderlich.android.tipcalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    companion object {
        const val DEFAULT_GUESTS = 4
        const val DEFAULT_TIP = 20.0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        setDefaultValues()

        //click listener
        btn_total.setOnClickListener {
            if (mainViewModel.validateBillField() && mainViewModel.validateTipField()
                && mainViewModel.validateGuestField())
                mainViewModel.calculateBillPerPerson()
            else
                Toast.makeText(this, getString(R.string.all_empty), Toast.LENGTH_LONG).show()
        }

        setUpObservers()
    }

    private fun setDefaultValues() {
        //setting default values
        et_guests.setText(getString(R.string.default_guests))
        et_tip.setText(getString(R.string.default_tip))
        mainViewModel.onGuestChange(DEFAULT_GUESTS)
        mainViewModel.onTipChange(DEFAULT_TIP)
    }

    private fun setUpObservers() {

        mainViewModel.getBillPerPerson().observe(this, Observer {
            tv_total.text = getString(R.string.total_per_person, it)
        })

        mainViewModel.getGuestNumber().observe(this, Observer {
            if (Integer.parseInt(et_guests.text.toString()) != it) et_guests.setText(it.toString())
        })

        mainViewModel.getBillAmount().observe(this, Observer {
            if ((et_bill.text?.toString())?.toDouble() != it) et_bill.setText(it.toString())
        })

        mainViewModel.getTipAmount().observe(this, Observer {
            if (et_tip.text?.toString()?.toDouble() != it) et_tip.setText(it.toString())
        })

        //Guest Text Input Layout
        tl_guests.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                if (TextUtils.isEmpty(text)) {
                    tl_guests.isErrorEnabled = true
                    tl_guests.setError(getString(R.string.empty_field_error))
                } else {
                    tl_guests.isErrorEnabled = false
                    mainViewModel.onGuestChange(text.toString().toInt())
                }
            }

        })

        //Bill Text Input Layout
        tl_bill.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                if (TextUtils.isEmpty(text)) {
                    tl_bill.isErrorEnabled = true
                    tl_bill.setError(getString(R.string.empty_field_error))
                } else {
                    tl_bill.isErrorEnabled = false
                    mainViewModel.onBillChange(text.toString().toDouble())
                }
            }


        })

        //Tip Text Input Layout
        tl_tip.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                if (TextUtils.isEmpty(text)) {
                    tl_tip.isErrorEnabled = true
                    tl_tip.setError(getString(R.string.empty_field_error))
                } else {
                    tl_tip.isErrorEnabled = false
                    mainViewModel.onTipChange(text.toString().toDouble())
                }
            }
        })

    }
}
