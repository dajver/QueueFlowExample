package dajver.com.taskqueueexample.queue.flow.work

import android.os.Handler
import dajver.com.taskqueueexample.queue.base.BaseTaskProcessor
import dajver.com.taskqueueexample.queue.base.interfaces.BaseProcessCallback
import dajver.com.taskqueueexample.queue.base.interfaces.BaseWorkListener
import dajver.com.taskqueueexample.queue.flow.QueueFlowManager.Companion.SECONDS_IN_ONE_PROCESS

class WorkProcess(var workListener: BaseWorkListener) : BaseTaskProcessor<WorkWorkItem>() {

    init {
        listener = workListener
    }

    override fun processWorkItem(item: WorkWorkItem, baseProcessCallback: BaseProcessCallback<WorkWorkItem>) {
        Handler().postDelayed({
            // do something here and on success call - onSuccess and on fail - onError
            try {
                baseProcessCallback.onSuccess(item)
                workListener.onJobComplete(item)
            } catch (e: Exception) {
                baseProcessCallback.onError(item, e.message!!)
                workListener.onJobFailed(e, item)
            }
        }, SECONDS_IN_ONE_PROCESS!!)
    }
}
