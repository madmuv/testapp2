package com.donyawan.testandroid2

data class TaskEntity(
    var title: String? = null,
    var isChecked: Boolean = false,
    var id :Int = 0,
    var timeStamp: Long
)
