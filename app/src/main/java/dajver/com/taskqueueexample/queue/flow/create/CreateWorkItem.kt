package dajver.com.taskqueueexample.queue.flow.create

import dajver.com.taskqueueexample.models.MetaDataModel
import dajver.com.taskqueueexample.queue.base.model.BaseWorkItem

class CreateWorkItem(override var metadata: MetaDataModel) : BaseWorkItem(metadata)