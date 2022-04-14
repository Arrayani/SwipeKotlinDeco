package com.gamecodeschool.swipekotlindeco

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * Created by Paolo Montalto on 12/06/17.
 * Copyright (c) 2017 Paolo Montalto. All rights reserved.
 */
internal class SampleRecyclerViewAdapter(context: Context) :
    RecyclerView.Adapter<SampleRecyclerViewAdapter.ViewHolder?>() {
    private val context: Context
    private var mItems: MutableList<String>? = null
    fun reloadItems() {
        try {
            mItems = ArrayList()
            (mItems as ArrayList<String>).add("Row 1")
            (mItems as ArrayList<String>).add("Row 2")
            (mItems as ArrayList<String>).add("Row 3")
            (mItems as ArrayList<String>).add("Row 4")
            (mItems as ArrayList<String>).add("Row 5")
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vh: ViewHolder?
       //  try {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.view_list_item, parent, false)
            vh = ViewHolder(view)

      //  } catch (e: Exception) {
      //    Log.e(TAG, e.message!!)
       // }
        return vh
    }



    override fun getItemCount(): Int {
        return mItems!!.size
    }

    fun addItem(item: String, position: Int) {
        try {
            mItems!!.add(position, item)
            notifyItemInserted(position)
        } catch (e: Exception) {
            Log.e("MainActivity", e.message!!)
        }
    }

    internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @JvmField
        var text1: TextView
        @JvmField
        var text2: TextView

        init {
            text1 = itemView.findViewById<View>(R.id.text1) as TextView
            text2 = itemView.findViewById<View>(R.id.text2) as TextView
        }
    }

    fun removeItem(position: Int): String? {
        var item: String? = null
        try {
            item = mItems!![position]
            mItems!!.removeAt(position)
            notifyItemRemoved(position)
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
        }
        return item
    }

    companion object {
        private const val TAG = "ADAPTER"
    }

    init {
        reloadItems()
        this.context = context
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.text1.text = mItems!![position]
            holder.text2.text = "Swipe left or right to see what happens"
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
        }
    }
}