package com.example.kotlinmvvmcode.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinmvvmcode.model.Products
import com.example.kotlinmvvmcode.repository.ProductRepository
import com.example.kotlinmvvmcode.utils.ApiResponse
import com.example.kotlinmvvmcode.utils.Constants
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(val repository: ProductRepository) : ViewModel() {

    val productList = MutableLiveData<ApiResponse<Products>>()
    fun getProductsList() {
        productList.value = ApiResponse.loading(null)
        viewModelScope.launch {
            val response = repository.fetchProducts(Constants.BRAND_NAME)
            when {
                response.isSuccessful -> {
                    val responseBody = response.body()
                    responseBody?.let {
                        productList.value = ApiResponse.success(responseBody)
                    }
                }
                else -> {
                    productList.value = ApiResponse.error(null, response.message())
                }
            }
        }
    }
}