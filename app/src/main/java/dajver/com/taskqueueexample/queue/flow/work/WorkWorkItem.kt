package dajver.com.taskqueueexample.queue.flow.work

import dajver.com.taskqueueexample.models.MetaDataModel
import dajver.com.taskqueueexample.queue.base.model.BaseWorkItem

class WorkWorkItem(override var metadata: MetaDataModel) : BaseWorkItem(metadata)