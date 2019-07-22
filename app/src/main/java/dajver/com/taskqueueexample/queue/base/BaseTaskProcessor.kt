package dajver.com.taskqueueexample.queue.base

import dajver.com.taskqueueexample.queue.base.interfaces.BaseProcessCallback
import dajver.com.taskqueueexample.queue.base.interfaces.BaseWorkListener
import dajver.com.taskqueueexample.queue.base.model.BaseWorkItem

abstract class BaseTaskProcessor<T> : BaseProcessCallback<T> where T : BaseWorkItem {

    private var queue = LinkedHashSet<T>()

    private var currentProcessingWorkItem: BaseWorkItem? = null
    protected var listener: BaseWorkListener? = null

    fun queueWorkItem(workItem: T) {
        if (currentProcessingWorkItem == null || currentProcessingWorkItem!! != workItem) {
            queue.add(workItem)
            checkWorkQueue()
        }
    }

    override fun onSuccess(workItem: T) {
        currentProcessingWorkItem = null
        checkWorkQueue()
    }

    override fun onError(workItem: T, error: String?) {
        currentProcessingWorkItem = null
        checkWorkQueue()
    }

    private fun checkWorkQueue() {
        if (currentProcessingWorkItem == null && queue.isNotEmpty()) {
            val currentItem = queue.first()
            queue.remove(currentItem)
            currentProcessingWorkItem = currentItem
            processWorkItem(currentItem, this)
        }
    }

    open fun cancelAll() {
        currentProcessingWorkItem = null
        queue.clear()
    }

    protected abstract fun processWorkItem(item: T, baseProcessCallback: BaseProcessCallback<T>)
}