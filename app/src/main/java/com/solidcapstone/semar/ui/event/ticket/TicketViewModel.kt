package com.solidcapstone.semar.ui.event.ticket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.solidcapstone.semar.data.remote.repository.WayangRepository
import okhttp3.MultipartBody

class TicketViewModel(private val repository: WayangRepository) : ViewModel() {
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

    fun buyTicket(
        eventId: Int,
        ticketsBought: Int,
        name: String,
        email: String,
        paymentMethod: String,
        imgMultipart: MultipartBody.Part
    ) = repository.buyTicket(eventId, ticketsBought, name, email, paymentMethod, imgMultipart)
}