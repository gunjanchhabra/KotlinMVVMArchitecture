package com.example.kotlinmvvmcode.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.kotlinmvvmcode.model.Products
import com.example.kotlinmvvmcode.repository.ProductRepository
import com.example.kotlinmvvmcode.usecase.ProductUseCase
import com.example.kotlinmvvmcode.utils.ApiResponse
import com.example.kotlinmvvmcode.utils.Constants
import com.example.kotlinmvvmcode.utils.Status
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainViewModelTest{
    private val testDispatcher = TestCoroutineDispatcher()
    lateinit var mainViewModel: MainViewModel

    @RelaxedMockK
    lateinit var productUseCase: ProductUseCase

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this,true)
        Dispatchers.setMain(testDispatcher)
        mainViewModel = MainViewModel(productUseCase)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun getProductsList() {
        coEvery { productUseCase.invoke(Constants.BRAND_NAME) } returns Response.success(
            Products()
        )
        runBlocking {
            mainViewModel.getProductsList()
            assertEquals(ApiResponse(Status.SUCCESS, Products(), null), mainViewModel.productListState.value)

        }
    }

    @Test
    fun apiErrorCase(){
        val response = okhttp3.Response.Builder()
            .code(400)
            .message("Error")
            .protocol(Protocol.HTTP_1_1)
            .request(Request.Builder().url(Constants.BASE_URL).build())
            .build()

        coEvery { productUseCase.invoke(Constants.BRAND_NAME) } returns Response.error(
            ResponseBody.create(
                MediaType.parse("application/json"),"{\"message\":[\"Error\"]}"),response
        )
        runBlocking {
            mainViewModel.getProductsList()
            assertEquals(ApiResponse(Status.ERROR,null,response.message()),mainViewModel.productListState.value)
        }
    }
}