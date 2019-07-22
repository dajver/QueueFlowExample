package dajver.com.taskqueueexample.queue.base.interfaces

import dajver.com.taskqueueexample.queue.base.model.BaseWorkItem

interface BaseProcessCallback<T> where  T : BaseWorkItem {
    fun onSuccess(workItem: T)

    fun onError(workItem: T, error: String?)
}