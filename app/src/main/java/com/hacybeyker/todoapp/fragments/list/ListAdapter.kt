package com.hacybeyker.todoapp.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hacybeyker.todoapp.data.models.ToDoData
import com.hacybeyker.todoapp.databinding.RowLayoutBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    private var dataList = emptyList<ToDoData>()

    fun setData(toDoData: List<ToDoData>) {
        this.dataList = toDoData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder = ListViewHolder.from(parent)

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) = holder.bind(dataList[position])

    override fun getItemCount(): Int = dataList.size

    class ListViewHolder(private val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowLayoutBinding.inflate(layoutInflater, parent, false)
                return ListViewHolder(binding)
            }
        }

        fun bind(item: ToDoData) {
            binding.toDoData = item
            binding.executePendingBindings()
        }
    }
}