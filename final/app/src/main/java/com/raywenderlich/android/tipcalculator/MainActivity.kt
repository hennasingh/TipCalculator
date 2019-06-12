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
      if (mainViewModel.validateBillField() && mainViewModel.validateTipField() && mainViewModel.validateGuestField())
        mainViewModel.calculateBillPerPerson()
      else
        Toast.makeText(this, getString(R.string.all_empty), Toast.LENGTH_LONG).show()
    }

    btn_reset.setOnClickListener {
      et_guests.text.clear()
      et_bill.text.clear()
      et_tip.text.clear()
      tv_total.text = ""
      setDefaultValues()
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
