package com.clean.currencyconverter.ui.viewmodel

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clean.currencyconverter.Repository.NetworkRepository
import com.clean.currencyconverter.api.NetworkResult
import com.clean.currencyconverter.model.CurrencyData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import javax.inject.Inject
/*
 aed 3.67
 gb 1.2
 1
 */


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
) : ViewModel() {

    private var _currencyDataResponse = MutableLiveData< NetworkResult<CurrencyData>>()
    val currencyResponse: LiveData<NetworkResult<CurrencyData>> = _currencyDataResponse

    private lateinit var prices: Array<Double>
    private val _convertedPrice = MutableLiveData<String>("0")
    val convertedPrice: LiveData<String> = _convertedPrice


    var _enteredPrice = MutableLiveData<String>("0")
    val enteredPrice: LiveData<String> = _enteredPrice

    var selectedPrice :Double = 1.0;
    var convertPrice :Double = 1.0;

    init {
        getCurrencyData()

    }

    private fun getCurrencyData() {
        viewModelScope.launch {
            networkRepository.getCurrencyData().collect {
                _currencyDataResponse.postValue(it)
            }
        }
    }

    fun updateData(prices : Array<Double>)
    {
        this.prices = prices
    }

    fun onItemSelected(pos : Int , isTop : Boolean)
    {
        if(isTop)
        {
            selectedPrice = prices.get(pos)
        }
        else
        {
            convertPrice = prices.get(pos)
        }
        calculatePrice()
    }

    fun calculatePrice()
    {
        var resultPrice : Double = (updatePrice?.toDouble())?.div(selectedPrice.toDouble())!!
        resultPrice *=convertPrice
        _convertedPrice.value = resultPrice.toString()
    }

    companion object {
        var updatePrice : Double = 0.0

        @BindingAdapter("addTextChangeListener")
         @JvmStatic
        fun addTextChangeListener(view: EditText, model : HomeScreenViewModel?) {

            view.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    var p = s?.toString()?:"0"
                    if(p.isEmpty())
                        p = "0"
                    updatePrice = p.toDouble()
                    model?.calculatePrice()


                }

            })

        }
    }

}

