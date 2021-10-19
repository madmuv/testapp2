package com.donyawan.testandroid2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class TaskViewModel(application: Application): AndroidViewModel(application) {
    val listItem : MutableLiveData<MutableList<TaskEntity>> = MutableLiveData()

    fun clearData() {
        listItem.value = mutableListOf()
    }

    fun deleteTask(itemList: MutableList<TaskEntity>) {
        val filtetNot = itemList.filterNot { data -> data.isChecked }
        listItem.value = filtetNot.toMutableList()
    }
}