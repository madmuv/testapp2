package com.donyawan.testandroid2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.donyawan.testandroid2.databinding.ItemListBinding


class TaskAdapter(
    private val list: ArrayList<TaskEntity>
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    companion object TaskDiffCallBack : DiffUtil.ItemCallback<TaskEntity>() {
        override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        lateinit var itemCheckBox: CheckBox
        lateinit var itemClickListener: ItemClickListener

        fun setItemClickLsitener(ic: ItemClickListener) {
            this.itemClickListener = ic
        }

        override fun onClick(v: View?) {
            this.itemClickListener.onItemClick(v, layoutPosition)
        }

        interface ItemClickListener {
            fun onItemClick(v: View?, position: Int)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list, null)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.itemCheckBox.text = item.title
        holder.itemCheckBox.isChecked = item.isChecked

        holder.setItemClickLsitener(object : ViewHolder.ItemClickListener{
            override fun onItemClick(v: View?, position: Int) {
                val myCheckBox = v as CheckBox
                val currentItem = list[position]

                if (myCheckBox.isChecked) {
                    currentItem.isChecked = true
                } else if (!myCheckBox.isChecked) {
                    currentItem.isChecked = false
                }
            }

        })
    }

    override fun getItemCount(): Int = list.size
}