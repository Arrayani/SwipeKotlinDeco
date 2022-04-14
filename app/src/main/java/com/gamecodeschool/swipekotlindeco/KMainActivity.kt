package com.gamecodeschool.swipekotlindeco

import android.graphics.Canvas
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import com.gamecodeschool.swipekotlindeco.SampleRecyclerViewAdapter
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.gamecodeschool.swipekotlindeco.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import androidx.core.content.ContextCompat
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private var mAdapter: SampleRecyclerViewAdapter? = null
    private var actionToggleLayout: MenuItem? = null
    private var isLinear = true
    private var recyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        // Set a layout manager
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        recyclerView!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        // Create and set an adapter
        mAdapter = SampleRecyclerViewAdapter(this)
        recyclerView!!.adapter = mAdapter
        // Create and add a callback
        val callback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                try {
                    val position = viewHolder.adapterPosition
                    val item = mAdapter!!.removeItem(position)
                    val snackbar = Snackbar.make(
                        viewHolder.itemView,
                        "Item " + (if (direction == ItemTouchHelper.RIGHT) "deleted" else "archived") + ".",
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.setAction(android.R.string.cancel) {
                        try {
                            if (item != null) {
                                mAdapter!!.addItem(item, position)
                            }
                        } catch (e: Exception) {
                            Log.e("MainActivity", e.message!!)
                        }
                    }
                    snackbar.show()
                } catch (e: Exception) {
                    Log.e("MainActivity", e.message!!)
                }
            }

            // You must use @RecyclerViewSwipeDecorator inside the onChildDraw method
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftBackgroundColor(
                        ContextCompat.getColor(
                            this@MainActivity,
                            R.color.recycler_view_item_swipe_left_background
                        )
                    )
                    .addSwipeLeftActionIcon(R.drawable.ic_archive_white_24dp)
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(
                            this@MainActivity,
                            R.color.recycler_view_item_swipe_right_background
                        )
                    )
                    .addSwipeRightActionIcon(R.drawable.ic_delete_white_24dp)
                    .addSwipeRightLabel(getString(R.string.action_delete))
                    .setSwipeRightLabelColor(Color.WHITE)
                    .addSwipeLeftLabel(getString(R.string.action_archive))
                    .setSwipeLeftLabelColor(Color.WHITE) //.addCornerRadius(TypedValue.COMPLEX_UNIT_DIP, 16)
                    //.addPadding(TypedValue.COMPLEX_UNIT_DIP, 8, 16, 8)
                    .create()
                    .decorate()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        actionToggleLayout = menu.findItem(R.id.actionToggleLayout)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        try {
            when (item.itemId) {
                R.id.actionRefresh -> {
                    mAdapter!!.reloadItems()
                    mAdapter!!.notifyDataSetChanged()
                }
                R.id.actionToggleLayout -> {
                    if (isLinear) {
                        recyclerView!!.layoutManager = GridLayoutManager(this, 2)
                        actionToggleLayout!!.setIcon(R.drawable.ic_list_white_24dp)
                        isLinear = false
                    } else {
                        recyclerView!!.layoutManager = LinearLayoutManager(this)
                        actionToggleLayout!!.setIcon(R.drawable.ic_grid_on_white_24dp)
                        isLinear = true
                    }
                }
                else -> {}
            }
        } catch (e: Exception) {
            Log.e("MainActivity", e.message!!)
        }
        return super.onOptionsItemSelected(item)
    }
}