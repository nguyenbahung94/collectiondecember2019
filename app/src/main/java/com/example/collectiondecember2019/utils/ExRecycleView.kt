package com.example.collectiondecember2019.utils

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CountryAdapter(
    val context: Context, val block: (viewType: Int, data: CountryDetails) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

//set up recycle view
/*
* private fun setUpRecyclerview() {
    layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    recyclerview_countries?.layoutManager = layoutManager
    countryadapter = CountryAdapter(activity!!, {  viewType : Int, data : CountryDetails -> coutriesItemClick(viewType,data) })
    recyclerview_countries?.adapter = countryadapter
}
fun  coutriesItemClick(viewType: Int, data: CountryDetails){

}
* */

//recycleview click item
/*  itemView.setOnClickListener {
      dataModel?.let {
          block(itemViewType, dataModel!!)
      }
  }*/


data class CountryDetails(val temp: Int)


//multiclick areas
/*
* class PlaylistAdapter(val context: Context, val glideDelegate: GlideDeligate): Adapter() {

    var onItemClick: ((Item) -> Unit)? = null
    var onPlayClick: ((Item) -> Unit)? = null
    var onPauseClick: ((Item) -> Unit)? = null
    var onPreviousClick: ((Item) -> Unit)? = null
    ...// other methods,

    fun onBind(item){
        view.setOnClickListener {
              onItemClick?.invoke(item)
        }
        view.button_play.setOnClickListener {
              onPlayClick?.invoke(item)
        }
}
*
*
* val adapter = PlaylistAdapter(activity!!,glideDeligare)

adapter.onItemClick = { item ->
    // process the item
}

adapter.onPlayClick = { item ->
    // proceed to play
}
* */