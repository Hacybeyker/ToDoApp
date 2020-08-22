package com.hacybeyker.todoapp.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hacybeyker.todoapp.R
import com.hacybeyker.todoapp.data.models.Priority
import com.hacybeyker.todoapp.data.models.ToDoData
import kotlinx.android.synthetic.main.row_layout.view.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    var dataList = emptyList<ToDoData>()

    fun setData(toDoData: List<ToDoData>) {
        this.dataList = toDoData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ToDoData) {
            itemView.rowTextTitle.text = item.title
            itemView.rowTextDescription.text = item.description
            when (item.priority) {
                Priority.HIGH -> itemView.rowCardPriority.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.red
                    )
                )
                Priority.MEDIUM -> itemView.rowCardPriority.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.yellow
                    )
                )
                Priority.LOW -> itemView.rowCardPriority.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.green
                    )
                )
            }

            itemView.rowConstraintBackground.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(item)
                itemView.findNavController().navigate(action)
            }
        }
    }
}