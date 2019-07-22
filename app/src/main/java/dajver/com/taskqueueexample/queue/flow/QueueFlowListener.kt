package dajver.com.taskqueueexample.queue.flow

import dajver.com.taskqueueexample.models.MetaDataModel

interface QueueFlowListener {
    fun onWorkItemStateChange(metaDataModel: MetaDataModel)
}
