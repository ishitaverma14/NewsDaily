package com.example.newsdaily


import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

// ek aisi class jiska 1 hi instance ho skta h
class MySingleton constructor(context : Context) {
    // companion is just like static in java..if we want to access member fn/variable directly by class and not by making object we use keyword companion
    companion object{
        // volatile likhne se that fn is  immediately made visible to other threads.
        @Volatile
        private var mInstance : MySingleton? = null;
        fun getInstance(context : Context)= mInstance ?: synchronized(this)
        {
            mInstance?:MySingleton(context).also{
                mInstance = it
            }
        }



        // ans = x?:y means agr x null h toh ans y nhi toh ans is x
    }
    // by lazy - tbtk nhi allocate hogi by lazy wale variable ko memory jbtk use 1st time use nhi kia jata
    // immutable h..or jb b agli baar use hoga toh cache memory mei se utha lega naki new bnega
    private val requestQueue : RequestQueue by lazy{
        // applocationContext is key
        // application ka context isliye lia kyuki puri app mei 1 hi baar banana h hr activity k liye alg nhi
        Volley.newRequestQueue(context.applicationContext)
    }
    fun <T> addToRequestQueue(req : Request<T>)
    {
        requestQueue.add(req)
    }
}