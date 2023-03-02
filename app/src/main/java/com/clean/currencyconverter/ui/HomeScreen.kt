package com.clean.currencyconverter.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.clean.currencyconverter.api.NetworkResult
import com.clean.currencyconverter.databinding.ActivityHomeScreenBinding
import com.clean.currencyconverter.ui.viewmodel.HomeScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreen : AppCompatActivity() {

    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var homeScreenViewModel: HomeScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        binding.setLifecycleOwner(this)
        setContentView(binding.root)
        homeScreenViewModel = ViewModelProvider(this)[HomeScreenViewModel::class.java]
        binding.homeScreenViewModel = homeScreenViewModel
        homeScreenViewModel.convertedPrice.observe(this){
            binding.convertedPrice.setText(it)
        }
        homeScreenViewModel.currencyResponse.observe(this) {
            when(it) {
                is NetworkResult.Loading -> {
                    binding.progressbar.isVisible = it.isLoading

                }

                is NetworkResult.Failure -> {
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                    binding.progressbar.isVisible = false

                }

                is  NetworkResult.Success -> {
                    binding.progressbar.isVisible = false
                    var data = it.data
                    val items = data.rates
                    homeScreenViewModel.updateData(items.values.toTypedArray())
                    val adapter = getAdapter(items.keys.toTypedArray())
                    binding.bottomCurrency.adapter = adapter
                    binding.topCurrency.adapter = adapter
                    binding.topCurrency.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener{
                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }

                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            homeScreenViewModel.onItemSelected(position , true)
                        }
                    }
                    binding.bottomCurrency.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener{
                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }

                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            homeScreenViewModel.onItemSelected(position , false)
                        }
                    }



                }
            }
        }
    }

   fun getAdapter(items : Array<String>) : ArrayAdapter<String>{

       val adapter = ArrayAdapter(this,
           android.R.layout.simple_spinner_item, items)
       return adapter
   }


}