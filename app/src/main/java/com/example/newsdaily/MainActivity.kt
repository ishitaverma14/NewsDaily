package com.example.newsdaily

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), ItemClickListener {

    private lateinit var mAdapter: NewsListAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler_view.layoutManager=LinearLayoutManager(this)

        getData()
        mAdapter = NewsListAdapter(this)
        recycler_view.adapter = mAdapter
    }
    private  fun getData()
    {
        var url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=aa551e48a91d49ad85e8c9073b22d13c"
        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET,url,
            null,
            Response.Listener {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(index in 0 until newsJsonArray.length())
                {
                    val newsObject = newsJsonArray.getJSONObject(index)
                    val news = News(
                        newsObject.getString("title"),
                        newsObject.getString("author"),
                        newsObject.getString("url"),
                        newsObject.getString("urlToImage")
                    )
                    newsArray.add(news);
                }
                mAdapter.updateNews(newsArray);
            },
            Response.ErrorListener {
                Log.d("wro","wrong")
            }
        )
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        // Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    override fun onClick(item: News) {

        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(
            ContextCompat.getColor(this, R.color.colorPrimary))
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}