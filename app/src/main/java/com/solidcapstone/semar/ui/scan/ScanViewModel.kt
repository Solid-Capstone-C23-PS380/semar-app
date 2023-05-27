package com.solidcapstone.semar.ui.scan

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.solidcapstone.semar.data.PredictResponse
import com.solidcapstone.semar.data.remote.retrofit.ApiConfig
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScanViewModel : ViewModel() {
    private var _state  = MutableLiveData<Boolean>()
    val state : LiveData<Boolean> = _state

    private var _result = MutableLiveData<String>()
    val result : LiveData<String> = _result

    fun postImage(imgMultipart : MultipartBody.Part, context: Context){
        ApiConfig.getApiService(context)
            .postImage(imgMultipart)
            .enqueue(object : Callback<PredictResponse> {
                override fun onResponse(call: Call<PredictResponse>, response: Response<PredictResponse>){
                    if(response.isSuccessful){
                        if(response.body() != null){
                            _state.postValue(true)
                            _result.postValue(response.body()?.result)
                        }
                    }else{
                        _state.postValue(false)
                    }
                }

                override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                    t.message?.let{ Log.d("Failure Post",it)}
                }
            })
    }

}