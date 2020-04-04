package com.riztech.hctest.widget

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.riztech.hctest.R
import com.riztech.hctest.model.ProductItem
import com.riztech.hctest.util.getProgressDrawable
import com.riztech.hctest.util.loadImage
import com.riztech.hctest.view.MainMenuFragmentDirections

class ProductAdapterGrid(private val context: Context?, private val productList: List<ProductItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_product_grid, parent,false)
        return GridHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val productItem = productList.get(position)
        when(holder){
            is GridHolder -> holder.bind(productItem)
            else -> throw IllegalArgumentException("Invalid type of data " + position)
        }
    }

    inner class GridHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvProduct: TextView = itemView.findViewById(R.id.tvGridItem)
        val ivProduct: ImageView = itemView.findViewById(R.id.ivGridItem)

        fun bind(item: ProductItem){
            tvProduct.text = item.productName
            ivProduct.loadImage(item.productImage, getProgressDrawable(itemView.context))
            itemView.setOnClickListener {
                item.link?.let {
                    val uris = Uri.parse(item.link)
                    val intents = Intent(Intent.ACTION_VIEW, uris)
                    val b = Bundle()
                    b.putBoolean("new_window", true)
                    intents.putExtras(b)
                    context?.startActivity(intents)
//                    val action = MainMenuFragmentDirections.goToDetailLink(item.link)
//                    Navigation.findNavController(itemView).navigate(action)
                }

            }
        }
    }
}