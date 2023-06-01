package com.solidcapstone.semar.ui.event.ticket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TicketViewModel : ViewModel() {
    private val _totalPrice = MutableLiveData<Int>()
    val totalPrice: LiveData<Int>
        get() = _totalPrice

    init {
        _totalPrice.value = 0
    }

    fun onTotalPriceChanged(totalPrice: Int) {
        _totalPrice.value = totalPrice
    }

    fun calculateTotalPrice(price: Int, amount: Int): Int {
        return (price * amount)
    }
}