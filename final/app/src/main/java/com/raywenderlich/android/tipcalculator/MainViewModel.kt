package com.raywenderlich.android.tipcalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/*
 * Copyright (c) 2019 Razeware LLC
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
class MainViewModel : ViewModel(){


  companion object{
    const val TAG = "MainViewModel"
  }


  private val guestsField: MutableLiveData<Int> = MutableLiveData()
  private val billField: MutableLiveData<Double> = MutableLiveData()
  private val tipField: MutableLiveData<Double>  = MutableLiveData()
  private val billPerPerson: MutableLiveData<Double> = MutableLiveData()

  fun onGuestChange(number: Int) = guestsField.postValue(number)
  fun getGuestNumber(): LiveData<Int> = guestsField
  fun validateGuestField():Boolean = guestsField.value != null

  fun onBillChange(number:Double) = billField.postValue(number)
  fun getBillAmount(): LiveData<Double> = billField
  fun validateBillField():Boolean = billField.value != null

  fun onTipChange(number: Double) = tipField.postValue(number)
  fun getTipAmount() : LiveData<Double> = tipField
  fun validateTipField(): Boolean = tipField.value != null

  fun getBillPerPerson(): LiveData<Double> = billPerPerson


  fun calculateBillPerPerson(){
    val guests = guestsField.value
    val bill = billField.value
    val tip = tipField.value

    val tipAmount = (tip!!.times(bill!!)).div(100)
    val totalAmount = bill.plus(tipAmount)
    billPerPerson.postValue(totalAmount.div(guests!!))

  }
}
