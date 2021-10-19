package com.donyawan.testandroid2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.donyawan.testandroid2.databinding.ItemListBinding


class TaskAdapter(val clickListener: TaskClickListener) : ListAdapter<TaskEntity, TaskAdapter.IViewHolder>(TaskDiffCallBack()) {

    private class TaskDiffCallBack : DiffUtil.ItemCallback<TaskEntity>() {
        override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
            return oldItem.timeStamp == newItem.timeStamp
        }

        override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IViewHolder {
        return IViewHolder(ItemListBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    class IViewHolder(val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(taskEntry: TaskEntity, clickListener: TaskClickListener) {
            binding.taskEntry = taskEntry
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: IViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, clickListener)
    }

}

class TaskClickListener(val clickListener: (taskEntry: TaskEntity) -> Unit) {
    fun onClick(taskEntry: TaskEntity) = clickListener(taskEntry)
}
