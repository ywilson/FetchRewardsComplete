package com.example.fetchrewards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.group_items.view.*
import kotlinx.android.synthetic.main.list_items.view.*


class ListAdapter(private val list: List<Items>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            0 -> GroupViewHolder(inflater.inflate(R.layout.group_items, parent, false))
            1 -> ItemViewHolder(inflater.inflate(R.layout.list_items, parent, false))
            else -> throw RuntimeException("No such viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> {
                val groupNum = list[position] as GroupClass
                val groupHolder: GroupViewHolder = holder as GroupViewHolder
                groupHolder.bindGroup(groupNum)
            }
            1 -> {
                val listItems = list[position] as DataClass
                val itemHolder: ItemViewHolder = holder as ItemViewHolder
                itemHolder.bindItems(listItems)
            }
        }
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].getType()
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(data: DataClass) {
            itemView.id_num.text = data.id.toString()
            itemView.name.text = data.name
        }
    }

    class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindGroup(group: GroupClass) {
            itemView.listId.text = group.listId.toString()
        }
    }

}

