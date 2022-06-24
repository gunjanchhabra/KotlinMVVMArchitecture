package com.example.kotlinmvvmcode

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinmvvmcode.databinding.AdapterProuctsBinding
import com.example.kotlinmvvmcode.model.ProductsItem

class ProductsAdapter : RecyclerView.Adapter<ProductViewHolder>() {

    private var productItemList = ArrayList<ProductsItem>()

    fun setProductList(productItem: ArrayList<ProductsItem>) {
        this.productItemList = productItem
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterProuctsBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productItemList[position]
        holder.binding.name.text = product.name
        holder.binding.emailId.text = product.price
        Glide.with(holder.itemView.context).load(product.image_link).into(holder.binding.imageview)

    }

    override fun getItemCount(): Int {
        return productItemList.size
    }
}

class ProductViewHolder(val binding: AdapterProuctsBinding) : RecyclerView.ViewHolder(binding.root)