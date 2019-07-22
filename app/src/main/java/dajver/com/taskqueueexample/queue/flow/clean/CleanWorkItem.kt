package dajver.com.taskqueueexample.queue.flow.clean

import dajver.com.taskqueueexample.models.MetaDataModel
import dajver.com.taskqueueexample.queue.base.model.BaseWorkItem

class CleanWorkItem(override var metadata: MetaDataModel) : BaseWorkItem(metadata)