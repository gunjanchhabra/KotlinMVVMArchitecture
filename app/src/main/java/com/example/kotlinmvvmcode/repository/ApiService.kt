package com.example.kotlinmvvmcode.repository

import com.example.kotlinmvvmcode.model.Products
import com.example.kotlinmvvmcode.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService{

    @GET(Constants.PRODUCT_API_ENDPOINT)
    suspend fun getProducts(@Query("brand") brand : String ) : Response<Products>
}