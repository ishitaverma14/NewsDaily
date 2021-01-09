package com.example.newsdaily

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_news.view.*

class NewsListAdapter(private val listener: ItemClickListener) : RecyclerView.Adapter<NewsViewHolder>() {

    private val items : ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        // ye tb call hota h jb view holder create hora hoga ..ek baar screen pe jitne views show honge bs vhi bnenge baki toh sb recycle honge
        val view : View =LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        val holderView : NewsViewHolder=NewsViewHolder(view)
        view.setOnClickListener{
            listener.onClick(items[holderView.adapterPosition])
        }
        return holderView

    }

    override fun getItemCount(): Int {
        //is list k andr kitne items rhenge,sirf 1st time call
        return items.size

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        // holder k andr data ko bind krra h
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        holder.author.text = currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.imageView)
        
    }
    fun updateNews(updatedNews : ArrayList<News>)
    {
        items.clear()
        items.addAll(updatedNews)
        // just like refresh..when this method is called above 3 functions will run again
        notifyDataSetChanged()
    }

}
class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val titleView : TextView=itemView.title;
    val imageView : ImageView = itemView.image_news;
    val author : TextView = itemView.author;


}
interface ItemClickListener{
    fun onClick(item: News)
}