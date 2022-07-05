package com.mhelrigo.presyo.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mhelrigo.domain.product.entity.Product
import com.mhelrigo.domain.product.entity.ProductOrigin
import com.mhelrigo.presyo.databinding.ItemProductBinding

class ProductRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.textViewName.text = product.name
            binding.chipCurrentPrice.text = "current ₱ ${
                com.mhelrigo.presyo.main.model.Product.displayablePrice(
                    product.currentPrice,
                )
            }"
            binding.chipPreviousPrice.text = "previous ₱ ${
                com.mhelrigo.presyo.main.model.Product.displayablePrice(
                    product.previousPrice,
                )
            }"
            binding.imageViewTrend.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.root.context,
                    com.mhelrigo.presyo.main.model.Product.trendImage(
                        product.previousPrice,
                        product.currentPrice
                    )
                )
            )
            binding.chipImported.visibility =
                if (product.productOrigin.name == ProductOrigin.INTERNATIONAL.name) View.VISIBLE else View.GONE
        }
    }

    private val products: ArrayList<Product> = arrayListOf()

    fun submitProducts(products: List<Product>) {
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProductViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ProductViewHolder).bind(products[position])
    }

    override fun getItemCount() = products.size
}