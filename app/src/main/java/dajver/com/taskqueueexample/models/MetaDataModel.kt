package dajver.com.taskqueueexample.models

import dajver.com.taskqueueexample.queue.enums.Statuses

class MetaDataModel(var id: Int?) {

    var state: String? = Statuses.CREATE

    var statusReason: String? = null

    var stateBeforeFail: String? = null
}
