package com.example.kotlinmvvmcode.repository

import com.example.kotlinmvvmcode.model.Products
import com.example.kotlinmvvmcode.utils.Constants
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class ProductRepositoryTest{
    @RelaxedMockK
    lateinit var apiService: ApiService

    @Before
    fun setup(){
        MockKAnnotations.init(this,true)
    }

    @Test
    fun getAllProductsTest(){
        runBlocking {
            coEvery { apiService.getProducts(Constants.BRAND_NAME) } returns Response.success(Products())
            val response = apiService.getProducts(Constants.BRAND_NAME)
            assertEquals(Products(),response.body())
        }
    }
}