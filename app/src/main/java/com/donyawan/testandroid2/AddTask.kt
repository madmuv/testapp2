package com.donyawan.testandroid2

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.donyawan.testandroid2.databinding.FragmentAddTaskBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTask : Fragment() {

    lateinit var binding: FragmentAddTaskBinding
    private var itemList = mutableListOf<TaskEntity>()
    private val taskViewModel: TaskViewModel by viewModels()
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(layoutInflater, container, false)

        setAddButton()
        setDeleteButton()
        observedata()
        setupRecyclerView()


        binding.editInput.addTextChangedListener {

            if (it != null) {
                binding.btnAdd.isEnabled = it.isNotEmpty()
            }
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(TaskClickListener { taskEntry ->
            if (taskEntry.isChecked) {
                taskEntry.isChecked = false
            } else if (!taskEntry.isChecked) {
                taskEntry.isChecked = true
            }

            hideKeyboard(requireActivity())
        })
        binding.listItem.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observedata() {
        taskViewModel.listItem.observe(viewLifecycleOwner, {
            taskAdapter.submitList(it)
            taskAdapter.notifyDataSetChanged()
            itemList = it

            binding.btnDelete.isEnabled = it.isNotEmpty()
        })
    }

    private fun setDeleteButton() {
        binding.btnDelete.setOnClickListener {
            taskViewModel.deleteTask(itemList)
            taskAdapter.notifyDataSetChanged()
        }
    }

    private fun setAddButton() {
        binding.btnAdd.setOnClickListener {

            if (TextUtils.isEmpty(binding.editInput.text)) {
                Toast.makeText(requireContext(), "Please, Fill your task", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.Main).launch {
                itemList.add(
                    TaskEntity(
                        title = binding.editInput.text.toString(),
                        isChecked = false,
                        timeStamp = System.currentTimeMillis()
                    )
                )
                taskViewModel.listItem.postValue(itemList)
                binding.editInput.text.clear()
            }
        }
    }

    private fun hideKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusedView = activity.currentFocus
        currentFocusedView.let {
            inputMethodManager.hideSoftInputFromWindow(
                currentFocusedView?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

}