package com.example.kotlinmvvmcode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.kotlinmvvmcode.databinding.ActivityMainBinding
import com.example.kotlinmvvmcode.utils.Status
import com.example.kotlinmvvmcode.viewmodel.MainViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: MainViewModel
    private val productsAdapter = ProductsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (application as MyApplication).appComponent.inject(this)
        viewModel.getProductsList()
        binding.rvProducts.adapter = productsAdapter

        viewModel.productListState.observe(this,{
            it.let {
                when (it.status) {
                    Status.LOADING -> {
                        binding.progressDialog.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        it.data?.let {
                            binding.progressDialog.visibility = View.GONE
                            productsAdapter.setProductList(it)
                        }
                    }
                    Status.ERROR -> {
                        binding.progressDialog.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        })


//        viewModel.productList.observe(this, {
//            it.let {
//                when (it.status) {
//                    Status.LOADING -> {
//                        binding.progressDialog.visibility = View.VISIBLE
//                    }
//                    Status.SUCCESS -> {
//                        it.data?.let {
//                            binding.progressDialog.visibility = View.GONE
//                            productsAdapter.setProductList(it)
//                        }
//                    }
//                    Status.ERROR -> {
//                        binding.progressDialog.visibility = View.GONE
//                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
//                    }
//                }
//            }
//        })
    }
}