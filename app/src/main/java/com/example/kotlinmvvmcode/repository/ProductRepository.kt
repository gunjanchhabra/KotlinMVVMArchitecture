package com.example.kotlinmvvmcode.repository

import com.example.kotlinmvvmcode.model.Products
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(val apiService: ApiService) {
    suspend fun fetchProducts(brandName : String): Response<Products> {
        return apiService.getProducts(brandName)
    }
}