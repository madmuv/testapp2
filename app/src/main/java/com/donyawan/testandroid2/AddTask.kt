package com.donyawan.testandroid2

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.donyawan.testandroid2.databinding.FragmentAddTaskBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTask : Fragment() {

    lateinit var binding: FragmentAddTaskBinding
    private var itemList  = ArrayList<TaskEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(layoutInflater, container, false)

        binding.listItem.adapter = TaskAdapter(itemList)

        setEditText()
        setAddButton()
        setDeleteButton()

        binding.editInput.addTextChangedListener {

            if (it != null) {
                binding.btnAdd.isEnabled = it.isNotEmpty()
            }
        }

        return binding.root
    }

    private fun setDeleteButton() {
        binding.btnDelete.isEnabled = itemList.isNotEmpty()
        binding.btnDelete.setOnClickListener {
            deleletTask()
        }
    }

    private fun setAddButton() {
        binding.btnAdd.setOnClickListener {

            if (TextUtils.isEmpty(binding.editInput.text)) {
                Toast.makeText(requireContext(), "Please, Fill your task", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            addTask(
                TaskEntity(
                    title = binding.editInput.text.toString(),
                    isChecked = false
                )
            )
        }
    }

    private fun setEditText() {

    }

    fun addTask(task: TaskEntity) {
        itemList.add(task)
    }

    fun deleletTask() {
        val filterItem = itemList.filterNot { taskEntity ->  taskEntity.isChecked}
        CoroutineScope(Dispatchers.Main).launch {
            itemList.clear()
            itemList.addAll(filterItem)
        }
    }

}