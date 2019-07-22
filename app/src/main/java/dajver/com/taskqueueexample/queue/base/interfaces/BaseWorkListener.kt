package dajver.com.taskqueueexample.queue.base.interfaces

import dajver.com.taskqueueexample.queue.base.model.BaseWorkItem

interface BaseWorkListener {
    fun onJobComplete(workItem: BaseWorkItem)

    fun onJobFailed(exception: Exception, workItem: BaseWorkItem)
}
