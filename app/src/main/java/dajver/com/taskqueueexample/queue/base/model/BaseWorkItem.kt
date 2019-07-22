package dajver.com.taskqueueexample.queue.base.model

import dajver.com.taskqueueexample.models.MetaDataModel

open class BaseWorkItem(open var metadata: MetaDataModel) {

    override fun hashCode(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || this::class != other::class) {
            return false
        }
        return metadata.id == (other as BaseWorkItem).metadata.id
    }
}