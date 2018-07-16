package com.karbyshev.catstock.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.karbyshev.catstock.R
import com.karbyshev.catstock.mvp.model.Item
import com.karbyshev.catstock.util.formatDate

class MainAdapter(private val notesList: List<Item>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notesList[position]
        holder.noteTitle.text = note.title
        holder.noteDate.text = formatDate(note.changedAt)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var noteTitle = itemView.findViewById<TextView>(R.id.itemNameTextView)
        var noteDate = itemView.findViewById<TextView>(R.id.itemDateTextView)
    }
}