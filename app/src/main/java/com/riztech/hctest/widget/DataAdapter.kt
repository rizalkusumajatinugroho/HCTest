package com.riztech.hctest.widget

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.riztech.hctest.R
import com.riztech.hctest.model.ArticleItem
import com.riztech.hctest.model.ProductItem
import com.riztech.hctest.util.getProgressDrawable
import com.riztech.hctest.util.loadImage

class DataAdapter(private val context: Context?, private var adapterList: ArrayList<Any>) : RecyclerView.Adapter<BaseViewHolder<*>> () {

    companion object{
        private const val VIEW_GRID = 0;
        private const val VIEW_TITLE = 1;
        private const val VIEW_ARTICLE = 2;
    }

    fun updateListData(newListData : ArrayList<Any>){
        adapterList.clear()
        adapterList.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when(viewType){
            VIEW_GRID -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_product, parent,false)
                GridViewHolder(view)
            }
            VIEW_TITLE -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_article_section_title, parent,false)
                TitleViewHolder(view)
            }

            VIEW_ARTICLE -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_article, parent,false)
                ArticleViewHolder(view)
            }else -> throw IllegalArgumentException("Invalid view type")

        }
    }

    override fun getItemCount(): Int {
        return adapterList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = adapterList[position]
        when(holder) {
            is GridViewHolder -> holder.bind(element as List<ProductItem>)
            is TitleViewHolder -> holder.bind(element as String)
            is ArticleViewHolder -> holder.bind(element as ArticleItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val comparable = adapterList[position]
        return when(comparable){
            is List<*> -> VIEW_GRID
            is ArticleItem -> VIEW_ARTICLE
            is String -> VIEW_TITLE
            else -> throw IllegalArgumentException("Invalid type of data " + position)
        }
    }

    inner class GridViewHolder(itemView : View): BaseViewHolder<List<ProductItem>>(itemView){
        val gridView: RecyclerView = itemView.findViewById(R.id.rvGrid)
        private lateinit var adapterProduct: ProductAdapterGrid

        override fun bind(item: List<ProductItem>) {

            adapterProduct = ProductAdapterGrid(context, item)

            gridView.apply {
                layoutManager = GridLayoutManager(context, 3)
                adapter = adapterProduct
                setHasFixedSize(true)
            }


        }
    }

    inner class TitleViewHolder(itemView: View) : BaseViewHolder<String>(itemView){
        val titleSection : TextView = itemView.findViewById(R.id.tvSectionTitle)
        override fun bind(item: String) {
            titleSection.text = item
        }
    }

    inner class ArticleViewHolder(itemView: View) : BaseViewHolder<ArticleItem>(itemView){
        val titleArticle: TextView = itemView.findViewById(R.id.tvArticleTitle)
        val imageArticle: ImageView = itemView.findViewById(R.id.ivArticleImage)
        override fun bind(item: ArticleItem) {
            titleArticle.text = item.articleTitle
            imageArticle.loadImage(item.articleImage, getProgressDrawable(itemView.context))

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